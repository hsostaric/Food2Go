package hr.foi.air.food2go.model;

public class StatusNarudzbe {
    private int id;
    private String naziv;

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

    public StatusNarudzbe(int id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }
}
