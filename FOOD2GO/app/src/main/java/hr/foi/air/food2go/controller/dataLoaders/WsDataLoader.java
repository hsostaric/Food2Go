package hr.foi.air.food2go.controller.dataLoaders;

import android.util.Log;

import hr.foi.air.core.Artikl;
import hr.foi.air.core.BodoviVjernostiView;
import hr.foi.air.core.Korisnik;
import hr.foi.air.core.Racun;
import hr.foi.air.webservice.WebServiceCaller;
import hr.foi.air.webservice.WebServiceHandler;

public class WsDataLoader {
    private DataLoadedListener dataLoadedListener;
    private WebServiceCaller webServiceCaller;
    WebServiceHandler webServiceHandler = new WebServiceHandler() {
        @Override
        public void onDataArrived(String message, String status, Object data) {
            dataLoadedListener.onDataLoaded(message,status,data);
        }

    };
    public WsDataLoader() {
        webServiceCaller = new WebServiceCaller(webServiceHandler);
    }

    public void Registracija(Korisnik korisnik, DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallForKorisnici(korisnik, "registracija");
    }

    public void Aktivacija(Korisnik korisnik, DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallForKorisnici(korisnik, "aktivacijski");
    }

    public void Prijava(Korisnik korisnik, DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallForKorisnici(korisnik, "prijava");
    }

    public void ZaboravljenaLozinka(Korisnik korisnik, DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallForKorisnici(korisnik, "zaboravljenalozinka");
    }

    public void AzurirajKorisnika(Korisnik korisnik, DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallForKorisnici(korisnik, "azurirajKorisnika");
    }

    public void IspisiRacune(String korisnickoIme, DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallDohvatiRacune(korisnickoIme);
    }

    public void IspisiArtikleRacuna(int racunID, DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallDohvatiArtiklePoRacunu(racunID);
    }

    public void DodajPovratnu(int racunID, String komentar, float ocjena, DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallDohvatiPovratnu(racunID, komentar, ocjena);
    }

    public void DohvatiArtiklePoKategoriji(DataLoadedListener dataLoadedListener, String kategorija){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallDohvatiArtiklePoKategoriji(kategorija);
    }
    public void DohvatiArtikleTrenutneNarudzbe(DataLoadedListener dataLoadedListener,Integer racun_id){
        this.dataLoadedListener= dataLoadedListener;
        webServiceCaller.CallDohvatiArtikleTrenutneNarudzbe(racun_id);
    }
    public void IskoristiBodoveVjernosti(Korisnik korisnik){
        this.dataLoadedListener= dataLoadedListener;
        webServiceCaller.IskoristiBodove(korisnik);
    }

    public void ZabiljeziBodoveVjernosti(Korisnik korisnik, BodoviVjernostiView bodoviVjernostiView){
        this.dataLoadedListener= dataLoadedListener;
        webServiceCaller.ZabiljeziBodove(korisnik,bodoviVjernostiView);
    }

    public void KreirajRacun(Korisnik korisnik){
        this.dataLoadedListener=dataLoadedListener;
        webServiceCaller.KreirajNoviRacun(korisnik);
    }

    public void dodajArtikleNaNarudzbe(Artikl artikl, Racun racun){
        this.dataLoadedListener=dataLoadedListener;
        webServiceCaller.DodajArtiklNaRacun(artikl,racun);
    }

    public void DodajCijenuNaRacun(Racun racun, float cijena) {
        this.dataLoadedListener = dataLoadedListener;
        Log.i("AIR_WSDataLoader", String.valueOf(cijena));
        webServiceCaller.DodajCijenuRacunu(racun, cijena);
    }

    public void DohvatiTrenutneBodove(Korisnik korisnik, DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallForKorisnici(korisnik, "dohvatitrenutnebodove");
    }

    public void DohvatiSveNagrade(DataLoadedListener dataLoadedListener){
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallDohvatiSveNagrade();
    }
    public void DohvatiRacun(int korisnikID,String kod, DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallForRacun(korisnikID,kod);
    }

    public void PosaljiRacun(int id,DataLoadedListener dataLoadedListener) {
        this.dataLoadedListener = dataLoadedListener;
        webServiceCaller.CallPosaljiMail(id);

    }
}
