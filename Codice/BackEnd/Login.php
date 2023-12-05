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

    }else {
        $Email          = $_POST['Email'];
        $password       = $_POST['Password'];
        $token          = $_POST['Token'];
        // $Email = "padrepio@gmail.com";
        // $password = "Password123";
        //DEFENDING against sql injection using real escape string 
        //sostituisce tutti caratteri speciali che possono compromettere il DB
        //$NameCategory = $con->real_escape_string($_POST['NameCategory']);
        
        $sql = "SELECT * FROM `Utente` 
        WHERE Email =  '$Email' AND 
        Password = '$password'";
       
        if( $result = $con->query($sql) ){ 
            
            
            if($result->num_rows > 0 ){

                $DataArray = [];
                while($row = $result->fetch_array()){
                    $Account = array (
                        'ID_Utente'     => $row['ID_Utente'],
                        'ID_Ristorante' => $row['ID_Ristorante'],
                        'Nome'          => $row['Nome'],
                        'Cognome'       => $row['Cognome'],
                        'Token'         => $row['Token'],
                        'Type_User'     => $row['Type_User']
                    );
                    array_push($DataArray, $Account);

                    $idUtente = $row['ID_Utente'];
                    $update = "UPDATE `Utente` SET `Token`='$token' WHERE  `ID_Utente` = $idUtente";

                    if( $con->query($update) == true ){
                        
                        $JsonResponse['STATUS'] = 1;
                        $message = array (
                            'MSG_STATUS' => '1 Account Trovato',
                            'DATA' => $DataArray
                        );

                    }else{
                        
                        $JsonResponse['STATUS'] = 0;
                        $message = array (
                            'MSG_STATUS' => '0 Comando SQL non andato a buon fine',
                            'DATA' => $con->error
                        );

                    }

                }

            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '0 Credenziali Errate',
                    'DATA' => ""
                );
            }

        }else{
            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => '0 Failed Query',
                'DATA' => $con->error
            );
        }
        $con->close();
    }
    
    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>