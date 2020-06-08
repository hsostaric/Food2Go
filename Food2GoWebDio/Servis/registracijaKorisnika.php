<?php
include("../Baza/Baza.php");

$naziv=$_GET['data1'];
$tipKorisnika=$_GET['data2'];

$veza = new Baza();
$veza->spojiDB();
$upit="INSERT INTO Tip_korisnika values (DEFAULT,'$naziv','$tipKorisnika')";
$veza->updateDB($upit);
$veza->zatvoriDB();
$status1="OK";
$response->status=$status1;
$myJSON = json_encode($response);
echo $myJSON;

?>

