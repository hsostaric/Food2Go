<?php
include ("../Baza/Baza.php");

function ResetirajLozinku(){
    $mail=$_POST['mail'];
    $username=$_POST['username'];
    $lozinka=GenerirajLozinku();
    $veza = new Baza();
    $veza->spojiDB();
    $upit="UPDATE Korisnik set Lozinka='$lozinka' where Email='$mail' AND KorisnickoIme='$username'";
    $veza->updateDB($upit);
    $veza->zatvoriDB();
    PosaljiLozinku($lozinka);
    
}
function GenerirajLozinku(){
    $code1 = md5("ZrnoSoli" . microtime());
    $code = substr($code1,0,8);
    return $code;
}
function PosaljiLozinku($lozinka){
    $kome=$_POST["mail"];
    $naslov="Nova lozinka";
    $sadrzaj="Vasa nova lozinka je: ".$lozinka." i mozete je izmijeniti nakon prijave!";
    $od="From: igradiski@foi.hr";
    mail($kome,$naslov, $sadrzaj, $od);

}



?>