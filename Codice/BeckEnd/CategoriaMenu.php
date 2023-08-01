<?php
    include 'functions.php';
    include '../../db_connection.php';

    $con = new mysqli($host_name, $user_name, $password, $database);
    header('Content-Type: application/json; charset=utf-8');

    if ($con == false) {
        $arrayJson['satus'] = 0;
        $arrayJson['msg'] = " Collegamento al server MySQL non riuscito ";
    }else {
        $Id_Ristorante = $_POST['id_ristorante'];
        $sql = " SELECT * FROM `CategoriaMenu` WHERE `ID_Ristorante` <=> $Id_Ristorante ;";
        
        if($result = $con->query($sql)){
            if($result->num_rows > 0 ){
                //echo $result->num_rows;
                while($row = $result->fetch_array()){
                    //$message =   $row['nome']. ' '. $row['cognome'];
                    $message = array (
                        'NomeCategoria' => $row['NameCategory'],
                        'ID_CategoriaMenu' => $row['ID_CategoryMenu']
                    );

                    $arrayJson['satus'] = 1;
                    $arrayJson['msg'] = $message;
                }
            }else{
                echo "Non ci sono elementi";
            }
        }else{
            echo "Errore eseguire la query $sql." .$con->error;
        }

        $con->close();
    }
    echo json_encode($arrayJson);
?>