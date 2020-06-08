<?php
require("../Baza/Baza.php");

function dohvatiRezultate($sql)
{
    $baza = new Baza();
    $baza->spojiDB();
    $rezultat = $baza->selectDB($sql);
    $baza->zatvoriDB();
    return $rezultat;
}

function izvrsiUpit($sql)
{
    $baza = new Baza();
    $baza->spojiDB();
    $baza->updateDB($sql);
    $baza->zatvoriDB();

}

function vratiOdgovor($status, $brojRedaka, $poruka, $podaci)
{
    $odgovor = ["status" => $status, "brojRedaka" => $brojRedaka, "poruka" => $poruka, "podaci" => $podaci];
    echo json_encode($odgovor);
}

function DodajCijenuRacunu($racunid,$cijena){
    $upit1 = "UPDATE Racun SET Ukupno='$cijena' WHERE ID='$racunid'";
    izvrsiUpit($upit1);
    $status = "OK";
    $poruka="Cijena je azurirana";
    $final["ID"]=$racunid;
    $final["Ukupno"]=$cijena;
    vratiOdgovor($status, 0, $poruka, $final);
}
function IzracunajBodove($ukupno,$korisnikID){
    $upit1 = "SELECT PravilaBodovanjaTemporalno.BrojBodova as 'BrojBodova'  
    FROM  PravilaBodovanjaTemporalno  LEFT JOIN PravilaBodovanja  ON 
    PravilaBodovanjaTemporalno.ID = PravilaBodovanja.AktualnoPraviloID
    WHERE '$ukupno' >= PravilaBodovanja.Cijena AND PravilaBodovanja.CijenaDo >= '$ukupno' ";

    $polje=dohvatiRezultate($upit1);
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $red = [];
    $final = [];
    $poruka = "Racun je pronaden";

    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $brojBodova=$row["BrojBodova"];
            $upit="UPDATE Korisnik SET Broj_Bodova=Broj_Bodova+'$brojBodova' WHERE ID='$korisnikID'";
            izvrsiUpit($upit);
        }
    }
}
function IskoristiKod($id){
    $upit="UPDATE Racun SET IskoristenKod=1 WHERE ID='$id'";
    izvrsiUpit($upit);
}
function DohvatiRacuneZaProvjeru($korisnikID,$kod){
    $pos=strpos($kod,";");
    if($pos!==false){
        //QR kod
        $kodovi= explode(";",$kod);
        $pin=$kodovi[0];
        $brojracuna=$kodovi[1];
        $polje=dohvatiRezultate("SELECT * FROM Racun WHERE Korisnik_ID='$korisnikID' AND PIN='".$pin."' AND BrojRacuna= '".$brojracuna."'
        AND IskoristenKod=0");
        $brojRedova = mysqli_num_rows($polje);
        $status = "OK";
        $red = [];
        $final = [];
        $poruka = "Racun je pronaden";
        if ($brojRedova > 0) {
            while ($row = $polje->fetch_assoc()) {
                $red["ID"]=$row["ID"];
                IskoristiKod($row["ID"]);
                $red["BrojRacuna"]=$row["BrojRacuna"];
                $red["Ukupno"]=$row["Ukupno"];
                IzracunajBodove($row["Ukupno"],$korisnikID);
                $red["QRkod"]=$row["QRkod"];
                $red["Datum"]=$row["Datum"];
                $red["Popust"]=$row["Popust"];
                $red["Korisnik_ID"]=$row["Korisnik_ID"];
                $red["PIN"]=$row["PIN"];
                $red["RestoranID"]=$row["RestoranID"];
                $red["Status_narudzbeID"]=$row["Status_narudzbeID"];
                $red["IskoristenKod"]=$row["IskoristenKod"];
                $final[]=$red;
                
            }
        }
        else{
            $status="Not OK QR";
        }
        vratiOdgovor($status, $brojRedova, $poruka, $final[0]);
    }
    else{
        //PIN
        $polje=dohvatiRezultate("SELECT * FROM Racun WHERE Korisnik_ID='$korisnikID' AND PIN='".$kod."' AND IskoristenKod=0");
        $brojRedova = mysqli_num_rows($polje);
        $status = "OK";
        $red = [];
        $final = [];
        $poruka = "Racun je pronaden";
        if ($brojRedova > 0) {
            while ($row = $polje->fetch_assoc()) {
                $red["ID"]=$row["ID"];
                IskoristiKod($row["ID"]);
                $red["BrojRacuna"]=$row["BrojRacuna"];
                $red["Ukupno"]=$row["Ukupno"];
                IzracunajBodove($row["Ukupno"],$korisnikID);
                $red["QRkod"]=$row["QRkod"];
                $red["Datum"]=$row["Datum"];
                $red["Popust"]=$row["Popust"];
                $red["Korisnik_ID"]=$row["Korisnik_ID"];
                $red["PIN"]=$row["PIN"];
                $red["RestoranID"]=$row["RestoranID"];
                $red["Status_narudzbeID"]=$row["Status_narudzbeID"];
                $red["IskoristenKod"]=$row["IskoristenKod"];
                $final[]=$red;
            }
        }
        else{
            $status="Not OK pin";
        }
        vratiOdgovor($status, $brojRedova, $poruka, $final[0]);
    }

}
function DohvatiSveKorisnike()
{
    $polje = dohvatiRezultate("SELECT * FROM Korisnik");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $red = [];
    $final = [];
    $poruka = "Korisinici su pronadjeni";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $red['id'] = $row["ID"];
            $red["username"] = $row["KorisnickoIme"];
            $red["lozinka"] = $row["Lozinka"];
            $final[] = $red;
        }

    }
    vratiOdgovor($status, $brojRedova, $poruka, $final);

}

function DohvatiKorisnikaReg($user, $email, $oib)
{
    $polje = dohvatiRezultate("SELECT *FROM Korisnik WHERE KorisnickoIme='$user' OR Email='$email' OR OIB='$oib'");
    $brojRedova = mysqli_num_rows($polje);

    return ($brojRedova == 0) ? false : true;

}

function BlokirajKorisnika($user, $blokiraj)
{
    $status = "OK";
    $red = [];
    $final = [];
    $poruka = "Nije uspjelo";
    if ($blokiraj == "da") {
        $upit1 = "UPDATE Korisnik SET StatusKorisnika=2 WHERE KorisnickoIme='$user'";
        izvrsiUpit($upit1);
        $status = "Nije OK";
        $poruka = "Korisnik je blokiran!";
    } else {
        $upit2 = "UPDATE Korisnik SET StatusKorisnika=1 WHERE KorisnickoIme='$user'";
        izvrsiUpit($upit2);
        $upit3 = "UPDATE Korisnik SET BrojPokusaja=0 WHERE KorisnickoIme='$user'";
        izvrsiUpit($upit3);
        $poruka = "Korisnik je odblokiran!";
    }
    vratiOdgovor($status, 0, $poruka, $final);

}

function BlokirajKorisnikaPrijePrijave($user)
{
    $korisnik = dohvatiRezultate("SELECT * FROM Korisnik WHERE KorisnickoIme='$user'");
    $brojRedova = mysqli_num_rows($korisnik);
    //korisnik postoji
    if ($brojRedova > 0) {
        while ($row = $korisnik->fetch_assoc()) {
            if ($row['BrojPokusaja'] >= 3) {
                $upit1 = "UPDATE Korisnik SET StatusKorisnika=1 WHERE KorisnickoIme='$user'";
                izvrsiUpit($upit1);
                $upit2 = "UPDATE Korisnik SET BrojPokusaja=0 WHERE KorisnickoIme='$user'";
                izvrsiUpit($upit2);
            }
        }
    }

}
function ProvjeriVelicinuStringa($string){
    $duzina=strlen($string);
    if($duzina<12){
        for($x=$duzina;$x<11;$x++){
            $string.=" ";
        }
    }
    return $string;
}
function PosaljiMail($id){
    $status = "OK";
    $red = [];
    $final = [];
    $podaci="";
    $sveNarudzbe="";
    $racunID=$id;
    //$to="cinematronic123@gmail.com";
    $poruka = "Mail poslan";
    $racun= dohvatiRezultate("SELECT * FROM Racun WHERE ID='$id'");
    $brojRedova = mysqli_num_rows($racun);
    //racunpostoji
    if ($brojRedova > 0) {
        while ($row = $racun->fetch_assoc()) {
            $pin=$row["PIN"];
            $datum=$row["Datum"];
            $racunBroj=$row["BrojRacuna"];
            $idkorisnika=$row["Korisnik_ID"];
            $ukupno=$row["Ukupno"];
            $podaci="$pin".";"."$racunBroj";
            //dohvacanje korisnika racuna
            $korisnik= dohvatiRezultate("SELECT * FROM Korisnik WHERE ID='$idkorisnika'");
            while ($row5 = $korisnik->fetch_assoc()) {
                $to=$row5["Email"];
            }
            //dohvacanje svih stavki racuna
            $stavkeRacuna=dohvatiRezultate("SELECT * FROM StavkeRacuna  WHERE Racun_ID='$id'");
            $brojStavki = mysqli_num_rows($racun);
            if($brojStavki>0){
                //kolicina stavke na racunu
                while ($row2 = $stavkeRacuna->fetch_assoc()) {
                    //dohvacanje imena artikla i cijene njegve
                    $idartikla=$row2["Artikl_ID"];
                    $kolicina=$row2["Kolicina"];
                    $artikl=dohvatiRezultate("SELECT * FROM Artikl WHERE ID='$idartikla'");
                    while ($row3 = $artikl->fetch_assoc()) {
                        $nazivArtikla=$row3["Naziv"];
                        $final[]=$nazivArtikla;
                        $nazivArtikla=ProvjeriVelicinuStringa($nazivArtikla);
                        $temporalCijenaID=$row3["Artiikl_Temporal_ID"];
                        //dohvacanje temporalne cijene
                        $cijena=dohvatiRezultate("SELECT * FROM Artiikl_Temporalno_Cijena WHERE ID='$temporalCijenaID'");
                        while ($row4 = $cijena->fetch_assoc()) {
                            $cijenaArtikla=$row4["Cijena"];
                                $final[]=$cijenaArtikla;
                        }
                        $narudzbe="$nazivArtikla"."\t\t"."$cijenaArtikla "." HRK "."\t"."$kolicina KOM"."\n";
                    }
                    $sveNarudzbe.=$narudzbe;
                }
            }
        }
    }
    $subject = 'Racun';
    $headers = "";
    $message="FOOD2GO Racun"."\n\n".
        " Racun ID="."$racunID"."\n".
        "PIN= "."$pin"."\n".
        "Datum: ".
        "$datum"."\n".
        "================================================="."\n".
        $sveNarudzbe.
        "================================================="."\n\n".
        "Ukupno: "."$ukupno"." HRK"."\n".
        "Vas QR kod je dostupan na sljedecem linku: "."https://chart.googleapis.com/chart?cht=qr&chs=150x150&chl=$podaci"."\n\n";
    mail($to, $subject, $message, $headers);
    //vratiOdgovor($status, 0, $poruka, $podaci);
}


function DohvatiKorisnika($user, $pass)
{
    BlokirajKorisnikaPrijePrijave($user);
    $final = [];
    $korisnik = dohvatiRezultate("SELECT * FROM Korisnik WHERE KorisnickoIme='$user'");
    $brojRedova = mysqli_num_rows($korisnik);
    //korisnik postoji
    if ($brojRedova > 0) {
        while ($row = $korisnik->fetch_assoc()) {
            $status = "OK";
            $red = [];
            $final = [];
            if ($row["Lozinka"] == $pass && $row["StatusKorisnika"] > 1) {
                $red['id'] = $row["ID"];
                $red['ime'] = $row["Ime"];
                $red['prezime'] = $row["Prezime"];
                $red["username"] = $row["KorisnickoIme"];
                $red["lozinka"] = $row["Lozinka"];
                $red["status"] = $row["StatusKorisnika"];
                $red['brojPokusaja'] = 0;
                $red['adresa'] = trim($row["Adresa"]);
                $red["oib"] = $row["OIB"];
                $red["email"] = $row["Email"];
                $red["mobitel"] = $row["Mobitel"];
                $red["brojBodova"] = $row["Broj_Bodova"];


                $final[] = $red;
                $upit1 = "UPDATE Korisnik SET BrojPokusaja= 0 WHERE KorisnickoIme='$user' and Lozinka = '$pass'";
                izvrsiUpit($upit1);
                //  $string=strval(json_encode($final));
                $poruka = "Korisnik je pronaden i spreman za prijavu!";
                vratiOdgovor($status, $brojRedova, $poruka, $final);
            } //korisnik postoji, no prijava iz nekog slucaja ne prolazi
            else {
                //blokiran
                if ($row["StatusKorisnika"] == 1) {
                    $status = "Nije OK";
                    $poruka = "Korisnik je pronaden, ali je blokiran!";
                    vratiOdgovor($status, $brojRedova, $poruka, $final);
                } //nije aktivan
                elseif ($row["StatusKorisnika"] == 0) {
                    $status = "Nije OK";
                    $poruka = "Korisnik je pronaden, ali nije aktiviran!";
                    vratiOdgovor($status, $brojRedova, $poruka, $final);

                } else {
                    $upit2 = "UPDATE Korisnik set BrojPokusaja=BrojPokusaja+1 WHERE KorisnickoIme='$user' ";
                    $status = "Nije OK";
                    $poruka = "Korisnik je pronaden, ali kriva je lozinka";
                    izvrsiUpit($upit2);
                    vratiOdgovor($status, $brojRedova, $poruka, $final);
                }
            }
        }
    } //korisnik ne postoji
    else {
        $poruka = "Korisnik nije pronaden";
        $brojRedova = 0;
        $status = "Nije OK";
        vratiOdgovor($status, $brojRedova, $poruka, $final);
    }
}

function posaljiKod($akt, $mail)
{
    $kome = $mail;
    $naslov = "Aktivacijski kod";
    $sadrzaj = "Uspjesno ste registrirani, vas registracijski kod je " . $akt . " i traje 24 sata";
    $od = "From: igradiski@foi.hr";
    mail($kome, $naslov, $sadrzaj, $od);
}

function posaljiLozinkuMail($pass, $mail)
{
    $kome = $mail;
    $naslov = "Lozinka";
    $sadrzaj = "Vaša lozinka je:" . $pass . "";
    $od = "From: igradiski@foi.hr";
    mail($kome, $naslov, $sadrzaj, $od);
}

function RegistrirajKorisnika($ime, $prezime, $username, $password, $oib, $email, $adresa, $brojmobitela, $brojpokusaja, $status, $aktivacijski, $bodovi, $tipkorisnika)
{
    $final = [];

    if (DohvatiKorisnikaReg($username, $email, $oib) == false) {
        //LUPI REGISTRACIJU SAD
        $upit = "INSERT INTO Korisnik values(DEFAULT,'$ime','$prezime','$username','$password','$oib','$email','$adresa','$brojmobitela','" . $brojpokusaja . "','$status','$aktivacijski','$bodovi','$tipkorisnika')";
        izvrsiUpit($upit);
        $status = "OK";
        $poruka = "Korisnik je registriran";
        posaljiKod($aktivacijski, $email);
    } else {
        $status = "Nije OK";
        $poruka = "Parametri nisu ispravno uneseni";
    }
    vratiOdgovor($status, 0, $poruka, $final);

}

function DohvatiKorisnikaAkt($email, $aktivacijski)
{
    $polje = dohvatiRezultate("SELECT *FROM Korisnik WHERE Email='$email' AND AktivacijskiKod='$aktivacijski' AND StatusKorisnika=0");
    $brojRedova = mysqli_num_rows($polje);

    return ($brojRedova == 0) ? false : true;

}

function AktivirajKorisnika($mail, $aktivacijski)
{

    if (DohvatiKorisnikaAkt($mail, $aktivacijski) == true) {
        $sql = "UPDATE Korisnik set StatusKorisnika=2 WHERE Email='$mail' AND AktivacijskiKod='$aktivacijski'";
        izvrsiUpit($sql);
        $status = "OK";
        $red = [];
        $final = [];
        $poruka = "Korisnik je aktiviran";
    } else {
        $status = "Nije OK";
        $poruka = "Unijeli ste krive podatke ili je korisnik vec aktiviran";
        $final = [];
    }

    vratiOdgovor($status, 0, $poruka, $final);
}

function stvoriLozinku()
{
    $code1 = md5("PasswordSalt" . microtime());
    $pass = substr($code1, 0, 10);
    return $pass;
}

function PosaljiLozinku($mail, $username)
{
    $pass = stvoriLozinku();
    $polje = izvrsiUpit("UPDATE Korisnik SET Lozinka ='$pass' WHERE KorisnickoIme='$username' AND Email = '$mail'");
    $polje = dohvatiRezultate("SELECT * FROM Korisnik WHERE KorisnickoIme='$username' AND Email = '$mail'");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $red = [];
    $final = [];
    $poruka = "Mail s lozinkom je poslan";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            posaljiLozinkuMail($row["Lozinka"], $row['Email']);
        }
    } else {
        $status = "Nije OK";
        $poruka = "Ne postoji taj korisnik";

    }
    vratiOdgovor($status, $brojRedova, $poruka, $final);
}


function KorisnikGetID($user)
{
    $ID = "";
    $korisnik = dohvatiRezultate("SELECT * FROM Korisnik WHERE KorisnickoIme='$user'");
    $brojRedova = mysqli_num_rows($korisnik);
    if ($brojRedova > 0) {
        while ($row = $korisnik->fetch_assoc()) {
            return $ID = $row['ID'];
        }
    }
    return $ID;
}

function DohvatiNarudzbe($username)
{
    $final = [];
    $korisnikID = KorisnikGetID($username);
    $racuni = dohvatiRezultate("SELECT * FROM Racun WHERE Korisnik_ID='$korisnikID'");
    $brojRedova = mysqli_num_rows($racuni);
    $artikl = [];
    $racun = [];
    $broj = 1;
    if ($brojRedova > 0) {
        while ($row = $racuni->fetch_assoc()) {
            $id = $row["ID"];
            $artikli = dohvatiRezultate("
                SELECT Artiikl_Temporalno_Cijena.Cijena AS 'Cijena',Artikl.Naziv AS 'Naziv',Artikl.Opis AS 'Opis',StavkeRacuna.Kolicina AS 'Kolicina',
                Racun.BrojRacuna AS 'BrojRacuna',Racun.Ukupno AS 'Ukupno', Racun.Datum AS 'Datum'
                FROM Artiikl_Temporalno_Cijena RIGHT JOIN
                 Artikl ON Artiikl_Temporalno_Cijena.ID= Artikl.Artiikl_Temporal_ID RIGHT JOIN
                 StavkeRacuna ON StavkeRacuna.Artikl_ID=Artikl.ID 
                RIGHT JOIN Racun ON StavkeRacuna.Racun_ID=Racun.ID WHERE Racun.ID='$id'");
            $brojRedovaArtikla = mysqli_num_rows($artikli);
            if ($brojRedovaArtikla > 0) {
                while ($rowArtikl = $artikli->fetch_assoc()) {
                    $artikl["BrojRacuna"] = $rowArtikl["BrojRacuna"];
                    $artikl["Naziv"] = $rowArtikl["Naziv"];
                    $artikl["Cijena"] = $rowArtikl["Cijena"];
                    $artikl["Opis"] = $rowArtikl["Opis"];
                    $artikl["Kolicina"] = $rowArtikl["Kolicina"];
                    $artikl["Ukupno"] = $rowArtikl["Ukupno"];
                    $artikl["Datum"] = $rowArtikl["Datum"];
                    $racun[] = ["Artikl" => $artikl];
                }
                array_multisort($artikl["Datum"], SORT_DESC, $rowArtikli);
            }
            $final[] = ["Racun" => $racun];
            $racun = [];
        }
        $string = strval(json_encode($final));
    } else {
        $status = "Nije OK";
        $brojRedova = 0;
        $poruka = "Greska kod dohvacanja racuna";
    }
    $status = "OK";
    $brojRedova = $brojRedova;
    $poruka = "Dohvaceno";
    vratiOdgovor($status, $brojRedova, $poruka, $string);
}


function DohvatiSveKategorije()
{
    $polje = dohvatiRezultate("SELECT * FROM Kategorija");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $red = [];
    $final = [];
    $poruka = "Kategorije su pronadene";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $red['id'] = $row["ID"];
            $red["Naziv"] = $row["Naziv"];
            $final[] = $red;
        }
    }
    vratiOdgovor($status, $brojRedova, $poruka, $final);
}

function DohvatiArtiklePoKategoriji($kategorija)
{
    $polje = dohvatiRezultate("SELECT * FROM Artikl WHERE KategorijaID='$kategorija'");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $red = [];
    $final = [];
    $string = "";
    $poruka = "Artikli su pronadeni!";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $red['id'] = $row["ID"];
            $red["urlSlike"] = $row["Slika"];
            $red["naziv"] = $row["Naziv"];
            $red["kolicinaZaliha"] = $row["Kolicina"];
            $red["minimalnaKolicina"] = $row["MinimalnaKolicina"];
            $red["opis"] = $row["Opis"];
            $idTemp = $row["Artiikl_Temporal_ID"];
            $polje2 = dohvatiRezultate("SELECT * FROM Artiikl_Temporalno_Cijena WHERE ID='$idTemp'");
            $brojRedova2 = mysqli_num_rows($polje2);
            if ($brojRedova2 > 0) {
                while ($row2 = $polje2->fetch_assoc()) {
                    $red["cijena"] = $row2["Cijena"];
                    //$red["VrijediOD"]=$row2["VrijediOD"];
                    //$red["VrijediDO"]=$row2["VrijediDO"];
                }
            }
            $final[] = $red;
        }
        $string = strval(json_encode($final));
    } else {
        $poruka = "Artikli nisu pronadeni!";
    }
    vratiOdgovor($status, $brojRedova, $poruka, $string);
}

function GenerirajRandomBrojRacuna()
{
    $broj = mt_rand(1111111, 9999999);
    $polje = dohvatiRezultate("SELECT * FROM Racun WHERE BrojRacuna='$broj'");
    $brojRedova = mysqli_num_rows($polje);
    if ($brojRedova > 0) {
        GennerirajRandomBrojRacuna();
    } else {
        return $broj;
    }
}
function RandomString()
{
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
    $randstring = '';
    for ($i = 0; $i < 10; $i++) {
        $randstring .= $characters[rand(0, strlen($characters))];
    }
    return $randstring;
}

function KreirajNoviRacun($user)
{
    $pin=RandomString();
    $brojRacuna = GenerirajRandomBrojRacuna();
    $date = (new \DateTime())->format('Y-m-d H:i:s');
    $upit = "INSERT INTO Racun VALUES(DEFAULT,'$brojRacuna',0,0,'$date',0,'$user','$pin',1,4,0)";
    izvrsiUpit($upit);
    $status = "OK";
    $red = [];
    $final = [];
    $poruka = "Inicijalna narudba je kreirana!";
    $polje = dohvatiRezultate("SELECT * FROM Racun ORDER BY id DESC LIMIT 1");
    $brojRedova = mysqli_num_rows($polje);
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $final["ID"] = $row["ID"];
        }
    }
    vratiOdgovor($status, 1, $poruka, $final);
}

function DodajStavkeRacuna($artikl, $racun, $kolicina)
{
    $upit = "INSERT INTO StavkeRacuna VALUES(DEFAULT,'$kolicina','$artikl','$racun')";
    izvrsiUpit($upit);
    $status = "OK";
    $poruka = "Stavka je dodana!";
    $final = [];
    $upit2 = "UPDATE Artikl SET Kolicina =Kolicina-'$kolicina' WHERE ID='$artikl'";
    $poruka .= " i stanje artikala azurirano!";
    izvrsiUpit($upit2);
    $polje = dohvatiRezultate("SELECT * FROM Racun WHERE ID='$racun'");
    $brojRedova = mysqli_num_rows($polje);
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $final["id"] = $row["ID"];
        }
    }
    vratiOdgovor($status, 1, $poruka, $final);
}

function DohvatiArtikl($id)
{

}

function AzurirajBodoveKorisnika($username)
{
    $polje = dohvatiRezultate("SELECT * FROM Bodovi WHERE Korisnik_ID='$username'");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $red = [];
    $final = [];
    $poruka = "Postoje bodovi";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $aktivni = $row["AktivniBodovi"];
            $upit = "Update Korisnik set Broj_Bodova='$aktivni' WHERE ID='$username'";
            izvrsiUpit($upit);
        }
    }
}

function DohvatiBodove($username)
{
    $polje = dohvatiRezultate("SELECT * FROM Korisnik WHERE ID='$username'");
    $brojRedova = mysqli_num_rows($polje);
    $bodoviKorisnika = 0;
    $status = "OK";
    $final = [];
    $poruka = "Postoje bodovi";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $bodoviKorisnika = $row["Broj_Bodova"];
            $finalrezultati["BodoviKorisnika"] = $row["Broj_Bodova"];
        }
    }
    $poljeNagrada = DohvatiRezultate("SELECT NagradaTemporalna.ID as 'NagradaTempID', NagradaTemporalna.BrojBodova as 'BrojBodova', Nagrada.Popust as 'Popust',
    Nagrada.Naziv as 'Naziv', Nagrada.ID as 'NagradaID'
    FROM NagradaTemporalna 
    LEFT JOIN Nagrada ON
    Nagrada.NagradaTemporalna_ID= NagradaTemporalna.ID
    WHERE NagradaTemporalna.BrojBodova <= '$bodoviKorisnika'
    ORDER BY NagradaTemporalna.BrojBodova DESC
    LIMIT 1");
    $brojRedovaNagrade = mysqli_num_rows($poljeNagrada);
    if ($brojRedovaNagrade > 0) {
        while ($rowNagrada = $poljeNagrada->fetch_assoc()) {
            $final["Popust"] = $rowNagrada["Popust"];
            $final["BrojBodova"] = $rowNagrada["BrojBodova"];
            $final["NagradaID"] = $rowNagrada["NagradaID"];
        }
    } else {
        $status = "Not OK";
        $poruka = "Korisnik nema dovoljno bodova za nagradu!";

    }
    vratiOdgovor($status, 1, $poruka, $final);
}

function IskoristiBodove($user, $bodovi, $nagrada)
{
    ///skini korisniku bodove
    ///
    /// postavi nagradu da je bila iskoristena
    $upit = "UPDATE Korisnik SET Broj_Bodova=Broj_Bodova-'$bodovi' WHERE ID='$user'";
    izvrsiUpit($upit);
    $poruka = "Broj bodova korisnika je azuriran";
    $upit2 = "INSERT INTO Bodovi VALUES(DEFAULT,'$bodovi','$user','$nagrada')";
    izvrsiUpit($upit2);
    $poruka .= " te zapisan";
    $status = "OK";
    $final = [];
    //racunupdate
    $brojRedova = 0;
    vratiOdgovor($status, 1, $poruka, $final);
}

function DohvatiTrenutneBodove($username)
{
    $polje = dohvatiRezultate("SELECT * FROM Korisnik WHERE KorisnickoIme='$username'");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $final = [];
    $fin = [];
    $poruka = "Bodovi su dohvaceni!";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $final["brojBodova"] = $row["Broj_Bodova"];
            $fin[] = $final;
        }
        $string = strval(json_encode($fin));
        vratiOdgovor($status, $brojRedova, $poruka, $string);
    } else {
        $poruka = "Greska kod dohvacanja!";
        $status = "Not OK";
        vratiOdgovor($status, 0, $poruka, 0);
    }
}

function DohvatiSveNagrade()
{
    $polje = dohvatiRezultate("SELECT * FROM Nagrada");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $final = [];
    $rezultat = [];
    $string = "";
    $poruka = "Nagrade su dohvacene";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $rezultat["id"] = $row["ID"];
            $rezultat["naziv"] = $row["Naziv"];
            $rezultat["popust"] = $row["Popust"];
            $idTemp = $row["NagradaTemporalna_ID"];
            $polje2 = dohvatiRezultate("SELECT * FROM NagradaTemporalna where ID='$idTemp'");
            $brojRedova2 = mysqli_num_rows($polje);
            if ($brojRedova2 > 0) {
                while ($row2 = $polje2->fetch_assoc()) {
                    $rezultat["brojBodova"] = $row2["BrojBodova"];
                }
            }
            $final[] = $rezultat;
        }
        $string = strval(json_encode($final));

    } else {
        $status = "Not OK";
        $poruka = "Nema nagrada!";
    }
    vratiOdgovor($status, $brojRedova, $poruka, $string);
}

function DodajPovratnuInformaciju($racun, $komentar, $ocjena)
{
    $upit3 = "INSERT INTO PovratnaInformacija VALUES (DEFAULT,'$komentar','$ocjena','$racun')";
    izvrsiUpit($upit3);

        $status = "OK";
        $poruka = "Narudzba je unesena!";
        $brojRedova = 1;
        $final = [];   

    vratiOdgovor($status, $brojRedova, $poruka, $final);
}

function DohvatiKolicinuArtikla($idArtikla)
{
    $polje = dohvatiRezultate("SELECT Kolicina FROM Artikl WHERE ID='$idArtikla'");
    $brojRedova = mysqli_num_rows($polje);
    $status = "OK";
    $kolicina = 0;
    $poruka = "Artikl je pronaden!";
    if ($brojRedova > 0) {
        while ($row = $polje->fetch_assoc()) {
            $kolicina = $row["Kolicina"];
        }
    } else {
        $poruka = "Artikl nije pronaden!";
    }
    vratiOdgovor($status, $brojRedova, $poruka, $kolicina);
}

function ProvijeriKorisnika($id, $email, $username)
{
    $polje = dohvatiRezultate("SELECT *FROM Korisnik WHERE KorisnickoIme='$username' AND Email='$email' AND ID='$id'");
    $brojRedova = mysqli_num_rows($polje);

    return ($brojRedova ==1) ? true : false;
}
function ProvijeriDostupnostKorisnickog($id, $email, $username){
    $polje = dohvatiRezultate("SELECT *FROM Korisnik WHERE KorisnickoIme='$username' OR Email='$email' or ID='$id'");
    $brojRedova = mysqli_num_rows($polje);

    return ($brojRedova >1) ? false : true;
}

function PromijeniKorisnickePodatke($ime, $prezime, $username, $adresa, $lozinka, $mobitel, $id, $email)
{
    $final = [];
    if (ProvijeriKorisnika($id, $email, $username) == true || ProvijeriDostupnostKorisnickog($id, $email, $username)==true) {
        izvrsiUpit("UPDATE Korisnik SET Ime='" . $ime . "',Prezime='" . $prezime . "',Adresa='" . $adresa . "',Lozinka='" . $lozinka . "',Mobitel='" . $mobitel . "', Email='" . $email . "', KorisnickoIme='" . $username . "' WHERE ID='" . $id . "'");
        vratiOdgovor("OK", 0, "Podaci su uspjesno azurirani !", $final);

    } else {
        vratiOdgovor("Nije OK", 1, "Korisničko ime ili email su zauzeti", $final);
    }

}

function DohvatiRacuneKorisnika($username)
{
    $poljeKorisnik = dohvatiRezultate("SELECT * FROM Korisnik WHERE KorisnickoIme='$username'");
    $id = 0;
    $brojRedovaKorisnika = mysqli_num_rows($poljeKorisnik);
    if ($brojRedovaKorisnika > 0) {
        while ($rowKorisnik = $poljeKorisnik->fetch_assoc()) {
            $id = $rowKorisnik["ID"];
        }
    }
    $polje = dohvatiRezultate("SELECT * FROM Racun WHERE Korisnik_ID='$id'");
    $brojRedova = mysqli_num_rows($polje);
    $final = [];
    $red = [];
    $string = "0";
    if ($brojRedova > 0) {
        $poruka = "Racuni su pronadeni!";
        $status = "OK";
        while ($row = $polje->fetch_assoc()) {
            $red["ID"] = $row["ID"];
            $red["BrojRacuna"] = $row["BrojRacuna"];
            $red["Ukupno"] = $row["Ukupno"];
            $red["QRkod"] = $row["QRkod"];
            $red["Datum"] = $row["Datum"];
            $red["Popust"] = $row["Popust"];
            $red["Korisnik_ID"] = $row["Korisnik_ID"];
            $red["PIN"] = $row["PIN"];
            $red["RestoranID"] = $row["RestoranID"];
            $red["Status_narudzbeID"] = $row["Status_narudzbeID"];
            $red["IskoristenKod"] = $row["IskoristenKod"];
            $final[] = $red;
        }
        $string = strval(json_encode($final));
    } else {
        $poruka = "Racuni nisu pronadeni!";
        $status = "Not OK";
    }
    vratiOdgovor($status, $brojRedova, $poruka, $string);
}

function DohvatiArtiklePoRacunu($idracun)
{
    $polje = dohvatiRezultate("SELECT Artiikl_Temporalno_Cijena.Cijena as 'Cijena', Artikl.ID as 'ID', Artikl.Naziv AS 'Naziv',
    Artikl.Kolicina as 'KolicinaNaSkladistu' , Artikl.MinimalnaKolicina as 'MinimalnaKolicina', 
    Artikl.Opis AS 'Opis', Artikl.KategorijaID as 'KategorijaID', Artikl.Artiikl_Temporal_ID AS 'TempID',
    StavkeRacuna.Kolicina AS 'Kolicina'
    FROM Artiikl_Temporalno_Cijena 
    LEFT JOIN Artikl ON Artiikl_Temporalno_Cijena.ID=Artikl.Artiikl_Temporal_ID
    LEFT JOIN StavkeRacuna ON Artikl.ID=StavkeRacuna.Artikl_ID
    LEFT JOIN Racun ON StavkeRacuna.Racun_ID=Racun.ID
    WHERE StavkeRacuna.Racun_ID='$idracun'");
    $brojRedova = mysqli_num_rows($polje);
    $final = [];
    $red = [];
    if ($brojRedova > 0) {
        $poruka = "Artikli su pronadeni!";
        $status = "OK";
        while ($row = $polje->fetch_assoc()) {
            $red["ID"] = $row["ID"];
            $red["Naziv"] = $row["Naziv"];
            $red["Kolicina"] = $row["KolicinaNaSkladistu"];
            $red["MinimalnaKolicina"] = $row["MinimalnaKolicina"];
            $red["Opis"] = $row["Opis"];
            $red["KategorijaID"] = $row["KategorijaID"];
            $red["Artiikl_Temporal_Id"] = $row["TempID"];
            $red["Kolicina"] = $row["Kolicina"];
            $red["Artiikl_Temporalno_Cijena"] = $row["Cijena"];
            $final[] = $red;
        }
    } else {
        $poruka = "Artikli nisu pronadeni!";
        $status = "Not OK";
    }
    vratiOdgovor($status, $brojRedova, $poruka, $final);
}

