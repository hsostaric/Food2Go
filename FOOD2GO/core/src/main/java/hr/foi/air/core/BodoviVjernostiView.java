package hr.foi.air.core;

public class BodoviVjernostiView {
    int Popust;
    int BrojBodova;
    int NagradaID;

    public BodoviVjernostiView(int popust, int brojBodova, int nagradaID) {
        Popust = popust;
        this.BrojBodova = brojBodova;
        NagradaID = nagradaID;
    }

    public int getPopust() {
        return Popust;
    }

    public void setPopust(int popust) {
        Popust = popust;
    }

    public int getBrojBodova() {
        return BrojBodova;
    }

    public void setBrojBodova(int brojBodova) {
        this.BrojBodova = brojBodova;
    }

    public int getNagradaID() {
        return NagradaID;
    }

    public void setNagradaID(int nagradaID) {
        NagradaID = nagradaID;
    }
}
