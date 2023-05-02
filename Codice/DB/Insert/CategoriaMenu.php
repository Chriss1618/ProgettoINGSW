<?php
    include 'functions.php';
    include '../../db_connection.php';
    

    $con = new mysqli($host_name, $user_name, $password, $database);
   

    if ($con == false) {
        $arrayJson['status'] = 0;
        $arrayJson['msg'] = " Collegamento al server MySQL non riuscito ";
    }else {
        $Id_Ristorante = $con->real_escape_string($_POST['id_ristorante']);
        $NameCategory = $con->real_escape_string($_POST['NameCategory']);

        $insert = "INSERT INTO `CategoriaMenu`( `ID_Ristorante`, `NameCategory`) 
        VALUES ($Id_Ristorante,'$NameCategory'); ";
        
        if( $conn->query($insert) == true ){
            $id_categoria = $conn->insert_id;

            $arrayJson['status'] = 1;
            $message = array (
                'NomeCategoria' => $row['NameCategory'],
                'ID_CategoriaMenu' => $row['ID_CategoryMenu']
            );
            array_push($messageArray, $message);
            $arrayJson['msg'] = $messageArray;
        }else{
            $arrayJson['status'] = 0;
            $arrayJson['msg'] = " errore ";
            // $arrayJson['msg'] = $conn->error;
        }

        

        $con->close();
    }
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode($arrayJson);
?>