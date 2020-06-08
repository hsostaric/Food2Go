<?php
include ("../Baza/Baza.php");

function stvoriAktivacijskiKod(){
    $code1 = md5("ZrnoSoli" . microtime());
    $code = substr($code1,0,7);
    return $code;
}

function posaljiKod($akt){
    $kome=$_POST["inputEmail"];
    $naslov="Aktivacijski kod";
    $sadrzaj="Uspjesno ste registrirani, vas registracijski kod je ".$akt." i traje 24 sata";
    $od="From: igradiski@foi.hr";
    mail($kome,$naslov, $sadrzaj, $od);

}

function provjeriIme(){
    if(empty($_POST["inputName"] )){
        return false;
    }
    return true;
}

function provjeriPrezime(){
    if(empty($_POST["inputSurname"])){
        return false;
    }
    return true;
}

function provjeriMail(){
    $regex="/^(?=^.{10,30}$)(?=^[A-Za-z0-9])[A-Za-z0-9\.]+@[A-Za-z0-9]+\.[A-Za-z]{2,}$/";
    if(empty($_POST["inputEmail"])){
        return false;
    }
    if(!preg_match($regex, $_POST["inputEmail"])){
        return false;
    }
    return true;
}
function provjeriLozinku(){
    $passwordFirst=$_POST["inputPassword"];
    $passwordSecond=$_POST["inputRepeatPassword"];
    $regex="/^([a-zA-z0-9]){5,20}$/";
    if(empty($passwordFirst) || !preg_match($regex, $passwordFirst) || ($passwordFirst!==$passwordSecond) ){
        return false;
    }
    else {
        return true;
    }
}
function provjeriOIB(){
    $oib=$_POST["inputOIB"];
    if(11==strlen($oib)){
        return true;
    }
    else{
        return false;
    }
}

function registrirajKorisnika(){
    if(  provjeriLozinku() && provjeriMail() &&  provjeriOIB()  && provjeriPrezime() && provjeriIme()){
        $veza = new Baza();
        $veza->spojiDB();
        $aktivacijskiKod = stvoriAktivacijskiKod();
        posaljiKod($aktivacijskiKod);
       $upit1="INSERT INTO Korisnik values(
        DEFAULT,
        '{$_POST['inputName']}',
        '{$_POST['inputSurname']}',
        '{$_POST['inputUsername']}',
        '{$_POST['inputPassword']}',
        '{$_POST['inputOIB']}',
        '{$_POST['inputEmail']}',
        '{$_POST['inputAdresa']}',
        '{$_POST['inputMobile']}',
        1,
        1,
        '$aktivacijskiKod',
        0,
        2)";

        $veza->updateDB($upit1);
        $veza->zatvoriDB();

        return true;
    }
    else{
        $message="Nije uspjelo!";
        echo "<script type='text/javascript'>alert('$message');</script>";
        return false;
    }


}

?>