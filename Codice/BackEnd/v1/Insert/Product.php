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
        $ID_category        = $_POST['ID_category'];
        $NameProduct        = $_POST['NameProduct'];
        $PhotoDATA          = $_POST['PhotoDATA'];
        $PhotoURLSENT       = $_POST['PhotoURL'];
        $PriceProduct       = $_POST['PriceProduct'];
        $DescriptionProduct = $_POST['DescriptionProduct'];
        $AllergeniProduct   = $_POST['AllergeniProduct'];
        $isSendToKitchen    = $_POST['isSendToKitchen'];
        $nIngredients = $_POST['nIngredient'];
        $RicettarioArray = [];
        for( $i = 0; $i < $nIngredients ; $i++){
            $Ricettario = array (
                'ID_Ingrediente' => $_POST['ID_Ingrediente'.$i],
                'Dosi' => $_POST['Dosi'.$i],
                'TypeMeasure' => $_POST['TypeMeasure'.$i],
            );
            array_push($RicettarioArray,$Ricettario);
        }
        
        if($PhotoURLSENT != "NoURL"){
            $PhotoURL = $PhotoURLSENT;
        }else{
            if($PhotoDATA != "NoPhoto"){
                $PhotoURL = date("d-m-Y").'-'.time().''.rand(1000,1000).'.jpg';
                file_put_contents('../../Images/Product/'.$PhotoURL,
                base64_decode($PhotoDATA));
            }else{
                $PhotoURL = "null";
            }
        }
        
        $toKitchen = 0;
        if($isSendToKitchen == "true"){
            $toKitchen = 1;
        }
        $insert = "INSERT INTO `Prodotto`( `ID_CategoryMenu`, `NameProdotto`, `PriceProdotto`, `Description`, `Allergeni`, `isSendToKitchen`, `PhotoURL`) 
                                    VALUES ( $ID_category ,'$NameProduct', $PriceProduct ,'$DescriptionProduct','$AllergeniProduct ', $toKitchen, '$PhotoURL ')";

        if( $con->query($insert) == true ){
            $ID_Prodotto    = $con->insert_id;
            foreach ($RicettarioArray as $ingredient) {
                $ID_Ingrediente     = $ingredient['ID_Ingrediente'];
                $Dosi               = $ingredient['Dosi'];
                $TypeMeasure        = $ingredient['TypeMeasure'];

                $insert2 = "INSERT INTO `Ricettario` (`ID_Prodotto`, `ID_Ingrediente`, `Dosi`, `UnitaMisura`) 
                                            VALUES ($ID_Prodotto, $ID_Ingrediente, $Dosi, '$TypeMeasure')";
                $result = $con->query($insert2); 
            }
        
            if( $result === true ){

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 New Product Inserted',
                    'DATA' => 'Ok'
                );

            }else{
                
                $JsonResponse['STATUS'] = 0;
                $message = array (
                    'MSG_STATUS' => '1 New Product Inserted',
                    'DATA' => 'OK but not Ricettario'
                );

            }
        }
    
    }

    array_push($messageResponse, $message);

    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>