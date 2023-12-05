<?php
    include 'functions.php';
    include '../../db_connection.php';

    header('Content-Type: application/json; charset=utf-8');
    $JsonResponse['STATUS'] = 0;
    $messageResponse = [];
    error_reporting(E_ALL);
    ini_set('display_errors', 1);
    $con = new mysqli($host_name, $user_name, $password, $database);
    
    if ($con == false) {

        $JsonResponse['STATUS'] = -1;
        $message = array (
            'MSG' => 'Connessione DB Fallita',
            'DATA' => array()
        );

    }else {
        $ID_Category    = $_POST['ID_Category'];
        $Id_Restaurant  = $_POST['id_restaurant'];
        
        $sqlRestaruant = "SELECT * 
        FROM `CategoriaMenu` 
        WHERE `ID_Ristorante` <=> $Id_Restaurant  AND `ID_CategoryMenu` <=> $ID_Category;";
        $result = $con->query($sqlRestaruant);
        if($result->num_rows > 0 ){
            $sql = "SELECT * 
            FROM `Prodotto` 
            WHERE `ID_CategoryMenu` <=> $ID_Category  
            ORDER BY(`Posizione`);";
            
            if($result = $con->query($sql)){
    
                if($result->num_rows > 0 ){
                    $ProductsArray = [];
                    while($row = $result->fetch_array()){
                        
                        $Product = array (
                            'ID_Prodotto'       => $row['ID_Prodotto'],
                            'ID_CategoryMenu'   => $row['ID_CategoryMenu'],
                            'NameProdotto'      => $row['NameProdotto'],
                            'PriceProdotto'     => $row['PriceProdotto'],
                            'Description'       => $row['Description'],
                            'Allergeni'         => $row['Allergeni'],
                            'isSendToKitchen'   => $row['isSendToKitchen'],
                            'PhotoURL'          => $row['PhotoURL']
                        );
                        
                        array_push($ProductsArray, $Product);
                       
                    }
    
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG_STATUS' => '1 Products Trovati',
                        'DATA' => $ProductsArray
                    );
                    
                }else{
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG_STATUS' => '1 Nessun Product',
                        'DATA' => ""
                    );
                }
            }else{
    
                $JsonResponse['STATUS'] = 0;
                $message = array (
                    'MSG_STATUS' => '0 Error Id cat' ,
                    'DATA' => ""
                );
            }
    
            $con->close();
        }else{
            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => '0 Error Id restaurant' ,
                'DATA' => ""
            );
        }
        
    }

    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>