<?php
    include 'functions.php';
    include '../../db_connection.php';
    
    header('Content-Type: application/json; charset=utf-8');
    $JsonResponse['STATUS'] = 0;
    $messageResponse = [];
    ini_set('display_errors', 1);
    ini_set('display_startup_errors', 1);
    $con = new mysqli($host_name, $user_name, $password, $database);

    if ($con == false) {

        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG' => 'Connessione DB Fallita',
            'DATA' => array()
        );

    }else {
        $Id_Tavolo = $_POST['Id_Tavolo'];
        //$Id_Tavolo = 35;
        
        $sqlOrdine = "SELECT ID_Ordine, Prezzo_Totale
        FROM Ordine
        WHERE `ID_Tavolo` <=> $Id_Tavolo
        ORDER BY ID_Ordine DESC
        LIMIT 1;";
            
        if($resultOrdine = $con->query($sqlOrdine)){
            
            if($resultOrdine->num_rows > 0 ){
                $rowOrdine = $resultOrdine->fetch_array();

                $id_Ordine      = $rowOrdine['ID_Ordine'];
                $PrezzoTotale   = $rowOrdine['Prezzo_Totale'];
                
                $sqlProdottoOrdinato = "SELECT *
                FROM ProdottoOrdinato
                WHERE `ID_Ordine` <=> $id_Ordine ;";

                if($resultProdottoOrdinato = $con->query($sqlProdottoOrdinato)){

                    if($resultProdottoOrdinato->num_rows > 0 ){
                        $ProductsArray = [];
                        while($rowProdottoOrdinato = $resultProdottoOrdinato->fetch_array()){

                            $id_Prodotto = $rowProdottoOrdinato['ID_Prodotto'];

                            $sqlProdotto = "SELECT *
                            FROM Prodotto
                            WHERE `id_Prodotto` <=> $id_Prodotto ;";

                            if($resultProdotto = $con->query($sqlProdotto)){
                                
                                while($rowProduct = $resultProdotto->fetch_array()){
                                    
                                    $Product = array (
                                        'ID_Prodotto'       => $rowProduct['ID_Prodotto'],
                                        'ID_CategoryMenu'   => $rowProduct['ID_CategoryMenu'],
                                        'NameProdotto'      => $rowProduct['NameProdotto'],
                                        'PriceProdotto'     => $rowProduct['PriceProdotto'],
                                        'Description'       => $rowProduct['Description'],
                                        'Allergeni'         => $rowProduct['Allergeni'],
                                        'isSendToKitchen'   => $rowProduct['isSendToKitchen'],
                                        'PhotoURL'          => $rowProduct['PhotoURL']
                                    );
                                    array_push($ProductsArray, $Product);
                                   
                                }
                                
                            }

                        }
                        $Ordine = array (
                            'ID_Ordine' => $id_Ordine,
                            'PrezzoTotale' => $PrezzoTotale,
                            'ListaProdottiOrdinati' => $ProductsArray
                        );
                        
                        $JsonResponse['STATUS'] = 1;
                        $message = array (
                            'MSG_STATUS' => '1 Ordini trovati',
                            'DATA' => $Ordine
                        );
                    }else{
                        $JsonResponse['STATUS'] = 1;
                        $message = array (
                            'MSG_STATUS' => '1 Nessun Ordine effettuato',
                            'DATA' => $id_Ordine
                        );
                    }

                }else{
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG_STATUS' => '0 Errore SQL Prodotto Ordinato',
                        'DATA' => ""
                    );
                }

            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '0 No Ordini su questo Tavolo',
                    'DATA' => ""
                );
            }
            
        }else{

            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => 'Comando SQL non andato a buon fine',
                'DATA' => ""
            );
        }

        $con->close();
    }

    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>