<?php
    include 'functions.php';
    include '../../db_connection.php';
    //include '../../sendemail/SendEmail.php';
    //sendMail();
    
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
        $Id_Ristorante = $_POST['id_ristorante'];
        $sql = "SELECT * 
        FROM `Ingrediente` 
        WHERE `ID_Ristorante` <=> $Id_Ristorante ;";
        
        if($result = $con->query($sql)){

            if($result->num_rows > 0 ){
                $IngredientsArray = [];
                while($row = $result->fetch_array()){
                    
                    $Ingredient = array (
                        'ID_Ingrediente' => $row['ID_Ingrediente'],
                        'ID_Ristorante' => $row['ID_Ristorante'],
                        'NameIngrediente' => $row['NameIngrediente'],
                        'Description' => $row['Description'],
                        'Price' => $row['Price'],
                        'Misura' => $row['Misura'],
                        'PhotoURL' => $row['PhotoURL'],
                        'UnitaMisura' => $row['UnitaMisura'],
                        'Quantita' => $row['Quantita']
                    );
                    
                    array_push($IngredientsArray, $Ingredient);
                   
                }

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Ingredienti Trovati',
                    'DATA' => $IngredientsArray
                );
                
            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '0 Nessun Ingredient',
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