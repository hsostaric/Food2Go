<?php

include ("../Baza/Baza.php");

function PrikaziTablicuBodova()
{
    $veza = new Baza();
    $veza->spojiDB();
    $upit = "SELECT * from Nagrada";
    $rezultat = $veza->selectDB($upit);
    if ($rezultat->num_rows > 0) {


    echo '<table id="table_id" class="display" border="1" style="margin: 0 auto;">
                    <thead>
                    <tr>
                        <td>Naziv</td>
                        <td>Popust</td>
                        <td>Broj bodova</td>
                        <td>Brisanje</td>
                        <td> Azuriranje</td>
                        
                    </tr></thead><tbody>';
    while ($row = $rezultat->fetch_assoc()) {
        $ID = $row['ID'];
        echo "<tr>
        <td>" . $row['Naziv'] . "</td>   
                        <td >" . $row['Popust'] . "</td>
                        <td>" . $row['BrojBodova'] . "</td>
                        <td><a class=\"nav - link\" name='$ID' href=\"https://airfood2go.000webhostapp.com/Backend/ObrisiNagradu.php\?data=$ID\">Obrisi<span class=\"sr-only\"></span></a></td>
                        <td><a class=\"nav - link\" name='$ID' href=\"https://airfood2go.000webhostapp.com/Stranice/AzuriranjeBodova.php\?data=$ID\">Azuriraj<span class=\"sr-only\"></span></a></td>
                    
                        </tr>";
    }
}
    echo'</tbody></table>';
    $veza->zatvoriDB();
}


function DodajBodove(){

    $veza = new Baza();
    $veza->spojiDB();
    $upit="INSERT INTO Nagrada values (DEFAULT,'{$_POST['naziv']}','{$_POST['popust']}','{$_POST['brojBodova']}')";
    $veza->updateDB($upit);
    $veza->zatvoriDB();
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/BodoviVrijednosti.php'; </script>";
}


?>