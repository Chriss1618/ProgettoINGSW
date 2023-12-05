<?php
    include 'functions.php';
    include '../../db_connection.php';
    
    header('Content-Type: application/json; charset=utf-8');
    error_reporting(E_ALL);
    ini_set('display_errors', 1);
    $JsonResponse['STATUS'] = 0;
    $messageResponse = [];
    
    $con = new mysqli($host_name, $user_name, $password, $database);
    if ($con == false) {

        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG_STATUS' => '0 Connessione DB Fallita',
            'DATA' => array()
        );

    }else {
        $Id_Ristorante = $_POST['id_ristorante'];
        //DEFENDING against sql injection using real escape string 
        //sostituisce tutti caratteri speciali che possono compromettere il DB
        $NameCategory = $con->real_escape_string($_POST['NameCategory']);
        if( $NameCategory != "" ){
            $sql = "SELECT * 
            FROM `CategoriaMenu` 
            WHERE `ID_Ristorante` <=> $Id_Ristorante AND `NameCategory` <=> '$NameCategory' ;";
            
            if($result = $con->query($sql)){
    
                $arrayJson['status'] = 1;
    
                if($result->num_rows > 0 ){
     
                    $JsonResponse['STATUS'] = 0;
                    $message = array (
                        'MSG_STATUS' => '0 Nome Categoria gia inserito',
                        'DATA' => "0"
                    );
        
                }else{
    
                    $insert = "INSERT INTO `CategoriaMenu`( `ID_Ristorante`, `NameCategory`) 
                    VALUES ($Id_Ristorante,'$NameCategory') ";
                   
                    if( $con->query($insert) == true ){
                        
                        $newCategory['ID_CategoriaMenu'] = $con->insert_id;
                        $newCategory['NomeCategoria'] = $NameCategory;
            
                        $JsonResponse['STATUS'] = 1;
                        $message = array (
                            'MSG_STATUS' => '1 Category Inserted',
                            'DATA' => $newCategory
                        );
            
                    }else{
                        
                        $JsonResponse['STATUS'] = 0;
                        $message = array (
                            'MSG_STATUS' => '0 Comando SQL non andato a buon fine',
                            'DATA' => "0"
                        );
            
                    }
    
                }
            }
    
    
            $con->close();
        }else{
            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => '0 Nome Categoria Vuota',
                'DATA' => "0"
            );
        }
        
    }

    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>