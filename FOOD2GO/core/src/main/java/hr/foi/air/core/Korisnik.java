package hr.foi.air.core;

public class Korisnik {

    private int id;
    private String ime;
    private String prezime;
    private String username;
    private String lozinka;
    private int status;
    private int brojPokusaja;
    private String adresa;
    private String oib;
    private String email;
    private String mobitel;
    private String aktivacijskiKod;
    private int brojBodova;

    public int getBrojBodova() { return  brojBodova; }

    public void setBrojBodova(int brojBodova) { this.brojBodova = brojBodova; }
    private static Korisnik prijavljeniKorisnik;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getMobitel() {
        return mobitel;
    }

    public void setMobitel(String mobitel) {
        this.mobitel = mobitel;
    }

    public String getAktivacijskiKod() {
        return aktivacijskiKod;
    }

    public void setAktivacijskiKod(String aktivacijskiKod) {
        this.aktivacijskiKod = aktivacijskiKod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBrojPokusaja() {
        return brojPokusaja;
    }

    public void setBrojPokusaja(int brojPokusaja) {
        this.brojPokusaja = brojPokusaja;
    }

     public static Korisnik getPrijavljeniKorisnik() {
        return prijavljeniKorisnik;
    }

    public static void setPrijavljeniKorisnik(Korisnik prijavljeniKorisnik) {
        Korisnik.prijavljeniKorisnik = prijavljeniKorisnik;
    }

    public String vratiImeiPrezime(){
        return this.ime+" "+this.prezime;
    }

    public Korisnik() {
    }

    public Korisnik(String ime, String prezime, String username, String lozinka, String oib, String email, String adresa, String mobitel, String aktivacijskiKod) {
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.lozinka = lozinka;
        this.oib = oib;
        this.email = email;
        this.adresa = adresa;
        this.mobitel = mobitel;
        this.aktivacijskiKod = aktivacijskiKod;
    }

    public Korisnik(String username, String lozinka) {
        this.username = username;
        this.lozinka = lozinka;
    }
}