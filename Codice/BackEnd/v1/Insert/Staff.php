<?php
    include 'functions.php';
    include '../../db_connection.php';
    include '../../sendemail/SendEmail.php';

    header('Content-Type: application/json; charset=utf-8');

    $JsonResponse['STATUS'] = 0;
    $messageResponse = [];
    // $JsonResponse['STATUS'] = 1;

    // $datiRicevuti['Nome'] = $_POST['Nome'];
    // $datiRicevuti['Cognome'] = $_POST['Cognome'];
    // $datiRicevuti['Type_User'] = $_POST['Type_User'];
    // $datiRicevuti['Email'] = $_POST['Email'];
    // $datiRicevuti['Password'] = $_POST['Password'];
    // $datiRicevuti['ID_Ristorante'] = $_POST['ID_Ristorante'];
    // $message = array (
    //     'MSG_STATUS' => '1 New Staff Inserted',
    //     'DATA' => $datiRicevuti
    // );

    $con = new mysqli($host_name, $user_name, $password, $database);
    if($con == false ){
        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG_STATUS' => '0 Connessione DB Fallita',
            'DATA' => array()
        );
    }else{
        $Nome               = $_POST['Nome'];
        $Cognome            = $_POST['Cognome'];
        $Type_User          = $_POST['Type_User'];
        $Email              = $_POST['Email'];
        $Password           = $_POST['Password'];
        $ID_Ristorante      = $_POST['ID_Ristorante'];
        
        $insert = "INSERT INTO `Utente`(`Nome`, `Cognome`, `Type_User`, `Email`, `Password`, `Token`, `ID_Ristorante`) 
                    VALUES ( '$Nome','$Cognome','$Type_User','$Email','$Password','NO_TOKEN',$ID_Ristorante)";

        if( $con->query($insert) == true ){
            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG_STATUS' => '1 New Staff Inserted',
                'DATA' => 'Ok'
            );
            sendMail($Email,$Password);

        }else{
            
            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => '0 New Stuff NOT Inserted',
                'DATA' => ''
            );

        }
        
    
    }

    array_push($messageResponse, $message);

    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>