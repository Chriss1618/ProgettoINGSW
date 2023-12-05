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
        $nProducts= $_POST['nProdottiOrdinati'];
        $Id_Utente= $_POST['Id_Utente'];
        $ProductsArray = [];
        $timestamp = time();
        for( $i = 0; $i < $nProducts ; $i++){
            $Id_Product = $_POST['ID_Product'.$i];
            $Id_ProductOrdinato = $_POST['ID_ProdottoOrdinato'.$i];
            $update = "UPDATE `ProdottoOrdinato` SET `ID_Utente`=$Id_Utente , `DataCompletamento`=$timestamp   WHERE  `ID_ProdottoOrdinato` = $Id_ProductOrdinato";
              
            $Product = array (
                'Id_Product' => $Id_ProductOrdinato,
                'Updated' => $con->query($update),
            );
            array_push($ProductsArray,$Product);

        }

        $JsonResponse['STATUS'] = 1;
        $message = array (
            'MSG_STATUS' => '1 Ordiniti Confermati',
            'DATA' => $ProductsArray
        );

    }
    array_push($messageResponse, $message);

    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>