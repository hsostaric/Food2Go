<?php
include ('../Baza/Baza.php');
include("zaglavlje.php");
include('../Backend/UpdateBodova.php');
if($_SESSION['tip_id']!=1){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}
if(isset($_POST['azuriraj'])){
UpdateBodove($_GET['data']);
}


function DohvatiNaziv(){
    $baza = new Baza();
    $baza->spojiDB();
    $upit= "Select * from Nagrada where ID='{$_GET['data']}'";
    $rezultat = $baza->selectDB($upit);
    if ($rezultat->num_rows > 0) {
        while ($row = $rezultat->fetch_assoc()) {
            echo $row['Naziv'];
        }
    }
    $baza->zatvoriDB();
    }


function DohvatiPopust(){
    $baza = new Baza();
    $baza->spojiDB();
    $upit= "Select * from Nagrada where ID='{$_GET['data']}'";
    $rezultat = $baza->selectDB($upit);
    if ($rezultat->num_rows > 0) {
        while ($row = $rezultat->fetch_assoc()) {
            echo $row['Popust'];
        }
    }
    $baza->zatvoriDB();
}

function DohvatiBrojBodova(){
    $baza = new Baza();
    $baza->spojiDB();
    $upit= "Select *  from Nagrada where ID='{$_GET['data']}'";
    $rezultat = $baza->selectDB($upit);
    if ($rezultat->num_rows > 0) {
        while ($row = $rezultat->fetch_assoc()) {
            echo $row['BrojBodova'];
        }
    }
    $baza->zatvoriDB();
}
?>
    <br>
    <div class="container h-100" >


        <br>
        <p class="h2 text-center">Azuriranje bodova vjernosti</p><hr><br>
        <div class="row h-100 justify-content-center align-items-center ">
            <form class="col-12" method="post">

                <div class="form-group row">
                    <label for="inputUsername" class="col-sm-2 col-form-label">Naziv:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputUsername" name="naziv" value="<?php DohvatiNaziv();?>">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Popust:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputPassword" name="popust"  value="<?php DohvatiPopust();?>" >
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Broj bodova:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputPassword" name="brojBodova" value="<?php DohvatiBrojBodova();?>">
                    </div>
                </div>
                <br>
                <div class="form-group row">
                    <div class="col-sm-4 "> <input name="azuriraj" id="prijava" type="submit" class="btn btn-primary btn-lg btn-block " value="Azuriraj brojeve"></div>
                    </div>
        </form>
    </div>
<?php
include ("podnozje.php");

?>