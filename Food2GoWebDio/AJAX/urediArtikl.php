<?php
require_once ("../Baza/Baza.php");
$baza = new Baza();
$id=$_POST['id'];
$naziv=$_POST['nazivArtikla'];
$cijena=$_POST['cijena'];
$kolicina= $_POST['kolicina'];
$opis = $_POST['opis'];
$kategorija =$_POST['kategorija'];
$baza->spojiDB();
$baza->updateDB("UPDATE Artikl SET Naziv='".$naziv."',Cijena='".$cijena."', KolicinaZaliha='".$kolicina."',Opis='".$opis."',KategorijaID='".$kategorija."' WHERE ID='".$id."'");
$baza->zatvoriDB();
echo "Artikl je ažuriran";
?>