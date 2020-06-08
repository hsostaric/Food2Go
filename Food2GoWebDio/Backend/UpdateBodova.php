<?php


function UpdateBodove($id){
$naziv=$_POST['naziv'];
$popust=$_POST['popust'];
$brojBodova=$_POST['brojBodova'];
$baza = new Baza();
$baza->spojiDB();
$upit="UPDATE Nagrada SET Naziv='$naziv', Popust='$popust', BrojBodova='$brojBodova' where ID='$id'";
$baza->updateDB($upit);
$baza->zatvoriDB();
echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/BodoviVrijednosti.php'; </script>";

}


?>