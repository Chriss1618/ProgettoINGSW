<?php
    include 'functions.php';
    include '../../db_connection.php';
    
    header('Content-Type: application/json; charset=utf-8');
    $serverKey = 'AAAA66cuIhA:APA91bFeAtioj6qMVRVG4BIMIy_N7OPdBVb_KEvgOiQosr5kXCfPCkYL7mUwR6HDkOwjx5sH8zFZOzKzr15R7BTjO_zfUyGIApaUdsBDqeKXHQBn1TMch698DOntGGI2n7NisSo1m1fl';

    // Define the FCM endpoint
    $fcmEndpoint = 'https://fcm.googleapis.com/fcm/send';

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
        $Id_Ordine        = $_POST['Id_Ordine'];
        $nProducts        = $_POST['nProducts'];
        $Id_Ristorante    = $_POST['Id_Ristorante'];
        if($_POST['sendToKitchen'] === "SEND"){

            $sendToChef = true;
        }else{
            $sendToChef = false;
        }
        $ProdottiArray = [];
        for ($i = 0; $i < $nProducts; $i++) {
            $priceProduct = (float)$_POST['priceProduct' . $i];
        
            $Prodotto = array(
                'Id_Product' => $_POST['Id_Product' . $i],
                'priceProduct' => $priceProduct
            );
        
            array_push($ProdottiArray, $Prodotto);
        }
        $totalPrice = 0.0;

        $sqlOrdine = "SELECT Prezzo_Totale
        FROM Ordine
        WHERE `Id_Ordine` <=> $Id_Ordine ;";
        
        if($resultOrdine = $con->query($sqlOrdine)){
            
            if($resultOrdine->num_rows > 0 ){
                $rowOrdine = $resultOrdine->fetch_array();
                $totalPrice = $rowOrdine['Prezzo_Totale'];
            }
        }

        for( $i = 0; $i < $nProducts ; $i++){
            $ID_Product = $ProdottiArray[$i]['Id_Product'];
            $insert = "INSERT INTO `ProdottoOrdinato`( `ID_Ordine`, `ID_Prodotto` ) VALUES ($Id_Ordine,$ID_Product)";
            $result = $con->query($insert); 

            if (isset($ProdottiArray[$i]['priceProduct'])) {
                $totalPrice += (float)$ProdottiArray[$i]['priceProduct']; // Convert to float if necessary
            }
        }
        
        $PriceUpdated = false;
        $updateSql = "UPDATE Ordine SET Prezzo_Totale = ? WHERE ID_Ordine = ?";
        $stmt = $con->prepare($updateSql);
        
        if ($stmt) {
            $stmt->bind_param("di", $totalPrice, $Id_Ordine);
            if ($stmt->execute()) {
                $PriceUpdated = true;
            }
            $stmt->close();
        }

        if( $result == true ){
            $JsonResponse['STATUS'] = 1;
            $message = array(
                'MSG_STATUS' => '1 Products Ordered',
                'DATA' => $PriceUpdated
            );
        }else{
            $JsonResponse['STATUS'] = 0;
            $message = array(
                'MSG_STATUS' => '0 Products Ordered',
                'DATA' => 'not OK'
            );
        }
        if($sendToChef){
            $sql = "SELECT * FROM `Utente` 
            WHERE ID_Ristorante = '$Id_Ristorante' AND Type_User = 'Chef'";
           
            if( $result = $con->query($sql) ){ 
                
                
                if($result->num_rows > 0 ){
    
                    $DataArray = [];
                    while($row = $result->fetch_array()){
                        $Account = array (
                            'ID_Utente'     => $row['ID_Utente'],
                            'ID_Ristorante' => $row['ID_Ristorante'],
                            'Nome'          => $row['Nome'],
                            'Cognome'       => $row['Cognome'],
                            'Token'         => $row['Token'],
                            'Type_User'     => $row['Type_User']
                        );
                        array_push($DataArray, $Account);
                        
                        // Create a notification payload
                        $token = $Account['Token'];
                        $messageFCM = [
                            'data' => [
                                'key1' => 'Forza',
                                'key2' => 'Scritto da BackEnd'
                            ],
                            'to' => $token
                        ];
    
                        // Encode the payload to JSON
                        $jsonMessage = json_encode($messageFCM);
    
                        // Set up the HTTP headers
                        $headers = [
                            'Authorization: key=' . $serverKey,
                            'Content-Type: application/json'
                        ];
    
                        // Initialize cURL session
                        $ch = curl_init();
    
                        // Set the cURL options
                        curl_setopt($ch, CURLOPT_URL, $fcmEndpoint);
                        curl_setopt($ch, CURLOPT_POST, true);
                        curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
                        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
                        curl_setopt($ch, CURLOPT_POSTFIELDS, $jsonMessage);
    
                        // Execute the cURL session
                        $response = curl_exec($ch);
    
                        // Check for cURL errors
                        if (curl_errno($ch)) {
                            
                        } else {
                            // Close cURL session
                            curl_close($ch);
    
                            // Print the FCM response
                            
                        }
    
                    }
    
                }else{
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG' => '0 Credenziali Errate',
                        'DATA' => ""
                    );
                }
    
            }
        }
        
    
    }

    array_push($messageResponse, $message);

    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>