package hr.foi.air.core;

public class Nagrada {
    private int id;
    private String naziv;
    private int popust;
    private int brojBodova;

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

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public int getBrojBodova() {
        return brojBodova;
    }

    public void setBrojBodova(int brojBodova) {
        this.brojBodova = brojBodova;
    }

    public Nagrada(int id, String naziv, int popust, int brojBodova) {
        this.id = id;
        this.naziv = naziv;
        this.popust = popust;
        this.brojBodova = brojBodova;
    }

    public Nagrada(){

    }
}
