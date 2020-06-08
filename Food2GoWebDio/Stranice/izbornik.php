<?php
session_start();
include("../Stranice/zaglavlje.php");
include ("../Baza/Baza.php");

$veza = new Baza();
$veza->spojiDB();
if($_SESSION['tip_id']!=1){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}


?>

<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="../CSS/food2go-css.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>

    <link rel="stylesheet" type="text/css" href="//cdn.datatables.net/1.10.20/css/jquery.dataTables.min.css"/>
    <link rel="stylesheet" href="../CSS/glavniIzvornik.css">
    <script type="text/javascript" src="//cdn.datatables.net/1.10.20/js/jquery.dataTables.min.js"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script src='https://www.google.com/recaptcha/api.js'></script>

</head>
<nav class="navbar navbar-light navbar-1 white" style="width: 95%">

    <!-- Navbar brand -->
    <a class="navbar-brand" style="font-size: 35px" href="#">Admin opcije</a>

    <!-- Collapse button -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent15"
            aria-controls="navbarSupportedContent15" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>

    <!-- Collapsible content -->
    <div class="collapse navbar-collapse" id="navbarSupportedContent15">


        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/IzmjenaLozinke.php">Izmjena lozinke <span class="sr-only"></span></a>
            </li>
            <?php
            echo'<li class="nav-item active ">
                <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/PregledKorisnika.php">Pregled korisnika <span class="sr-only"></span></a>
            </li>
               <li class="nav-item active">
                <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/PovratneInformacije.php">Pregled povratnih informacija <span class="sr-only"></span></a>
            </li> 
            <li class="nav-item active">
                <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/BodoviVrijednosti.php">Kreiranje i upravljanje s bodovima vjernosti <span class="sr-only"></span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="Artikli.php">Upravljanje artiklima <span class="sr-only"></span></a>
            </li>
            <li class="nav-item active">
                <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/Kategorije.php">Upravljanje kategorijama <span class="sr-only"></span></a>
            </li>
            ';
            ?>



        </ul>
        <!-- Links -->

    </div>
    <!-- Collapsible content -->

</nav>
<!--/.Navbar-->

</html>


<?php

//include("../Stranice/podnozje.php");
?>



