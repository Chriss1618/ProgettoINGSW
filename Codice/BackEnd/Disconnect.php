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
        $ID_Utente          = $_POST['ID_Utente'];

        $update = "UPDATE `Utente` 
        SET `Token`='DISCONNECTED'
        WHERE `ID_Utente` = $ID_Utente";
    
        if ($con->query($update) === true) {
            $JsonResponse['STATUS'] = 1;
            $message = array(
                'MSG_STATUS' => '1 Utente Disconnected',
                'DATA' => 'Ok'
            );
        }else{
            $JsonResponse['STATUS'] = 0;
            $message = array(
                'MSG_STATUS' => '0 Failed to disconnect',
                'DATA' => ''
            );
        }
        $con->close();
    }
    
    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>