<?php
include ('zaglavlje.php');
include ('../Backend/ArtikliBack.php');
$artikl=[];
if($_SESSION['tip_id']!=1){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}

?>
<br>
<div class="container h-100" >
    <p class="h2 text-center"> Upravljanje artiklima </p><hr><br>
    <div class="row h-100 justify-content-center align-items-center ">
        <form class="col-12" method="post" name="upravljanjeArtiklima" id="artikli">
        <?php
        DohvatiArtikle();
        ?>
<br>
<br>
    </form>

        <form class="col-12" method="post" id="formaZaRadArtiklima">
           <?php

           if(!isset($_GET['artiklID'])) echo'<p  class="h3 text-center">Novi artikl</p><hr>';
           else{
               echo'<p  class="h3 text-center">Uredi artikl</p><hr>';
               $artikl=dohvatiArtiklPremaIDu();

           }
           ?>
            <br><br>
           <?php
           if(isset($_GET['artiklID'])){
               echo ' <div class="form-group row">
                <label for="idArtikla" class="col-sm-2 col-form-label">ID :</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="idArtikla" name="idArtikla" value="'.$artikl['ID'].'" readonly>
                </div></div><br>';
           }
           ?>
            <div class="form-group row">
                <label for="nazivArtikla" class="col-sm-2 col-form-label">Naziv :</label>
                <div class="col-sm-9">
                    <input type="text" class="form-control" id="nazivArtikla" name="nazivArtikla" <?php
                    if(isset($_GET['artiklID']))echo"value='".$artikl["Naziv"]."'";
                    ?>placeholder="Unesite naziv artikla" required>
                </div><br>

            </div>
            <div class="form-group row">
                <label for="cijenaArtikla" class="col-sm-2 col-form-label">Cijena :</label>
                <div class="col-sm-9">
                    <input type="number" min="1" max="1000" <?php
                    if(isset($_GET['artiklID']))echo"value='".$artikl["Cijena"]."'";
                  ?> value="10" class="form-control" id="cijenaArtikla" name="cijenaArtikla" required>
                </div>
            </div>
            <div class="form-group row">
                <label for="kolicinaRobe" class="col-sm-2 col-form-label">Kolicina :</label>
                <div class="col-sm-9">
                    <input type="number" min="1" max="1000" value="10" class="form-control" id="kolicinaRobe" name="kolicinaRobe"<?php
                    if(isset($_GET['artiklID']))echo"value='".$artikl["KolicinaZaliha"]."'";
                    ?> required>
                </div>
            </div>
            <div class="form-group row">
                <label for="opisArtikla" class="col-sm-2 col-form-label">Opis :</label>
                <div class="col-sm-9">
                     <textarea  class="form-control" id="opisArtikla"  name="opisArtikla" cols="50" rows="4" ><?php
                         if(isset($_GET['artiklID']))echo $artikl["Opis"];
                         ?></textarea>
                </div>
            </div>
            <div class="form-group row">
                <label for="vrstaKategorije" class="col-sm-2 col-form-label">Kategorija :</label>
                <div class="col-sm-9">
                    <?php DohvatiKategorije() ?>
                </div>
            </div>

            <div style="margin: 0 auto" class="form-group row">
                <div class="col-sm-5 align-text-bottom ">
                 <?php
                 if(!isset($_GET['artiklID']))echo'<input id="artiklDodaj" name="artiklDodaj" type="submit" class="btn btn-primary btn-lg active " value="Dodaj"></div> ';
                 else echo '<button id="artiklUpdate" name="artiklUpdate" type="submit" class="btn btn-primary btn-lg active " >Ažuriraj</button></div> ';
                 ?>
            </div>
        </form>
    </div></div>
<?php
include ('podnozje.php');
?>
