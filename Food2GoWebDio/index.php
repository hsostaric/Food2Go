<?php
session_start();
include("./Stranice/zaglavlje.php");
include ("./Baza/Baza.php");

$veza = new Baza();
$veza->spojiDB();


?>

<p> </p>

<?php

include("./Stranice/podnozje.php");
?>

