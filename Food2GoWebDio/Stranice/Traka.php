<?php
session_start();

function prikaziTraku(){
    echo'<ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="index.php">Pocetna <span class="sr-only"></span></a>
      </li>
      <li class="nav-item active">
       <a class="nav-link" href="Registracija.php">Registracija <span class="sr-only"></span></a>
      </li>
       <li class="nav-item active">
       <a class="nav-link" href="Prijava.php">Prijava <span class="sr-only"></span></a>
      </li>
      </ul>';
}
?>