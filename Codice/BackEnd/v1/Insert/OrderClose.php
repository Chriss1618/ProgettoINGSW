<?php
    include 'functions.php';
    include '../../db_connection.php';
    
    header('Content-Type: application/json; charset=utf-8');

    $JsonResponse['STATUS'] = 0;
    $messageResponse = [];

    $con = new mysqli($host_name, $user_name, $password, $database);
    if($con == false ){
        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG_STATUS' => '0 Connessione DB Fallita',
            'DATA' => array()
        );
    }else{
        $Id_Tavolo        = $_POST['Id_Tavolo'];
        
        $update = "UPDATE `Tavolo` 
        SET `State_Tavolo`= 0
        WHERE `Id_Tavolo` = $Id_Tavolo";
    
        if ($con->query($update) === true) {
            $JsonResponse['STATUS'] = 1;
            $message = array(
                'MSG_STATUS' => '1 Table Closed',
                'DATA' => "ok"
            );
        }else{
            $JsonResponse['STATUS'] = 1;
            $message = array(
                'MSG_STATUS' => '0 Table NOT Closed',
                'DATA' => ""
            );
            }
            
        
    
    }

    array_push($messageResponse, $message);

    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>