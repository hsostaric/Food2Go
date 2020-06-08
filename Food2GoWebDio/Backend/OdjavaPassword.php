<?php
function Odjavi(){
    session_start();
    session_destroy();
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/Prijava.php'; </script>";
}

?>