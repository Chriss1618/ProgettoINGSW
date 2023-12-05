<?php
    include 'functions.php';
    include '../../db_connection.php';

    header('Content-Type: application/json; charset=utf-8');
    $JsonResponse['MSG_STATUS'] = 0;
    $messageResponse = [];

    $con = new mysqli($host_name, $user_name, $password, $database);
    
    if ($con == false) {

        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG_STATUS' => 'Connessione DB Fallita',
            'DATA' => array()
        );

    }else {
        $Id_Ristorante = $_POST['id_ristorante'];
        $sql = "SELECT * 
        FROM `CategoriaMenu` 
        WHERE `ID_Ristorante` <=> $Id_Ristorante ;";
        
        if($result = $con->query($sql)){

            $arrayJson['status'] = 1;

            if($result->num_rows > 0 ){

                $categorieArray = [];
                while($row = $result->fetch_array()){
                    
                    $categoria = array (
                        'NomeCategoria' => $row['NameCategory'],
                        'ID_CategoriaMenu' => $row['ID_CategoryMenu']
                    );
                    
                    array_push($categorieArray, $categoria);
                   
                }

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Categorie Trovate',
                    'DATA' => $categorieArray
                );
            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '0 Nessuna Categoria',
                    'DATA' => ""
                );
            }
        }else{

            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => 'Comando SQL non andato a buon fine',
                'DATA' => ""
            );
        }

        $con->close();
    }

    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>