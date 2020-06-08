package hr.foi.air.food2go.model;

public class Restoran {
    private int id;
    private String naziv;
    private String adresa;
    private String iban;
    private String oib;
    private int godinaOsnivanja;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getOib() {
        return oib;
    }

    public void setOib(String oib) {
        this.oib = oib;
    }

    public int getGodinaOsnivanja() {
        return godinaOsnivanja;
    }

    public void setGodinaOsnivanja(int godinaOsnivanja) {
        this.godinaOsnivanja = godinaOsnivanja;
    }

    public Restoran(int id, String naziv, String adresa, String iban, String oib, int godinaOsnivanja) {
        this.id = id;
        this.naziv = naziv;
        this.adresa = adresa;
        this.iban = iban;
        this.oib = oib;
        this.godinaOsnivanja = godinaOsnivanja;
    }
}
