<?php
include ("../Baza/Baza.php");


function PrijaviKorisnika(){
    $ime=$_POST['inputUsername'];
    $password=$_POST['inputPassword'];
    $veza = new Baza();
    $veza->spojiDB();
    $upit="SELECT * from Korisnik WHERE KorisnickoIme='$ime' AND Lozinka='$password'";
    $rezultat = $veza->selectDB($upit);
    if($rezultat->num_rows > 0){
        while($row =$rezultat->fetch_assoc()){
            if($row['KorisnickoIme']== $ime && $row['Lozinka']== $password && $row['Tip_korisnikaID']==1){
                $_SESSION['korisnik_ID']=$row['ID'];
                $_SESSION['korisnik_ime']=$row['KorisnickoIme'];
                $_SESSION['tip_id']=$row['Tip_korisnikaID'];
                $veza->zatvoriDB();
                return true;
            }

        }
    }
    else{
        $veza->zatvoriDB();
        return false;
    }

}


?>