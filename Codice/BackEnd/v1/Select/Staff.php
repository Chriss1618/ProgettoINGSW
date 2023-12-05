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

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Staff Trovati',
                    'DATA' => $staffArray
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