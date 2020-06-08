<?php
include("zaglavlje.php");
include("../Backend/PovranteInformacijeBack.php");
if($_SESSION['tip_id']!=1){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}
if(isset($_POST['nagrada'])){
    DodajBodove();
}



?>
<br>
<div class="container h-100" >
    <p class="h2 text-center">Povratne informacije</p><hr><br>
    <?php
    PrikaziPovratneInformacije();
    ?>
    <br>