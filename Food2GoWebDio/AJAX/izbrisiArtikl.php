<?php
require_once ("../Baza/Baza.php");
$baza= new Baza();
$baza->spojiDB();
$id=$_POST['id'];
$baza->updateDB("Delete from Artikl where ID='".$id."'");

$baza->zatvoriDB();
echo "Artikl je uklonjen.";