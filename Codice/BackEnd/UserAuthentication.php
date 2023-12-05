<?php
    include 'functions.php';
    include 'db_connection.php';

    header('Content-Type: application/json; charset=utf-8');
    $JsonResponse['STATUS'] = 0;
    $messageResponse = [];

    $con = new mysqli($host_name, $user_name, $password, $database);
    if ($con == false) {
    
        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG' => '0 Connessione DB Fallita',
            'DATA' => array()
        );

    }else{
        $Id_Ristorante  = $_POST['id_ristorante'];
        $EmailUser      = $_POST['EmailUser'];
        $TokenUser      = $_POST['TokenUser'];

        if($Id_Ristorante == ''){
            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG' => '0 Nessun Utente Passato',
                'DATA' => ""
            );
        }else{
            
            $sql = "SELECT * 
            FROM `Utente` 
            WHERE `ID_Ristorante` <=> $Id_Ristorante AND 
            `Email` <=> '$EmailUser' AND 
            `Token` <=> '$TokenUser';";

            if($result = $con->query($sql)){
                if($result->num_rows == 1 ){

                    $account = [];
                    while($row = $result->fetch_array()){

                        $DataAccount = array (
                            'ID_Utente'     => $row['ID_Utente'],
                            'ID_Ristorante' => $row['ID_Ristorante'],
                            'Nome'          => $row['Nome'],
                            'Cognome'       => $row['Cognome'],
                            'Type_User'     => $row['Type_User'],
                            'Email'         => $row['Email'],
                            'Password'      => $row['Password'],
                            'Token'         => $row['Token']
                        );
                        array_push($account,$DataAccount);
                    }
                    

                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG' => '1 Utente trovato',
                        'DATA' => $account
                    );
                   
                }else{
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG' => '0 Nessun Utente con questo Token',
                        'DATA' => ""
                    );
                }
            }else{
                $JsonResponse['STATUS'] = -1;
                $message = array (
                    'MSG' => '0 Comando SQL non andato a buon fine',
                    'DATA' => ""
                );
            }

        }
        

        $con->close();
    }
    
    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);

    
?>