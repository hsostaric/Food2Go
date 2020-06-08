<?php

include("../Baza/Baza.php");

$ID = $_GET['data'];
$veza = new Baza();
$veza->spojiDB();
$upit="SELECT * FROM Korisnik WHERE ID='$ID'";
$rezultat=$veza->selectDB($upit);
if ($rezultat->num_rows > 0) {
while ($row = $rezultat->fetch_assoc()) {
    if($row['StatusKorisnika']==1){
        $upit1="UPDATE Korisnik set StatusKorisnika=0 WHERE ID='$ID'";
        $veza->updateDB($upit1);
    }
    else{
        $upit2="UPDATE Korisnik set StatusKorisnika=1 WHERE ID='$ID'";
        $veza->updateDB($upit2);
    }

}
$veza->zatvoriDB();
echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/PregledKorisnika.php'; </script>";
}



?>

