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

    } else {

        $Id_Ristorante = $_POST['id_ristorante'];
        $Id_categoria  = $_POST['id_category'];

        $delete = "DELETE FROM ProdottoOrdinato
                    WHERE ID_Prodotto IN (
                        SELECT ID_Prodotto
                        FROM Prodotto
                        WHERE ID_CategoryMenu = $Id_categoria
                    );";
        $con->query($delete);

        $delete = "DELETE FROM `CategoriaMenu` 
        WHERE ID_Ristorante = $Id_Ristorante AND 
        ID_CategoryMenu = $Id_categoria";

        $con->query($delete);
        if ($con->affected_rows > 0) {

            $deleted['CategoryDeleted'] = true;

            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG' => '1 Categoria Eliminata',
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