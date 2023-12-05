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
        $nomeIngrediente= $_POST['NameIngredient'];
        $Id_Ristorante  = $_POST['id_ristorante'];
        $Misura         = $_POST['Misura'];
        $PhotoDATA      = $_POST['PhotoDATA'];
        $PhotoURLSENT   = $_POST['PhotoURL'];
        $UnitaMisura    = $_POST['UnitaMisura'];
        $Quantita       = $_POST['Quantita'];
        $Price          = $_POST['Prezzo'];
        $Description    = $_POST['Description'];

        if($PhotoURLSENT != "NoURL"){
            $insert = "INSERT INTO `Ingrediente`( `ID_Ristorante`, `NameIngrediente`, `Description`, `Price`, `Misura`, `PhotoURL`, `UnitaMisura`, `Quantita`)  
                                      VALUES( $Id_Ristorante, '$nomeIngrediente', '$Description', $Price, '$Misura', '$PhotoURLSENT', '$UnitaMisura', '$Quantita') ";

            if( $con->query($insert) == true ){
                $newIngredient['ID_Ingredient']     = $con->insert_id;
                $newIngredient['NameIngredient']    = $nomeIngrediente;
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Ingredient Inserted',
                    'DATA' => $newIngredient
                );

            }else{
                
                $JsonResponse['STATUS'] = 0;
                $message = array (
                    'MSG_STATUS' => '0 Comando SQL non andato a buon fine',
                    'DATA' => $con->error
                );

            }
        }else{
            if($PhotoDATA != "NoPhoto"){
                $PhotoURL = date("d-m-Y").'-'.time().''.rand(1000,1000).'.jpg';
                file_put_contents('../../Images/Ingredient/'.$PhotoURL,
                base64_decode($PhotoDATA));
            }else{
                $PhotoURL = "null";
            }

            $insert = "INSERT INTO `Ingrediente`( `ID_Ristorante`, `NameIngrediente`, `Description`, `Price`, `Misura`, `PhotoURL`, `UnitaMisura`, `Quantita`)  
                                      VALUES( $Id_Ristorante, '$nomeIngrediente', '$Description', $Price, '$Misura', '$PhotoURL', '$UnitaMisura', '$Quantita') ";

            if( $con->query($insert) == true ){
                $newIngredient['ID_Ingredient']     = $con->insert_id;
                $newIngredient['NameIngredient']    = $nomeIngrediente;
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Ingredient Inserted',
                    'DATA' => $newIngredient
                );

            }else{
                
                $JsonResponse['STATUS'] = 0;
                $message = array (
                    'MSG_STATUS' => '0 Comando SQL non andato a buon fine',
                    'DATA' => $con->error
                );

            }
        }

        
       
    }
    

    array_push($messageResponse, $message);


    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>