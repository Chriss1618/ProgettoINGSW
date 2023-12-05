<?php
include 'functions.php';
include '../../db_connection.php';
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

header('Content-Type: application/json; charset=utf-8');

$JsonResponse['STATUS'] = 0;
$messageResponse = [];

$con = new mysqli($host_name, $user_name, $password, $database);
if ($con == false) {
    $JsonResponse['STATUS'] = -1;
    $message = array(
        'MSG_STATUS' => '0 Connessione DB Fallita',
        'DATA' => array()
    );

} else {
    $ID_Restaurant      = $_POST['ID_Ristorante'];

    $sql = "SELECT  *  FROM Tavolo WHERE ID_Ristorante = $ID_Restaurant";
    
    if ($result = $con->query($sql)) {
        $nTavoliOpened = 0;
        $TavoliAperti = [];
        while($row = $result->fetch_array()){
            
            $Tavolo = array (
                'ID_Tavolo'         => $row['ID_Tavolo'],
                'ID_Ristorante'     => $row['ID_Ristorante'],
                'State_Tavolo'      => $row['State_Tavolo'],
                'Numero_tavolo'     => $row['Numero_tavolo']
            );
        
            if($Tavolo['State_Tavolo'] == 1){
                $nTavoliOpened++;
                array_push($TavoliAperti, $Tavolo);
            }
        }

        if($nTavoliOpened > 0){
            $Ordini = [];
            for($i = 0 ; $i < $nTavoliOpened; $i++){
                $Id_Tavolo = $TavoliAperti[$i]['ID_Tavolo'];
                $tavoloOrdine = $TavoliAperti[$i];
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
                        
                        if($PrezzoTotale > 0){
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
                                                    'ID_Prodotto'           => $rowProduct['ID_Prodotto'],
                                                    'ID_CategoryMenu'       => $rowProduct['ID_CategoryMenu'],
                                                    'NameProdotto'          => $rowProduct['NameProdotto'],
                                                    'PriceProdotto'         => $rowProduct['PriceProdotto'],
                                                    'Description'           => $rowProduct['Description'],
                                                    'Allergeni'             => $rowProduct['Allergeni'],
                                                    'isSendToKitchen'       => $rowProduct['isSendToKitchen'],
                                                    'PhotoURL'              => $rowProduct['PhotoURL'],
                                                    'Id_User'               => $rowProdottoOrdinato['ID_Utente'],
                                                    'TimestampCompletamento'=> $rowProdottoOrdinato['DataCompletamento'],
                                                    'ID_ProdottoOrdinato'   => $rowProdottoOrdinato['ID_ProdottoOrdinato']
                                                );
                                                if($Product['isSendToKitchen'] == 1) {
                                                    array_push($ProductsArray, $Product);
                                                }
                                                
                                               
                                            }
                                            
                                            
                                        }else{
                                            $JsonResponse['STATUS'] = 1;
                                            $message = array (
                                                'MSG_STATUS' => '0 Errore SQL Prodotto Ordinato',
                                                'DATA' => ""
                                            );
                                        }
            
                                    }
                                    if(!empty($ProductsArray)){
                                        $Ordine = array (
                                            'ID_Ordine' => $id_Ordine,
                                            'PrezzoTotale' => $PrezzoTotale,
                                            'ListaProdottiOrdinati' => $ProductsArray,
                                            'Tavolo' => $tavoloOrdine
                                        );
                                        array_push($Ordini, $Ordine);
                                    }
                                    
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
                        }
                        
        
                    }else{
                        $JsonResponse['STATUS'] = 1;
                        $message = array (
                            'MSG_STATUS' => '0 No Ordini su questo Tavolo',
                            'DATA' => ""
                        );
                    }
                }
            }
            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG_STATUS' => '1 Ordini trovati',
                'DATA' => $Ordini
            );
        }else{
            $JsonResponse['STATUS'] = 1;
                $message = array(
                    'MSG_STATUS' => '0 Nessun Tavolo Aperto',
                    'DATA' => ''
                );
        }
        


    }else{
        $JsonResponse['STATUS'] = 1;
        $message = array(
            'MSG_STATUS' => '0 Errore sql',
            'DATA' => ''
        );
    }

        
    
}

array_push($messageResponse, $message);

$JsonResponse['BODY'] = $messageResponse;
echo json_encode($JsonResponse);
?>
