<?php
    include 'functions.php';
    
    $serverKey = "AAAA5YELVfs:APA91bGR4uA2JqAOJmMNXMfmtbb08tBH6KOZmoNf1YXUrH3WDjPS9OZ94ca60dc2HFcT0m0EXpvO-cRx0_vbwQXuyTEO9Q-_3jKjWpZ22zLim9nJR6V4Pi5R3AsDGSkJtqodIb3ydEJQ";

    $deviceToken = $_POST['token'];

    // Set the FCM API endpoint
    $url = 'https://fcm.googleapis.com/fcm/send';
    
    $data = array(
        'key1' => 'porcoDio',
        'key2' => '1'
    );

    $fields = json_encode( array(
        'to' => $deviceToken,
        'data' => $data
    ));

    // Set the request headers
    $headers = array(
        'Authorization: key=' . $serverKey,
        'Content-Type: application/json'
    );

    // Initialize cURL
    $curl = curl_init();

    // Set the cURL options
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_POST, true);
    curl_setopt($curl, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($curl, CURLOPT_POSTFIELDS, $fields);
    
    // Execute the cURL request
    $response = curl_exec($curl);
    curl_close($curl);
    
    //END COMUNICATION *******************

    $arrayJson['status'] = 1;

    $messageArray = [];
    $message = array (
        'token' => $deviceToken
    );
    array_push($messageArray, $message);
    $arrayJson['msg'] = $messageArray;

    echo json_encode($arrayJson);
    
?>