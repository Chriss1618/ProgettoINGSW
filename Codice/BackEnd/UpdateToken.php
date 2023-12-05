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
        $idUtente = $_POST['ID_Utente'];
        $token    = $_POST['Token'];
        
        $idUtente = $row['ID_Utente'];
        $update = "UPDATE `Utente` SET `Token`='$token' WHERE  `ID_Utente` = $idUtente";

        if( $con->query($update) == true ){
            
            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG_STATUS' => '1 Token Updated',
                'DATA' => $DataArray
            );

        }else{
            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => '0 Comando SQL non andato a buon fine',
                'DATA' => $con->error
            );

        }

        $con->close();
    }
    
    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>