<?php

include ("../Baza/Baza.php");

$ID = $_GET['data'];
$veza = new Baza();
$veza->spojiDB();
$upit ="DELETE FROM Kategorija WHERE ID='{$_GET['data']}'";
$veza->updateDB($upit);
$veza->zatvoriDB();
echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/Kategorije.php'; </script>";
?>