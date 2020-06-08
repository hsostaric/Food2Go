<?php
include ("../Baza/Baza.php");

function AktivirajRacun(){
    $mail=$_POST['email'];
    $kod= $_POST['kod'];
    $veza = new Baza();
    $veza->spojiDB();
    $upit="UPDATE Korisnik set StatusKorisnika=3 WHERE  Email ='$mail' AND Aktivacijski_kod='$kod'";
    $veza->updateDB($upit);
    $veza->zatvoriDB();
}

?>