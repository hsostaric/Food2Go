<?php
include("zaglavlje.php");
//require_once ("../Baza/Baza.php");
include("../Backend/DodavanjeBodova.php");
if($_SESSION['tip_id']!=1){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}
if(isset($_POST['nagrada'])){
    DodajBodove();
}



?>
    <br>
    <div class="container h-100" >
        <p class="h2 text-center">Bodovi vjernosti</p><hr><br>
        <?php
            PrikaziTablicuBodova();
        ?>
        <br>
    <p class="h2 text-center">Unos bodova vjernosti</p><hr><br>
        <div class="row h-100 justify-content-center align-items-center ">
            <form class="col-12" method="post">

                <div class="form-group row">
                    <label for="inputUsername" class="col-sm-2 col-form-label">Naziv:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputUsername" name="naziv" placeholder="Unesite naziv">
                    </div>
                </div>

                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Popust:</label>
                    <div class="col-sm-9">
                        <input type="trext" class="form-control" id="inputPassword" name="popust" placeholder="Unesite popust">
                    </div>
                </div>
                <div class="form-group row">
                    <label for="inputPassword" class="col-sm-2 col-form-label">Broj bodova:</label>
                    <div class="col-sm-9">
                        <input type="text" class="form-control" id="inputPassword" name="brojBodova" placeholder="Unesite broj bodova">
                    </div>
                </div>
                <br>
                <div class="form-group row">
                    <div class="col-sm-4 "> <input name="nagrada" id="prijava" type="submit" class="btn btn-primary btn-lg btn-block " value="Dodaj bodove"></div>
                    </div>
        </form>
    </div>
<?php
include ("podnozje.php");

?>