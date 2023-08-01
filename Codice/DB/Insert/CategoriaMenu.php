<?php
    include 'functions.php';
    include '../../db_connection.php';

    $con = new mysqli($host_name, $user_name, $password, $database);
    header('Content-Type: application/json; charset=utf-8');
    if ($con == false) {
        $arrayJson['status'] = 0;
        $arrayJson['msg'] = " Collegamento al server MySQL non riuscito ";
    }else {
        $Id_Ristorante = $_POST['id_ristorante'];
        //DEFENDING against sql injection using real escape string 
        //sostituisce tutti caratteri speciali che possono compromettere il DB
        $NameCategory = $con->real_escape_string($_POST['NameCategory']);

        $insert = "INSERT INTO `CategoriaMenu`( `ID_Ristorante`, `NameCategory`) 
        VALUES ($Id_Ristorante,'$NameCategory') ";
       
        if( $con->query($insert) == true ){
            $arrayJson['status'] = 1;
            $id_newCategory = $con->insert_id;
            $messageArray = [];
            $message = array (
                'NomeCategoria' => $NameCategory,
                'ID_CategoriaMenu' => $id_newCategory
            );
            array_push($messageArray, $message);
            $arrayJson['msg'] = $messageArray;
        }else{
            $arrayJson['status'] = 0;
            $arrayJson['msg'] = $con->error;
        }
        $con->close();
    }
    
    echo json_encode($arrayJson);
?>