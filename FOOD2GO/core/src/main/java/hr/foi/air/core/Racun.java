package hr.foi.air.core;

import java.sql.Timestamp;

public class Racun {
    private int ID;
    private String BrojRacuna;
    private float Ukupno;
    private String QRkod;
    private String Datum;
    private int Popust;
    private int Korisnik_ID;
    private String PIN;
    private int RestoranID;
    private int Status_narudzbeID;
    private boolean IskoristenKod;

    public int getID() {
        return ID;
    }

    public void setID(int ID) { this.ID = ID; }

    public int getKorisnik_ID() {
        return Korisnik_ID;
    }

    public void setKorisnik_ID(int Korisnik_ID) {
        this.Korisnik_ID = Korisnik_ID;
    }

    public String getPIN() {
        return PIN;
    }

    public void setPIN(String PIN) {
        this.PIN = PIN;
    }

    public int getRestoranID() {
        return RestoranID;
    }

    public void setRestoranID(int RestoranID) {
        this.RestoranID = RestoranID;
    }

    public int getStatus_narudzbeID() {
        return Status_narudzbeID;
    }

    public void setStaus_narudzbeID(int Staus_narudzbeID) { this.Status_narudzbeID = Staus_narudzbeID; }

    public String getBrojRacuna() {
        return BrojRacuna;
    }

    public void setBrojRacuna(String BrojRacuna) {
        this.BrojRacuna = BrojRacuna;
    }

    public float getUkupno() {
        return Ukupno;
    }

    public void setUkupno(float Ukupno) {
        this.Ukupno = Ukupno;
    }

    public String getQRkod() {
        return QRkod;
    }

    public void setQRkod(String QRkod) {
        this.QRkod = QRkod;
    }

    public boolean getIskoristenKod() {
        return IskoristenKod;
    }

    public void setIskoristenKod(boolean IskoristenKod) {
        this.IskoristenKod = IskoristenKod;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String Datum) {
        this.Datum = Datum;
    }

    public int getPopust() {
        return Popust;
    }

    public void setPopust(int Popust) {
        this.Popust = Popust;
    }

    public Racun(int ID, String BrojRacuna, float Ukupno, String QRkod, String Datum, int Popust, int Korisnik_ID, String PIN, int RestoranID, int Status_narudzbeID, boolean IskoristenKod) {
        this.ID = ID;
        this.BrojRacuna = BrojRacuna;
        this.Ukupno = Ukupno;
        this.QRkod = QRkod;
        this.Datum = Datum;
        this.Popust = Popust;
        this.Korisnik_ID = Korisnik_ID;
        this.PIN = PIN;
        this.RestoranID = RestoranID;
        this.Status_narudzbeID = Status_narudzbeID;
        this.IskoristenKod = IskoristenKod;
    }

    public Racun(){

    }
}
