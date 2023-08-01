<?php
$url = 'http://your-android-app.com/api-endpoint';
$data = array('key1' => 'value1', 'key2' => 'value2');

$options = array(
  'http' => array(
    'method' => 'POST',
    'header'  => 'Content-type: application/x-www-form-urlencoded',
    'content' => http_build_query($data),
  ),
);

$context  = stream_context_create($options);
$result = file_get_contents($url, false, $context);

if ($result === false) {
  // Handle error
} else {
  // Handle success
}
?>