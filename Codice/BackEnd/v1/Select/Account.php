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
        $Id_Ristorante = $_POST['ID_Ristorante'];
        $sql = "SELECT * 
        FROM `Ristorante` 
        WHERE `ID_Ristorante` <=> $Id_Ristorante ;";
        
        if($result = $con->query($sql)){

            $arrayJson['STATUS'] = 1;

            if($result->num_rows > 0 ){

                $RestaurantArray = [];
                while($row = $result->fetch_array()){
                    
                    $sql = "SELECT * 
                    FROM `Tavolo` 
                    WHERE `ID_Ristorante` <=> $Id_Ristorante ;";
                    
                    $nTavoli = 0;
                    if($result2 = $con->query($sql)){
                        
                        while($row2 = $result2->fetch_array()){
                            $nTavoli++;
                        }
                        
                    }

                    $restaurant = array (
                        'NameRistorante' => $row['NameRistorante'],
                        'AddressRistorante' => $row['AddressRistorante'],
                        'Phone' => $row['Phone'],
                        'ID_Ristorante' => $row['ID_Ristorante'],
                        'nTavoli' => $row['NrTavoli']
                    );
                    
                    array_push($RestaurantArray, $restaurant);
                   
                }

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '1 Ristorante Trovato',
                    'DATA' => $RestaurantArray
                );
            }else{
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG_STATUS' => '0 Nessun Ristorante',
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