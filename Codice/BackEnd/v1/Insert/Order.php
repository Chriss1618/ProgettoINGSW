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

        
        $insert = "INSERT INTO `Ordine`(  `ID_Tavolo`) 
                                    VALUES ( $Id_Tavolo )";
        if( $con->query($insert) == true ){
            $id_ordine = $con->insert_id;
            $update = "UPDATE `Tavolo` 
            SET `State_Tavolo`=1
            WHERE `Id_Tavolo` = $Id_Tavolo";
        
            if ($con->query($update) === true) {
                $JsonResponse['STATUS'] = 1;
                $message = array(
                    'MSG_STATUS' => '1 Order Created',
                    'DATA' => $id_ordine
                );
            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array(
                    'MSG_STATUS' => '1 Order Created but not updated State',
                    'DATA' => $id_ordine
                );
            }
            
        }else{
            $JsonResponse['STATUS'] = 0;
                $message = array(
                    'MSG_STATUS' => '0 Failed Created',
                    'DATA' => ''
                );
        }
    
    }

    array_push($messageResponse, $message);

    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>