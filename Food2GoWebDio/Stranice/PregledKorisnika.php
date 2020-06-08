<?php

include("zaglavlje.php");
if($_SESSION['tip_id']!=1){
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/index.php'; </script>";
}
include("../Backend/DohvacanjeKorisnika.php");
?>


<?php

echo '<br/>';?>
<br>

    <p class="h2 text-center"> Upravljanje korisnicima </p><hr><br>

        <form class="col-12" method="post" name="upravljanjeArtiklima" id="artikli" style="margin: 0 auto">
           <?php TablicaKorisnika();
            ?>

            <p>0 je blokiran
                <br>
                1 je aktivan
            </p>
    </form>



<?php
include ('podnozje.php')
?>