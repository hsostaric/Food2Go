<?php
require_once ("../Baza/Baza.php");
$baza = new Baza();
$id=$_POST['id'];

$baza->spojiDB();
$baza->updateDB("UPDATE Korisnik SET Tip_korisnikaID=1 WHERE ID='".$id."'");
$baza->zatvoriDB();
echo "Uloga je promijenjena.";
?>