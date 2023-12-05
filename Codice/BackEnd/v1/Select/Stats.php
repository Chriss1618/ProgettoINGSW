<?php
    include 'functions.php';
    include '../../db_connection.php';

    header('Content-Type: application/json; charset=utf-8');
    $JsonResponse['MSG_STATUS'] = 0;
    $messageResponse = [];

    $con = new mysqli($host_name, $user_name, $password, $database);
    
    if ($con == false) {

        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG_STATUS' => 'Connessione DB Fallita',
            'DATA' => array()
        );

    }else {

        $Id_Ristorante = $_POST['id_ristorante'];
        $sql = "SELECT * 
        FROM `Utente` 
        WHERE `ID_Ristorante` <=> $Id_Ristorante ;";
        
        if($result = $con->query($sql)){

            if($result->num_rows > 0 ){

                $staffArray = [];
                while($row = $result->fetch_array()){
                    
                    $Member = array (
                        'ID_Utente' => $row['ID_Utente'],
                        'Nome' => $row['Nome'],
                        'Cognome' => $row['Cognome'],
                        'Type_User' => $row['Type_User'],
                        'Email' => $row['Email'],
                        'Password' => $row['Password'],
                        'Token' => $row['Token'],
                    );
                
                    array_push($staffArray, $Member);
                   
                }

                $ProdottiOrdinati = [];
                $query = "SELECT ProdottoOrdinato.ID_Utente, ProdottoOrdinato.ID_ProdottoOrdinato, ProdottoOrdinato.DataCompletamento 
                FROM Ordine, Tavolo, ProdottoOrdinato 
                WHERE Tavolo.ID_Ristorante = $Id_Ristorante 
                AND Ordine.ID_Tavolo = Tavolo.ID_Tavolo 
                AND ProdottoOrdinato.ID_Ordine = Ordine.ID_Ordine 
                AND ProdottoOrdinato.ID_Utente IS NOT NULL";

                if ($result = $con->query($query)) {
                    $nTavoliOpened = 0;
                    
                    while($row = $result->fetch_array()){
                        
                        $ProdottoOrdinato = array (
                            'ID_Utente'         => $row['ID_Utente'],
                            'ID_ProdottoOrdinato'     => $row['ID_ProdottoOrdinato'],
                            'DataCompletamento'      => $row['DataCompletamento']
                        );
                        array_push($ProdottiOrdinati, $ProdottoOrdinato);
                    }
                }else{
                    $JsonResponse['STATUS'] = 1;
                    $message = array(
                        'MSG_STATUS' => '0 Errore sql',
                        'DATA' => ''
                    );
                }
                
                $stats['Chefs'] = $staffArray;
                $stats['ProdottiOrdinati'] = $ProdottiOrdinati;

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Stats Ottenute',
                    'DATA' => $stats
                );
            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '0 Nessun Membro',
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