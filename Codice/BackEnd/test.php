<?php
  $host_name = 'db5012461742.hosting-data.io';
  $database = 'dbs10477487';
  $user_name = 'dbu1834306';
  $password = 'DB_ingsw2023';
  
  include 'functions.php';

  $con = new mysqli($host_name, $user_name, $password, $database);
  
  if ($con == false) {
    echo('<p>Collegamento al server MySQL non riuscito: '. $con->connect_error .'</p>');
  }else {
        $sql = "SELECT * FROM studente";
        
        if($result = $con->query($sql)){
            if($result->num_rows > 0 ){
                //echo $result->num_rows;
                while($row = $result->fetch_array()){
                    //$message =   $row['nome']. ' '. $row['cognome'];
                    $message = array (
                        'nome' => $row['nome'],
                        'cognome' => $row['cognome'],
                        'sticazzi' => 666
                    );
                    $arrayJson = array (
                        'value' => 2,
                        'message' => $message
                    );

                    $arrayJson['messageFromAndroid'] = $_POST['nome'].' '.$_POST['cognome'];
                }
            }else{
                echo "Non ci sono elementi";
            }
        }else{
            echo "Errore eseguire la query $sql." .$con->error;
        }

        $con->close();

        header('Content-Type: application/json; charset=utf-8');
        
        echo json_encode($arrayJson);
    }
?>