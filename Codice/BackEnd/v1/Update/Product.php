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
        $ID_Product         = $_POST['ID_Product'];
        $NameProduct        = $_POST['NameProduct'];
        $PhotoDATA          = $_POST['PhotoDATA'];
        $PhotoURL_OLD       = $_POST['PhotoURL'];
        $PriceProduct       = $_POST['PriceProduct'];
        $DescriptionProduct = $_POST['DescriptionProduct'];
        $AllergeniProduct   = $_POST['AllergeniProduct'];
        $isSendToKitchen    = $_POST['isSendToKitchen'];
        $nIngredients       = $_POST['nIngredient'];
        
        //LETTURA RICETTARio
 
        if( $NameProduct != "" AND $NameProduct != null  AND $NameProduct != "null" ){
             
            $RicettarioArray = [];
            for( $i = 0; $i < $nIngredients ; $i++){
                $Ricettario = array (
                    'ID_Ingrediente' => $_POST['ID_Ingrediente'.$i],
                    'Dosi' => $_POST['Dosi'.$i],
                    'TypeMeasure' => $_POST['TypeMeasure'.$i],
                );
                array_push($RicettarioArray,$Ricettario);
            }
        
            //SALVO NUOVA FOTO
            if($PhotoDATA != "NoPhoto"){
                unlink('../../Images/Product/'.$PhotoURL_OLD);
                $PhotoURL = date("d-m-Y").'-'.time().''.rand(1000,1000).'.jpg';
                file_put_contents('../../Images/Product/'.$PhotoURL, base64_decode($PhotoDATA));
            }else{
                $PhotoURL = $PhotoURL_OLD;
            }
    
            $toKitchen = 0;
            if($isSendToKitchen == "true"){
                $toKitchen = 1;
            }
    
    
            $update = "UPDATE `Prodotto` 
            SET `NameProdotto` = '$NameProduct', `PriceProdotto` = '$PriceProduct' , `Description` = '$DescriptionProduct', `Allergeni` = '$AllergeniProduct', `isSendToKitchen` = $toKitchen, `PhotoURL` = '$PhotoURL ' 
            WHERE  `ID_Prodotto` = $ID_Product"; 

            $recieved['NameProduct']    = $NameProduct;       
            $recieved['PriceProduct']   = $PriceProduct; 
            $recieved['DescriptionProduct'] = $DescriptionProduct; 
            $recieved['Allergeni']  = $AllergeniProduct; 
            $recieved['toKitchen']  = $toKitchen; 
            $recieved['PhotoURL']   = $PhotoURL; 
            $recieved['ID_Product'] = $ID_Product;   

            $con->query($update);
            if( $con->affected_rows > 0 ){
                
                $delete = "DELETE FROM `Ricettario` 
                WHERE `ID_Prodotto` = $ID_Product  ";
    
                $result = $con->query($delete); 
            
                foreach ($RicettarioArray as $ingredient) {
                    $ID_Ingrediente     = $ingredient['ID_Ingrediente'];
                    $Dosi               = $ingredient['Dosi'];
                    $TypeMeasure        = $ingredient['TypeMeasure'];
    
                    $insert2 = "INSERT INTO `Ricettario` (`ID_Prodotto`, `ID_Ingrediente`, `Dosi`, `UnitaMisura`) 
                                                VALUES ( $ID_Product, $ID_Ingrediente, $Dosi, '$TypeMeasure')";
                    $result2 = $con->query($insert2); 
                }
            
                if( $result2 === true ){
    
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG_STATUS' => '1 Product updated Inserted',
                        'DATA' => 'Ok'
                    );
    
                }else{
                    
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG_STATUS' => '1 Product updated Inserted',
                        'DATA' => 'OK but not Ricettario'
                    );
    
                }
            }else{
                $JsonResponse['STATUS'] = 0;
                $message = array (
                    'MSG_STATUS' => '0 Product NOT updated',
                    'DATA' => $recieved
                );
            }
        }else{
            $JsonResponse['STATUS'] = 0;
            $message = array (
                'MSG_STATUS' => '0 Product Empty',
                'DATA' => ''
            );
        }
        
    
    }

    array_push($messageResponse, $message);

    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>