<?php


function UpdateKategorije($id){
    $naziv=$_POST['naziv'];
    $baza = new Baza();
    $baza->spojiDB();
    $upit="UPDATE Kategorija SET Naziv='$naziv' where ID='$id'";
    $baza->updateDB($upit);
    $baza->zatvoriDB();
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/Kategorije.php'; </script>";

}


?>