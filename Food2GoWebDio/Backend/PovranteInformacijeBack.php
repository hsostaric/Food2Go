<?php

include("../Baza/Baza.php");

function PrikaziPovratneInformacije(){
    $veza = new Baza();
    $veza->spojiDB();
    $upit="SELECT * from PovratnaInformacija";
    $rezultat=$veza->selectDB($upit);
    if ($rezultat->num_rows > 0) {

    }
    echo '<table id="table_id" class="display" border="1" style="margin: 0 auto;">
                    <thead>
                    <tr>
                        <td>ID</td>
                        <td>Komentar</td>
                        <td>Ocjena</td>
                        <td>Brisanje</td>
                    </tr></thead><tbody>';
    while ($row = $rezultat->fetch_assoc()) {
        $ID=$row['ID'];
        echo"<tr><td>" . $row['ID'] . "</td>   
                        <td>" . $row['Komentar'] . "</td>
                        <td>" . $row['Ocjena'] . "</td>
                        <td><a class=\"nav-link\" name='$ID' href=\"https://airfood2go.000webhostapp.com/Backend/ObrisiPovratnu.php\?data=$ID\">Obrisi<span class=\"sr-only\"></span></a></td>
                        
                    </tr>";
    }
    echo'</tbody></table>';
    $veza->zatvoriDB();
}


?>