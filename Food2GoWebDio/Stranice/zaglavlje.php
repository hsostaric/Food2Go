<?php

session_start();

$trenutna=basename($_SERVER["PHP_SELF"]);


?>

<!DOCTYPE html>
<html lang="hr">
<head>
    <title>FOOD2GO - Web dio aplikacije</title>
    <meta name="autor" content="AIR Webmaster"/>
    <meta name="datum" content="1.11.2019."/>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="../CSS/food2go-css.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.16/css/jquery.dataTables.css">
    <script type="text/javascript" src="https://cdn.datatables.net/1.10.16/js/jquery.dataTables.js"></script>
    <script type="text/javascript">
        $(document).ready( function () {
            $("#table_id").DataTable();
        } );

    </script>
    <script src='https://www.google.com/recaptcha/api.js'></script>
    <script>
        function startTime() {
            var today = new Date();
            var h = today.getHours();
            var m = today.getMinutes();
            var s = today.getSeconds();
            m = checkTime(m);
            s = checkTime(s);
            document.getElementById('txt').innerHTML =
                h + ":" + m + ":" + s;
            var t = setTimeout(startTime, 500);
        }
        function checkTime(i) {
            if (i < 10) {i = "0" + i};  // add zero in front of numbers < 10
            return i;
        }
    </script>
</head>
<body style="width: 95%" class=" mx-auto" onload="startTime()">
<header style="width: 95%" class="header">

        <div class="row">
            <div class="col-8">  <span>
<strong>FOOD2GO</strong>

    </span></div>
            <div class="col-4" id="txt" "></div>
        </div>

</header>
<nav style="width: 95%" class="navbar navbar-expand-lg navbar-dark bg-dark ">
    <?php
    switch (true){
        case $trenutna:
        default:
            echo'<ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="https://airfood2go.000webhostapp.com/index.php">Pocetna <span class="sr-only"></span></a>
      </li>';




        if(isset($_SESSION['korisnik_ime'])){
            // tu idu ovi kaj se ne vide kad si odlogiran
            echo' 
       <li class="nav-item active">
        <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/izbornik.php">Izbornik <span class="sr-only"></span></a>
       </li>';

        }
        //odjava prijava
       if(isset($_SESSION['korisnik_ime'])){
           echo
           '<li class="nav-item active">
            <a class="nav-link" href="https://airfood2go.000webhostapp.com/Backend/OdjavaBack.php">Odjava <span class="sr-only"></span></a>
            </li>';

       }
       else{
           echo'
      <li class="nav-item active">
       <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/Registracija.php">Registracija <span class="sr-only"></span></a>
      </li>';
           echo '<li class="nav-item active">
            <a class="nav-link" href="https://airfood2go.000webhostapp.com/Stranice/Prijava.php">Prijava <span class="sr-only"></span></a>
            </li>';
       }
      echo '</ul>';
    }

    ?>
</nav>
<section class="mx-auto">