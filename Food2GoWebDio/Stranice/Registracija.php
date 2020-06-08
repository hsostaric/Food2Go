<?php
include("zaglavlje.php") ;
include("../Backend/RegistracijaBack.php");
include ("reCaptcha.php");

if(isset($_POST["submit1"])){

   if(registrirajKorisnika()){
       echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/AktivacijaRacuna.php'; </script>";
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

    <p class="h2 text-center">Registracija</p><hr><br>
    
    <div class="row h-100 justify-content-center align-items-center ">
<form class="col-12" method="post">
    <div class="form-group row">
        <label for="inputName" class="col-sm-2 col-form-label">Ime:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="inputName" name="inputName" placeholder="Unesite ime">
        </div><br>
        
    </div>
    <div class="form-group row">
        <label for="inputSurname" class="col-sm-2 col-form-label">Prezime:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="inputSurname" name="inputSurname" placeholder="Unesite prezime">
        </div>
    </div>
    <div class="form-group row">
        <label for="inputUsername" class="col-sm-2 col-form-label">Korisničko ime:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="inputUsername" name="inputUsername" placeholder="Unesite korisničko ime">
        </div>
    </div>
    <div class="form-group row">
        <label for="inputEmail" class="col-sm-2 col-form-label">E-mail:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="inputEmail"  name="inputEmail" placeholder="netko@example.com">
        </div>
    </div>
    <div class="form-group row">
        <label for="inputPassword" class="col-sm-2 col-form-label">Lozinka:</label>
        <div class="col-sm-9">
            <input type="password" class="form-control" id="inputPassword" name="inputPassword" placeholder="Unesite lozinku">
        </div>
    </div>
    <div class="form-group row">
        <label for="inputRepeatPassword" class="col-sm-2 col-form-label">Potvrda lozinke:</label>
        <div class="col-sm-9">
            <input type="password" class="form-control" id="inputRepeatPassword" name="inputRepeatPassword" placeholder="Ponovite lozinku">
        </div>
    </div>
    <div class="form-group row">
        <label for="inputOIB" class="col-sm-2 col-form-label">OIB:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="inputOIB" name="inputOIB" placeholder="Unesite OIB">
        </div>
    </div>
    <div class="form-group row">
        <label for="inputMobile" class="col-sm-2 col-form-label">Broj mobitela:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="inputMobile" name="inputMobile" placeholder="Unesite broj mobitela">
        </div>
    </div>
    <div class="form-group row">
        <label for="inputAdresa" class="col-sm-2 col-form-label">Adresa:</label>
        <div class="col-sm-9">
            <input type="text" class="form-control" id="inputAdresa"  name="inputAdresa" placeholder="Unesite svoju adresu">
        </div>
    </div>

    <div class="form-group row">
            <div class="col-sm-5 align-text-bottom ">
                <input id="submit1" name="submit1" type="submit" class="btn btn-info btn-lg active " value="Registriraj se"></div>
    </div>
</form>
</div>
<?php
include ("podnozje.php");
?>

