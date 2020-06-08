package hr.foi.air.core;

public class StavkeRacuna {
    private String ID;
    private String Naziv;
    private String Kolicina;
    private String MinimalnaKolicina;
    private String Opis;
    private String KategorijaID;
    private String Artiikl_Temporal_Id;
    private String Artiikl_Temporalno_Cijena;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNaziv() {
        return Naziv;
    }

    public void setNaziv(String Naziv) {
        this.Naziv = Naziv;
    }

    public String getKolicina() {
        return Kolicina;
    }

    public void setKolicina(String Kolicina) {
        this.Kolicina = Kolicina;
    }

    public String getMinimalnaKolicina() {
        return MinimalnaKolicina;
    }

    public void setMinimalnaKolicina(String MinimalnaKolicina) {
        this.MinimalnaKolicina = MinimalnaKolicina;
    }

    public String getOpis() {
        return Opis;
    }

    public void setOpis(String Opis) {
        this.Opis = Opis;
    }

    public String getKategorijaID() {
        return KategorijaID;
    }

    public void setKategorijaID(String KategorijaID) {
        this.KategorijaID = KategorijaID;
    }

    public String getArtiikl_Temporal_Id() {
        return Artiikl_Temporal_Id;
    }

    public void setArtiikl_Temporal_Id(String Artiikl_Temporal_Id) {
        this.Artiikl_Temporal_Id = Artiikl_Temporal_Id;
    }

    public String getArtiikl_Temporalno_Cijena() {
        return Artiikl_Temporalno_Cijena;
    }

    public void setArtiikl_Temporalno_Cijena(String artiikl_Temporalno_Cijena) {
        Artiikl_Temporalno_Cijena = artiikl_Temporalno_Cijena;
    }

    public StavkeRacuna() {
    }

    public StavkeRacuna(String ID, String Naziv, String Kolicina, String MinimalnaKolicina, String Opis, String KategorijaID, String Artiikl_Temporal_Id, String Artiikl_Temporalno_Cijena) {
        this.ID = ID;
        this.Naziv = Naziv;
        this.Kolicina = Kolicina;
        this.MinimalnaKolicina = MinimalnaKolicina;
        this.Opis = Opis;
        this.KategorijaID = KategorijaID;
        this.Artiikl_Temporal_Id = Artiikl_Temporal_Id;
        this.Artiikl_Temporalno_Cijena = Artiikl_Temporalno_Cijena;
    }
}
