<?php

require_once ("../Baza/Baza.php");
$baza = new Baza();

$naziv=$_POST['nazivArtikla'];
$cijena=$_POST['cijena'];
$kolicina= $_POST['kolicina'];
$opis = $_POST['opis'];
$kategorija =$_POST['kategorija'];
$baza->spojiDB();

$baza->updateDB("Insert into Artikl values (DEFAULT , '".$naziv."', '".$cijena."','".$kolicina."',1,'".$opis."','".$kategorija."')");

$baza->zatvoriDB();
echo "Artikl je dodan";