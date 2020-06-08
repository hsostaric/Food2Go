<?php
include("../Baza/Baza.php");
$veza = new Baza();
$veza->spojiDB();
$upit="SELECT * from Korisnik";
$status1="Not OK";
$data->ime="";
$data->prezime="";
$rezultat=$veza->selectDB($upit);
if ($rezultat->num_rows > 0) {
    while ($row = $rezultat->fetch_assoc()) {
        $data->prezime=$row['Prezime'];
        $data->ime=$row['Ime'];
        $status1="OK";
        $Korisnik->status=$status1;
        $Korisnik->korisnik=$data;
        $myJSON = json_encode($Korisnik);
        echo $myJSON;
    }
}





$veza->zatvoriDB();



?>