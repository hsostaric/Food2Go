<?php
include('funkcije.php');
header("Content-Type:application/json");

if (isset($_GET)) {
    if (!empty($_GET['korisnici'])) {
        if ($_GET['korisnici'] == 'svi') {
            DohvatiSveKorisnike();
        }
    }
    elseif (!empty($_GET['username']) && !empty($_GET['password'])) {
        DohvatiKorisnika($_GET['username'], $_GET['password']);
    }
    elseif (isset($_GET['metoda'])) {
        if ($_GET["metoda"] == "reg") {
            if (!empty($_GET['ime']) && !empty($_GET['prezime']) && !empty($_GET['korisnickoime']) && !empty($_GET['lozinka']) && !empty($_GET['oib']) && !empty($_GET['email'])) {
                RegistrirajKorisnika(($_GET['ime']), ($_GET['prezime']), ($_GET['korisnickoime']), ($_GET['lozinka']), (($_GET['oib'])), ($_GET['email']), ($_GET['adresa']), ($_GET['brojmobitela']), "0", "0", ($_GET['aktivacijski']), "0", "2");
            }
        }
        elseif ($_GET["metoda"] == "zaboravljena") {
            if (!empty($_GET['email']) && !empty($_GET['username'])) {
                PosaljiLozinku($_GET['email'], $_GET['username']);
            }
        }
        elseif ($_GET["metoda"] == "slanjeracuna") {
            if (!empty($_GET['racunid'])) {
                PosaljiMail($_GET['racunid']);
            }
        }
        elseif ($_GET["metoda"] == "kod") {
            if (!empty($_GET['email']) && !empty($_GET['aktivacijski'])) {
                AktivirajKorisnika($_GET['email'], $_GET['aktivacijski']);
            }
        }
        elseif ($_GET["metoda"] == "dohvatiracunzaprovjeru") {
            if (!empty($_GET['korisnikid']) && !empty($_GET['kod'])) {
                DohvatiRacuneZaProvjeru($_GET['korisnikid'], $_GET['kod']);
            }
        }
        elseif ($_GET["metoda"] == "narudbe") {
            if (!empty($_GET['username'])) {
                DohvatiNarudzbe($_GET['username']);
            }
        }
        elseif ($_GET["metoda"] == "blokiraj") {
            if (!empty($_GET['username']) && !empty($_GET['blokiraj'])) {
                BlokirajKorisnika($_GET['username'], $_GET['blokiraj']);
            }
        }
        elseif ($_GET["metoda"] == "kategorije" AND $_GET["kategorije"] == "sve") {
            DohvatiSveKategorije();
        }
        elseif ($_GET["metoda"] == "artikli") {
            if (!empty($_GET['kategorije'])) {
                DohvatiArtiklePoKategoriji($_GET['kategorije']);
            }
        }
        elseif ($_GET["metoda"] == "racun" AND $_GET["tip"] == "novi") {
            if (!empty($_GET["korisnikid"])) {
                KreirajNoviRacun($_GET["korisnikid"]);
            }

        }
        elseif ($_GET["metoda"] == "dodajnarudbi") {
            if (!empty($_GET["artiklid"])) {
                DohvatiArtikl($_GET["artiklid"]);
            }
        }
        elseif ($_GET["metoda"] == "dohvatibodove") {
            if (!empty($_GET["userid"]))
                DohvatiBodove($_GET["userid"]);
        }
        elseif ($_GET["metoda"] == "iskoristibodove") {
            if (!empty($_GET["userid"] AND !empty($_GET["iskoristenibodovi"]) AND !empty($_GET["nagradaid"]))) {
                IskoristiBodove($_GET["userid"], $_GET["iskoristenibodovi"], $_GET["nagradaid"]);
            }
        }
        elseif ($_GET["metoda"] == "dohvatitrenutnebodove") {
            if (!empty($_GET["userid"])) {
                DohvatiTrenutneBodove($_GET["userid"]);
            }
        }
        elseif ($_GET["metoda"] == "dohvatisvenagrade") {
            DohvatiSveNagrade();
        }
        elseif ($_GET["metoda"] == "kolicinaartikla") {
            if (!empty($_GET["idartikla"]))
                DohvatiKolicinuArtikla($_GET["idartikla"]);
        }
        elseif ($_GET["metoda"] == "dodajstavkeracuna") {
            if (!empty($_GET["artiklid"]) AND !empty($_GET["racunid"]) AND !empty($_GET["kolicina"])) {
                DodajStavkeRacuna($_GET["artiklid"], $_GET["racunid"], $_GET["kolicina"]);
            }
        }
        elseif ($_GET["metoda"] == "azurirajKorisnika") {
            if (!empty($_GET["ime"]) && !empty($_GET["prezime"]) && !empty($_GET["username"]) && !empty($_GET["lozinka"]) && !empty($_GET["id"]) && !empty($_GET["email"])) {
                PromijeniKorisnickePodatke(($_GET["ime"]), ($_GET["prezime"]), ($_GET["username"]), ($_GET["adresa"]), ($_GET["lozinka"]), ($_GET["mobitel"]), ($_GET['id']), ($_GET["email"]));
            }
        }
        elseif ($_GET["metoda"] == "racunikorisnika") {
            if (!empty($_GET["korisnikuser"])) {
                DohvatiRacuneKorisnika($_GET["korisnikuser"]);
            }

        }
        elseif ($_GET["metoda"] == "artikliracuna") {
            if (!empty($_GET["racunid"])) {
                DohvatiArtiklePoRacunu($_GET["racunid"]);
            }
        }
        elseif ($_GET["metoda"] == "dodajcijenuracunu") {
            if (!empty($_GET["racunid"]) && !empty($_GET["cijena"])) {
                DodajCijenuRacunu($_GET["racunid"], $_GET["cijena"]);
            }
        }
        elseif ($_GET["metoda"] == "dodajpovratnu") {
            if (!empty($_GET["racunid"]) && !empty($_GET["komentar"]) && !empty($_GET["ocjena"])) {
                DodajPovratnuInformaciju($_GET["racunid"], $_GET["komentar"], $_GET["ocjena"]);
            }
        }
    }

}
