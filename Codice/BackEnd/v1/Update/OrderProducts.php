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
        $nProducts= $_POST['nProducts'];
        $ProductsArray = [];
        for( $i = 0; $i < $nProducts ; $i++){
            
            $Id_Product = $_POST['Id_Product'.$i];
            $Posizione  = $_POST['Posizione'.$i];
            $update = "UPDATE `Prodotto` SET `Posizione`=$Posizione WHERE  `ID_Prodotto` = $Id_Product";

              
            $Product = array (
                'Id_Product' => $Id_Product,
                'Updated' => $con->query($update),
            );
            array_push($ProductsArray,$Product);
        

        }

        $JsonResponse['STATUS'] = 1;
        $message = array (
            'MSG_STATUS' => '1 Products Updated',
            'DATA' => $ProductsArray
        );

    }

        
    

    array_push($messageResponse, $message);


    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>