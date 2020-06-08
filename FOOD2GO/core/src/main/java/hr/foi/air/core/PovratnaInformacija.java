package hr.foi.air.core;

public class PovratnaInformacija {
    private int id;
    private String komentar;
    private int ocjena;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public int getOcjena() {
        return ocjena;
    }

    public void setOcjena(int ocjena) {
        this.ocjena = ocjena;
    }

    public PovratnaInformacija(int id, String komentar, int ocjena) {
        this.id = id;
        this.komentar = komentar;
        this.ocjena = ocjena;
    }
}
