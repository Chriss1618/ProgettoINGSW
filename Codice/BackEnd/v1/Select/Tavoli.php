<?php
include 'functions.php';
include '../../db_connection.php';
ini_set('display_errors', 1);
ini_set('display_startup_errors', 1);
error_reporting(E_ALL);

header('Content-Type: application/json; charset=utf-8');

$JsonResponse['STATUS'] = 0;
$messageResponse = [];

$con = new mysqli($host_name, $user_name, $password, $database);
if ($con == false) {
    $JsonResponse['STATUS'] = -1;
    $message = array(
        'MSG_STATUS' => '0 Connessione DB Fallita',
        'DATA' => array()
    );

} else {
    $ID_Restaurant      = $_POST['ID_Ristorante'];

    $sql = "SELECT  *  FROM Tavolo WHERE ID_Ristorante = $ID_Restaurant";
    
    if ($result = $con->query($sql)) {

        $tavoliArray = [];
        while($row = $result->fetch_array()){
            
            $Tavolo = array (
                'ID_Tavolo'         => $row['ID_Tavolo'],
                'ID_Ristorante'     => $row['ID_Ristorante'],
                'State_Tavolo'      => $row['State_Tavolo'],
                'Numero_tavolo'     => $row['Numero_tavolo']
            );
        
            array_push($tavoliArray, $Tavolo);
           
        }


        $JsonResponse['STATUS'] = 1;
        $message = array(
            'MSG_STATUS' => '1 Tavoli trovati',
            'DATA' => $tavoliArray
        );
    }else{
        $JsonResponse['STATUS'] = 1;
        $message = array(
            'MSG_STATUS' => '0 Errore sql',
            'DATA' => ''
        );
    }

        
    
}

array_push($messageResponse, $message);

$JsonResponse['BODY'] = $messageResponse;
echo json_encode($JsonResponse);
?>
