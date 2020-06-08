<?php
include("zaglavlje.php");
include ("../Baza/Baza.php");
include("../Backend/OdjavaPassword.php");

if(isset($_POST['izmjena'])){
    $password=$_POST['password'];
    $ID=$_SESSION['korisnik_ID'];
    $veza = new Baza();
    $veza->spojiDB();
    $upit="UPDATE Korisnik set Lozinka ='$password' where ID='$ID'";
    $veza->updateDB($upit);
    $veza->zatvoriDB();
    Odjavi();
}
?>


<br>
<div class="container h-100" >
    <p class="h2 text-center">Izmjena lozinke</p><hr><br>
    <div class="row h-100 justify-content-center align-items-center ">
        <form class="col-12" method="post">

            <div class="form-group row">
                <label for="inputUsername" class="col-sm-2 col-form-label">Nova lozinka:</label>
                <div class="col-sm-9">
                    <input type="password" class="form-control" id="inputUsername" name="password" placeholder="Unesite novu lozinku">
                </div>
            </div>


            <br>
            <div class="form-group row" >
                <div  class="col-sm-4 "> <input name="izmjena" id="prijava" type="submit" class="btn btn-primary btn-lg btn-block " value="Promijeni lozinku"></div>
            </div>
    </div>
    </form>
</div>
<?php
include ("podnozje.php");
?>
