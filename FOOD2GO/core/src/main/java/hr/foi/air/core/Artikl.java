package hr.foi.air.core;

public class Artikl {
    private int id;
    private String urlSlike;
    private String naziv;
    private float cijena;
    private int kolicinaZaliha;
    private int minimalnaKolicina;
    private String opis;
    private int KolicinaTrenutna;
    private int kolicina;

    public void setKolicina(int kolicina) { this.kolicina = kolicina; }

    public void setKolicinaTrenutna(int kolicinaTrenutna) { this.KolicinaTrenutna = kolicinaTrenutna; }

    public int getKolicina() { return this.kolicina; }

    public int getKolicinaTrenutna(){
        return this.KolicinaTrenutna;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
  
    public String getUrlSlike() {
        return urlSlike;
    }

    public void setUrlSlike(String urlSlike) {
        this.urlSlike = urlSlike;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public float getCijena() {
        return cijena;
    }

    public void setCijena(float cijena) {
        this.cijena = cijena;
    }

    public int getKolicinaZaliha() {
        return kolicinaZaliha;
    }

    public void setKolicinaZaliha(int kolicinaZaliha) {
        this.kolicinaZaliha = kolicinaZaliha;
    }

    public int getMinimalnaKolicina() {
        return minimalnaKolicina;
    }

    public void setMinimalnaKolicina(int minimalnaKolicina) {
        this.minimalnaKolicina = minimalnaKolicina;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Artikl(int id, String naziv, String urlSlike, float cijena, int kolicinaZaliha, int minimalnaKolicina, String opis) {
        this.id = id;
        this.urlSlike = urlSlike;
        this.naziv = naziv;
        this.cijena = cijena;
        this.kolicinaZaliha = kolicinaZaliha;
        this.minimalnaKolicina = minimalnaKolicina;
        this.opis = opis;
    }

    public Artikl(String naziv, float cijena, String opis) {
        this.naziv = naziv;
        this.cijena = cijena;
        this.opis = opis;
    }
}
