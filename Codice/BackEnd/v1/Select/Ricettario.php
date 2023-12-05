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
            'MSG' => 'Connessione DB Fallita',
            'DATA' => array()
        );

    }else {
        $ID_Product = $_POST['ID_Product'];
        $sql = "SELECT * 
        FROM `Ricettario` 
        WHERE `ID_Prodotto` <=> $ID_Product ;";
        
        if($result = $con->query($sql)){

            if($result->num_rows > 0 ){
                $RicettarioArray = [];

                while($row = $result->fetch_array()){

                    $Ricettario['Dosi'] = $row['Dosi'];
                    $Ricettario['UnitaMisura'] = $row['UnitaMisura'];
                    
                    $idIngrediente = $row['ID_Ingrediente'];
                    $sql = "SELECT * 
                    FROM `Ingrediente` 
                    WHERE `ID_Ingrediente` <=> $idIngrediente ;";

                    if($result2 = $con->query($sql)){
                        $rowIngredient = $result2->fetch_array();
                        $Ingredient = array (
                            'ID_Ingrediente' => $rowIngredient['ID_Ingrediente'],
                            'ID_Ristorante' => $rowIngredient['ID_Ristorante'],
                            'NameIngrediente' => $rowIngredient['NameIngrediente'],
                            'Description' => $rowIngredient['Description'],
                            'Price' => $rowIngredient['Price'],
                            'Misura' => $rowIngredient['Misura'],
                            'PhotoURL' => $rowIngredient['PhotoURL'],
                            'UnitaMisura' => $rowIngredient['UnitaMisura'],
                            'Quantita' => $rowIngredient['Quantita'],
                        );
                        $Ricettario['Ingrediente'] = $Ingredient;
                    }
                    array_push($RicettarioArray,$Ricettario);
                }   

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Ricettari Trovati',
                    'DATA' => $RicettarioArray
                );
            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Ricettari Vuoto',
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