<?php
    include 'functions.php';
    include '../../db_connection.php';

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
        $ID_Category = $_POST['ID_Category'];
        $Id_Product = $_POST['Id_Product'];
        
        $delete = "DELETE FROM `Ricettario` 
        WHERE  ID_Prodotto = $Id_Product";
        if( $con->query($delete) === true ){ 


        }

        $delete = "DELETE FROM `ProdottoOrdinato` 
        WHERE  ID_Prodotto = $Id_Product";
        if( $con->query($delete) === true ){ 
            

        }
        $delete = "DELETE FROM `Prodotto` 
        WHERE ID_CategoryMenu = $ID_Category AND 
        ID_Prodotto = $Id_Product";
       
        if( $con->query($delete) === true ){ 

            $deleted['ProductDeleted'] = true;

            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG' => '1 Prodotto Eliminato',
                'DATA' => $deleted
            );

        }else{
            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG' => '0 Failed Deleting',
                'DATA' => $con->error
            );
        }
        $con->close();
    }
    
    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>