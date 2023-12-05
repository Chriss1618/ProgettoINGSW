<?php
    //include 'functions.php';
    //include '../../db_connection.php';
    
    use PHPMailer\PHPMailer\PHPMailer;
    use PHPMailer\PHPMailer\SMTP;
    use PHPMailer\PHPMailer\Exception;

    require 'PHPMailer/src/Exception.php';
    require 'PHPMailer/src/PHPMailer.php';
    require 'PHPMailer/src/SMTP.php';

    
    function sendMail( $to,$passwordUser  ) {
        $htmlBody = "<!DOCTYPE html>
        <html lang='en'>
        <head>
            <meta charset='UTF-8'>
            <meta name='viewport' content='width=device-width, initial-scale=1.0'>
            <title>Email di Benvenuto</title>
        </head>
        <body style='text-align: center; background-color: #f0f0f0; padding: 20px; font-family: Arial, sans-serif;'>
        
            <img src='http://s956013630.sito-web-online.it/App/Images/ratatouille.png' alt='Logo Ratatouille' style='max-width: 50%; height: auto;'>
        
            <h1>Benvenuto in Ratatouille!</h1>
        
            <p>Il tuo amministratore ha creato un account per permetterti di accedere all'applicazione Ratatouille23.</p>
        
            <p>La tua password &egrave: <strong> $passwordUser</strong></p>
        
            <p>Grazie per aver scelto Ratatouille!</p>
        
        </body>
        </html>
        ";

        try {
            // ... il tuo codice di invio email ...
        
            $mail = new PHPMailer(true);
            $mail->isSMTP();
            $mail->Host = 'smtp.gmail.com';
            $mail->SMTPAuth = true;
            $mail->Username ='ingswteam@gmail.com';
            $mail->Password = 'yqgr kblk wovx txlz';
            $mail->SMTPSecure = 'ssl';
            $mail->Port = 465;

            $mail->setFrom('ingswteam@gmail.com');
            $mail->addAddress($to);
            $mail->Subject = 'Credenziali per Ratatouille';
            $mail->isHTML(true);
            $mail->Body = $htmlBody; 
            $mail->send();
        } catch (Exception $e) {
        }
        
    }
?>