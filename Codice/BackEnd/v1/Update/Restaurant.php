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
    $ID_Restaurant      = $_POST['ID_Restaurant'];
    $NameRistorante     = $_POST['NameRistorante'];
    $AddressRistorante  = $_POST['AddressRistorante'];
    $Phone              = $_POST['Phone'];
    $NrTavoli           = $_POST['NrTavoli'];
    
    $Nome               = $_POST['Nome'];
    $Cognome            = $_POST['Cognome'];
    $ID_Utente          = $_POST['ID_Utente'];

    $sql = "SELECT * 
    FROM `Tavolo` 
    WHERE `ID_Ristorante` <=> $ID_Restaurant ;";
    
    $NrTavoliOLD = 0;

    $sql = "SELECT COUNT(*) AS TotalTavoli FROM Tavolo WHERE ID_Ristorante = $ID_Restaurant";
    $result = $con->query($sql);
    if ($result && $row = $result->fetch_assoc()) {
        $NrTavoliOLD = $row['TotalTavoli'];
        if ($NrTavoliOLD < $NrTavoli) {
            // Insert new tables
            for ($i = $NrTavoliOLD; $i < $NrTavoli; $i++) {
                $NumeroTavolo = $i + 1;
                $insert2 = "INSERT INTO `Tavolo` ( `Numero_tavolo`, `ID_Ristorante`) VALUES ('$NumeroTavolo', $ID_Restaurant)";
                $result2 = $con->query($insert2);
                
            }
        }
    }
    // Update the Ristorante table
    $update = "UPDATE `Ristorante` 
    SET `NameRistorante`='$NameRistorante', `AddressRistorante`='$AddressRistorante', `Phone`='$Phone', `NrTavoli`= $NrTavoli 
    WHERE `ID_Ristorante` = $ID_Restaurant";

    if ($con->query($update) === true) {

        $update = "UPDATE `Utente` 
        SET `Nome`='$Nome', `Cognome`='$Cognome'
        WHERE `ID_Utente` = $ID_Utente";
    
        if ($con->query($update) === true) {
            $JsonResponse['STATUS'] = 1;
            $message = array(
                'MSG_STATUS' => '1 Restaurant updated AND Utente',
                'DATA' => 'Ok'
            );
        }else{
            $JsonResponse['STATUS'] = 1;
            $message = array(
                'MSG_STATUS' => '1 Restaurant updated NOT Utente',
                'DATA' => 'Ok'
            );
        }

        
    } else {
        $JsonResponse['STATUS'] = 0;
        $message = array(
            'MSG_STATUS' => '0 Restaurant NOT Updated',
            'DATA' => ''
        );
    }
    
}

array_push($messageResponse, $message);

$JsonResponse['BODY'] = $messageResponse;
echo json_encode($JsonResponse);
?>
