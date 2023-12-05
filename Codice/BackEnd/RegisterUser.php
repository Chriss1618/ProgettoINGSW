<?php
    include 'functions.php';
    include 'db_connection.php';
    
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

    }else {

        //DEFENDING against sql injection using real escape string 
        //sostituisce tutti caratteri speciali che possono compromettere il DB
        $Nome       = $_POST['Nome'];
        $Token      = $_POST['Token'];
        $TypeUser   = $_POST['TypeUser'];
        $Cognome    = $_POST['Cognome'];
        $Email      = $_POST['Email'];
        $Password   = $_POST['Password'];
         
        $sql = "SELECT * FROM `Utente` 
        WHERE `Email` = '$Email'";
        $result = $con->query($sql);
       
        if( $result->num_rows > 0 ){ 
            $JsonResponse['STATUS'] = 1;
            $message = array (
                'MSG' => '0 Utente già Registrato con questa Email!',
                'DATA' => ""
            );
        }else{
            $insert = "INSERT INTO `Ristorante`( `NameRistorante`, `AddressRistorante`, `Phone`) VALUES ('','','')";

            if( $con->query($insert) == true ){

                $Id_Ristorante = $con->insert_id;
                
                $insert = "INSERT INTO `Utente`( `Nome`, `Cognome`, `Type_User`, `Email`,`Password`,`Token`, `ID_Ristorante`) 
                                        VALUES ('$Nome','$Cognome','$TypeUser','$Email','$Password','$Token',$Id_Ristorante);";

                if( $con->query($insert) == true ){

                    $Id_Utente = $con->insert_id;

                    $JsonResponse['STATUS'] = 1;

                    $NewAccount['Nome']         = $Nome;
                    $NewAccount['Cognome']      = $Cognome;
                    $NewAccount['TypeUser']     = $TypeUser;
                    $NewAccount['Email']        = $Email;
                    $NewAccount['Password']     = $Password;
                    $NewAccount['Token']        = $Token;
                    $NewAccount['Id_Ristorante']= $Id_Ristorante;
                    $NewAccount['Id_Utente']    = $Id_Utente;

                    
                    $JsonResponse['STATUS'] = 1;
                    $message = array (
                        'MSG' => '1 Amministratore Inserted',
                        'DATA' => $NewAccount
                    );

                }else{
                    
                    $JsonResponse['STATUS'] = 0;
                    $message = array (
                        'MSG' => '0 Comando SQL non andato a buon fine',
                        'DATA' => $con->error
                    );

                }

            }else{
                
                $JsonResponse['STATUS'] = 0;
                $message = array (
                    'MSG' => '0 Comando SQL non andato a buon fine',
                    'DATA' => $con->error
                );

            }
        }
        
        $con->close();
    }

    array_push($messageResponse, $message);
    $JsonResponse['BODY'] = $messageResponse;
    echo json_encode($JsonResponse);
?>