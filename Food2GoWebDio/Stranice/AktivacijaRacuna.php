<?php
include("zaglavlje.php");
include("../Backend/AktivacijaKodaBack.php");
if(isset($_POST["Potvrdi"])){
    if(AktivirajRacun()){
        echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/Prijava.php'; </script>";
    }
    else{

    }

}

?>
    <br>
    <div class="container h-100" >
        <p class="h2 text-center">Aktivacija racuna</p><hr><br>
        <div class="row h-100 justify-content-center align-items-center ">
            <form class="col-12" method="post">

                <div class="form-group row">
                    <label for="inputUsername" class="col-sm-2 col-form-label">Email:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="email" id="inputUsername" placeholder="Unesite email">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Aktivacijski kod:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" name="kod" id="kod" placeholder="Unesite kod">
                    </div>
                </div>
                <br>
                <div class="form-group row">

                    <div class="col-sm-4 "> <input name="Potvrdi" id="prijava" type="submit" class="btn btn-primary btn-lg btn-block " value="Aktiviraj"></div
                </div></div>
        </form>
    </div>
<?php
include ("podnozje.php");
?>