<?php
include("zaglavlje.php");

include("../Backend/PrijavaBack.php");
if(isset($_POST['prijava'])){

    if(PrijaviKorisnika()){
        echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
    }
    else{

    }

}
if(isset($_SESSION['korisnik_ime']) && !empty($_SESSION['korisnik_ime'])){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}


?>
    <br>
    <div class="container h-100" >
        <p class="h2 text-center">Prijava</p><hr><br>
        <div class="row h-100 justify-content-center align-items-center ">
            <form class="col-12" method="post">

                <div class="form-group row">
                    <label for="inputUsername" class="col-sm-2 col-form-label">Korisničko ime:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputUsername" name="inputUsername" placeholder="Unesite korisničko ime">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Lozinka:</label>
                    <div class="col-sm-9">
                        <input type="password" class="form-control" id="inputPassword" name="inputPassword" placeholder="Unesite lozinku">
                    </div>
                </div>
<br>
                <div class="form-group row">
                    <div class="col-sm-4 "> <p><a href="https://airfood2go.000webhostapp.com/Stranice/NovaLozinka.php" class="btn btn-link btn-lg active btn-block" role="button" aria-pressed="true">Zaboravili ste lozinku ?</a></p></div>
                    <div class="col-sm-4 "> <input name="prijava" id="prijava" type="submit" class="btn btn-primary btn-lg btn-block " value="Prijavi se"></div>
                    <div class="col-sm-4 "> <p><a  href="https://airfood2go.000webhostapp.com/Stranice/AktivacijaRacuna.php" class="btn btn-link btn-lg active btn-block" role="button" aria-pressed="true">Aktivacija računa </a></p></div>
                </div></div>
            </form>
        </div>
<?php
include ("podnozje.php");
?>