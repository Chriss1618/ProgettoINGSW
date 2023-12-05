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
            'MSG' => '0 Connessione DB Fallita',
            'DATA' => array()
        );

    } else {

        if (isset($_POST['ID_Utente'])) {
           
            $ID_Utente = $_POST['ID_Utente'];

            $delete = "DELETE FROM ProdottoOrdinato WHERE ID_Utente = $ID_Utente";
            $con->query($delete);
            
            $delete = "DELETE FROM `Utente` 
            WHERE ID_Utente = $ID_Utente";

            $con->query($delete);

            if ($con->affected_rows > 0) {

                $deleted['UtenteDeleted'] = true;

                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG' => '1 Utente Eliminato',
                    'DATA' => $deleted
                );

            } else {
                $JsonResponse['STATUS'] = 1;
                $message = array (
                    'MSG' => '0 Failed Deleting',
                    'DATA' => $con->error
                );
            }
           
        } else {
            $JsonResponse['STATUS'] = -1;
            $message = array (
                'MSG' => 'utente non passato',
                'DATA' => ''
            );

        }
        
        $con->close();
        
    }
    
    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>