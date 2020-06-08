<?php

include("../Baza/Baza.php");

function TablicaKorisnika(){
    $veza = new Baza();
    $veza->spojiDB();
    $upit="SELECT * from Korisnik";
    $rezultat=$veza->selectDB($upit);
    if ($rezultat->num_rows > 0) {

    }
    echo '<table id="table_id" class="display" border="1" style="margin: 0 auto;">
                    <thead>
                    <tr>
                        <td>ID</td>
                        <td>Ime</td>
                        <td>Prezime</td>
                        <td>Username</td>
                        <td>OIB</td>
                        <td>Mail adresa</td>
                        <td>Adresa</td>
                        <td>Mobitel</td>
                        <td>Broj pokusaja prijave</td>
                        <td>Status korisnika</td>
                        <td>Broj bodova</td>
                        
                        <td>Tip korisnika</td>
                        <td>Blokiranje</td>
                        <td>Dodaj/Ukloni ulogu administrator</td>
                   
                    </tr></thead><tbody>';
        while ($row = $rezultat->fetch_assoc()) {
            $ID=$row['ID'];
            echo"<tr><td>" . $row['ID'] . "</td>   
                        <td >" . $row['Ime'] . "</td>
                        <td>" . $row['Prezime'] . "</td>
                        <td>" . $row['KorisnickoIme'] . "</td>
                        <td>" . $row['OIB'] . "</td>
                        <td>" . $row['Email'] . "</td>
                        <td>" . $row['Adresa'] . "</td>
                        <td>" . $row['BrojMobitela'] . "</td>
                        <td>" . $row['BrojPokusaja'] . "</td>
                        <td>" . $row['StatusKorisnika'] . "</td>
                        <td>" . $row['Broj_bodova'] . "</td>
                        <td>" . $row['Tip_korisnikaID'] . "</td>
                        <td><a class=\"nav-link\" name='$ID' href=\"https://airfood2go.000webhostapp.com/Backend/BlokirajKorisnika.php\?data=$ID\">Promijeni status<span class=\"sr-only\"></span></a></td>";
                        if($row['Tip_korisnikaID']>1)echo '<td><button name="dodijeliAdmina" id="'.$row['ID'].'" class="btn btn-success ">Dodijeli ulogu </button></td>';
                       else echo'<td><button name="ukloniAdmina" id="'.$row['ID'].'" class="btn btn-light">Ukloni ulogu </button></td>';
                   echo"</tr>";
        }
        echo'</tbody></table>';
        $veza->zatvoriDB();
    }






?>