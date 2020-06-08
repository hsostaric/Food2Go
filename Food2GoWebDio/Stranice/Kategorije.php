<?php
session_start();
include("../Backend/KategorijeBack.php");
include("../Stranice/zaglavlje.php");
include ("../Baza/Baza.php");

$veza = new Baza();
$veza->spojiDB();
if($_SESSION['tip_id']!=1){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}
if(isset($_POST['kategorija'])){
    DodajKategoriju();
}

?>

<br>
<div class="container h-100" >
    <p class="h2 text-center">Kategorije</p><hr><br>
    <?php
    PrikaziTablicuKategorija();
    ?>
    <br>
    <p class="h2 text-center">Unos kategorija</p><hr><br>
    <div class="row h-100 justify-content-center align-items-center ">
        <form class="col-12" method="post">

            <div class="form-group row">
                <label for="inputUsername" class="col-sm-2 col-form-label">Naziv:</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="inputUsername" name="naziv" placeholder="Unesite naziv">
                </div>
            </div>


            <br>
            <div class="form-group row">
                <div class="col-sm-4 "> <input name="kategorija" id="prijava" type="submit" class="btn btn-primary btn-lg btn-block " value="Dodaj kategoriju"></div>
            </div>
        </form>
    </div>
    <?php
    include ("podnozje.php");

    ?>
