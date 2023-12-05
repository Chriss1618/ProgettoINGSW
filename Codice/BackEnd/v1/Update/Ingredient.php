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
        $ID_Ingredient  = $_POST['ID_Ingredient'];
        $Misura         = $_POST['Misura'];
        $PhotoDATA      = $_POST['PhotoDATA'];
        $PhotoURLpassed = $_POST['PhotoURL'];
        $UnitaMisura    = $_POST['UnitaMisura'];
        $Quantita       = $_POST['Quantita'];
        $Price          = $_POST['Prezzo'];
        $Description    = $_POST['Description'];

        // $nomeIngrediente= 'Ciao';
        // $Id_Ristorante  = 1;
        // $ID_Ingredient  = 2;
        // $Misura         = 20;
        // $PhotoDATA      = '1313';
        // $PhotoURLpassed = '23-08-2023-16927964111000.jpg';
        // $UnitaMisura    = 'L';
        // $Quantita       = 300;
        // $Price          = 2.3;
        // $Description    = 'Descrizione a caso';

        if($PhotoDATA != "NoPhoto"){
            unlink('../../Images/Ingredient/'.$PhotoURLpassed);
            $PhotoURL = date("d-m-Y").'-'.time().''.rand(1000,1000).'.jpg';
            file_put_contents('../../Images/Ingredient/'.$PhotoURL,
                base64_decode($PhotoDATA));
        }else{
            $PhotoURL = $PhotoURLpassed;
        }

        $update = "UPDATE `Ingrediente` SET `NameIngrediente`='$nomeIngrediente', `Description`='$Description', `Price`=$Price, `Misura`='$Misura', `PhotoURL`='$PhotoURL', `UnitaMisura`='$UnitaMisura', `Quantita`=$Quantita WHERE  `ID_Ingrediente` = $ID_Ingredient";

        if( $con->query($update) == true ){
            $newIngredient['id_ristorante']     = $Id_Ristorante;
            $newIngredient['ID_Ingredient']     = $ID_Ingredient;
            $newIngredient['NameIngredient']    = $nomeIngrediente;
            $newIngredient['PhotoURL']          = $PhotoURL;
            $newIngredient['Misura']            = $Misura;
            $newIngredient['UnitaMisura']       = $UnitaMisura;
            $newIngredient['Quantita']          = $Quantita;
            $newIngredient['Price']             = $Price;
            $newIngredient['Description']       = $Description;

            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG_STATUS' => '1 Ingredient Updated',
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
    

    array_push($messageResponse, $message);


    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>