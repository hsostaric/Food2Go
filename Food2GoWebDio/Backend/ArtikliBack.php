<?php
include ('../Baza/Baza.php');


function DohvatiArtikle(){
    $baza = new Baza();
    $baza->spojiDB();
    $artikli = $baza->selectDB("SELECT Artikl.ID,Artikl.Naziv,Artikl.Cijena,Artikl.KolicinaZaliha,Artikl.Opis,(SELECT Kategorija.Naziv FROM Kategorija WHERE Kategorija.ID=Artikl.KategorijaID) as kategorijaNaziv  FROM Artikl");
    if($artikli->num_rows>0){

            echo '<table id="table_id" class="display" border="1" style="width: 70%">
                        <thead>
                    <tr>
                        <td>Naziv </td>
                        <td>Cijena</td>
                        <td>Količina</td>
                        <td>Opis</td>
                        <td>Naziv kategorije</td>
                        <td>Ažuriraj</td>
                        <td>Obriši</td>
                    </tr></thead><tbody>';
            while ($row=$artikli->fetch_assoc()){

                echo"<tr><td>" . $row['Naziv'] . "</td>   
                        <td >" . $row['Cijena'] . "</td>
                        <td>" . $row['KolicinaZaliha'] . "</td>
                        <td>" . $row['Opis'] . "</td>
                        <td>" . $row['kategorijaNaziv'] . "</td>
                        <td><a href='Artikli.php?artiklID=".$row['ID']."#formaZaRadArtiklima' name='azuriraj' id='".$row['ID']."' class='class=\"btn btn-warning btn-lg \"' role='button' >Ažuriraj</a></td>
                        <td> <button name='delete' id='".$row['ID']."' class='class=\"btn btn-danger btn-lg \"' >Izbriši</button></td>
                       
                    
                    </tr>";
            }
            echo"</tbody></table>";
            $baza->zatvoriDB();
    }else{
        echo " <p class=\"h4 text-center\"> Tablica ne sadržava nikakve rezultate ! </p><hr><br>";
    }

}

function DohvatiKategorije(){
    $i=0;
    $baza = new Baza();
    $baza->spojiDB();
    $kategorije = $baza->selectDB("SELECT * FROM `Kategorija` ");
    echo'<select class="form-control" id="kategorija" name="kategorija">';
    while($row=$kategorije->fetch_assoc()){
        $i++;
        echo '<option value="'.$row["ID"].'"';
        if ($i==1){echo'selected="true"';}
        echo'>'.$row["Naziv"].'</option>';
    }
    echo '</select>';

    $baza->zatvoriDB();
}
function dohvatiArtiklPremaIDu(){
    $id=$_GET['artiklID'];
    $Baza= new Baza();
    $Baza->spojiDB();
    $pom=$Baza->selectDB("Select *from Artikl where ID='".$id."'");
    $Baza->zatvoriDB();
    $polje=$pom->fetch_assoc();
   return $polje;


}


?>