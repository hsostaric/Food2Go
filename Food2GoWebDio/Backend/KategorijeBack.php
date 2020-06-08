<?php
function PrikaziTablicuKategorija()
{
    $veza = new Baza();
    $veza->spojiDB();
    $upit = "SELECT * from Kategorija";
    $rezultat = $veza->selectDB($upit);
    if ($rezultat->num_rows > 0) {
        echo '<table id="table_id" class="display" border="1" style="margin: 0 auto;">
                    <thead>
                    <tr>
                        <td>ID</td>
                        <td>Naziv</td>
                        <td>Obrisi</td>
                        <td>Azuriraj</td>
                    </tr></thead><tbody>';
        while ($row = $rezultat->fetch_assoc()) {
            $ID = $row['ID'];
            echo "<tr>
        <td>" . $row['ID'] . "</td>   
                        <td >" . $row['Naziv'] . "</td>
                        <td><a class=\"nav - link\" name='$ID' href=\"https://airfood2go.000webhostapp.com/Backend/ObrisiKategoriju.php\?data=$ID\">Obrisi<span class=\"sr-only\"></span></a></td>
                        <td><a class=\"nav - link\" name='$ID' href=\"https://airfood2go.000webhostapp.com/Stranice/AzuriranjeKategorija.php\?data=$ID\">Azuriraj<span class=\"sr-only\"></span></a></td>
                    
                        </tr>";
        }
    }
    echo'</tbody></table>';
    $veza->zatvoriDB();
}
function DodajKategoriju(){
    $veza = new Baza();
    $veza->spojiDB();
    $upit="INSERT INTO Kategorija values (DEFAULT,'{$_POST['naziv']}')";
    $veza->updateDB($upit);
    $veza->zatvoriDB();
    echo "<script type='text/javascript'>  window.location='https://airfood2go.000webhostapp.com/Stranice/Kategorije.php'; </script>";

}




?>