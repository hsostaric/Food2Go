package hr.foi.air.webservice;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.foi.air.core.Artikl;
import hr.foi.air.core.BodoviVjernostiView;
import hr.foi.air.core.Korisnik;
import hr.foi.air.core.Racun;
import hr.foi.air.core.Nagrada;
import hr.foi.air.core.PovratnaInformacija;
import hr.foi.air.core.Racun;
import hr.foi.air.core.StavkeRacuna;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class WebServiceCaller {
    Retrofit retrofit;
    WebServiceHandler webServiceHandler;
    Call<WebServiceResponse> call;
    private final String baseUrl = "https://airfood2go.000webhostapp.com/Servis/";

    public WebServiceCaller(WebServiceHandler webServiceHandler) {
        this.webServiceHandler = webServiceHandler;
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
        this.retrofit = new Retrofit.Builder().baseUrl(baseUrl).addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();
    }

    public void CallForKorisnici(Korisnik data, final String method) {
        WebService webService = retrofit.create(WebService.class);
        if (method == "registracija") {
            call = webService.RegistrirajSe(data.getIme(), data.getPrezime(),
                    data.getUsername(), data.getLozinka(),
                    data.getOib(), data.getEmail(), data.getAdresa(),
                    data.getMobitel(), data.getAktivacijskiKod());
        } else if (method == "aktivacijski") {
            call = webService.AktivacijskiKod(data.getEmail(), data.getAktivacijskiKod());
        } else if (method == "prijava") {
            call = webService.PrijaviSe(data.getUsername(), data.getLozinka());
        } else if (method == "zaboravljenalozinka") {
            call = webService.ZaboravljenaLozinka(data.getLozinka(), data.getUsername());
        } else if (method == "azurirajKorisnika") {
            call = webService.AzurirajKorisnika(data.getIme(), data.getPrezime(), data.getUsername(), data.getAdresa(), data.getLozinka(), data.getMobitel(), data.getId(), data.getEmail());
        }

        else if (method=="dohvatitrenutnebodove"){
            call = webService.DohvatiTrenutneBodove(data.getUsername());
        }
        CallFromServer(method);
    }

    public void CallDohvatiArtiklePoKategoriji(String kategorija) {
        WebService webService = retrofit.create(WebService.class);
        call = webService.DohvatiArtiklePoKategoriji(kategorija);
        CallFromServer("DohvatiArtiklePoKategoriji");
    }

    public void CallDohvatiArtiklePoRacunu(int racunID){
        WebService webService = retrofit.create(WebService.class);
        call = webService.DohvatiArtikleRacuna(racunID);
        CallFromServer("dohvatiartikleracuna");
    }

    public void CallDohvatiRacune(String korisnickoIme){
        WebService webService = retrofit.create(WebService.class);
        call = webService.DohvatiRacuneKorisnika(korisnickoIme);
        CallFromServer("dohvatiracunekorisnika");
    }

    public void CallDohvatiPovratnu(int idRacuna, String komentar, float ocjena){
        WebService webService = retrofit.create(WebService.class);
        call = webService.PovratnaInformacija(idRacuna, komentar, ocjena);
        CallFromServer("dodajpovratnu");
    }

    public void CallDohvatiSveNagrade(){
        WebService webService = retrofit.create(WebService.class);
        call = webService.DohvatiSveNagrade();
        CallFromServer("dohvatisvenagrade");
    }

    public void CallDohvatiArtikleTrenutneNarudzbe(Integer racun_id) {
        WebService webService = retrofit.create(WebService.class);

    }
    public void CallForRacunQR(int korisnikID,String kod){
        WebService webService = retrofit.create(WebService.class);
        call=webService.DohvatiRacunZaProvjeru(korisnikID, kod);
        CallFromServer("racunZaProvjeru");
    }

    public void IskoristiBodove(Korisnik korisnik) {
        WebService webService = retrofit.create(WebService.class);
        call = webService.DohvatiBodoveKorisnika(korisnik.getId());
        CallFromServer("iskoristiBodove");
    }

    public void ZabiljeziBodove(Korisnik korisnik, BodoviVjernostiView bodoviVjernostiView) {
        WebService webService = retrofit.create(WebService.class);
        call = webService.ZabiljeziIskoristenjeNagrade(korisnik.getId(), bodoviVjernostiView.getBrojBodova(), bodoviVjernostiView.getNagradaID());
        CallFromServer("iskoristenje");
    }

    public void KreirajNoviRacun(Korisnik korisnik){
        WebService webService = retrofit.create(WebService.class);
        call=webService.KreirajRacun(korisnik.getId());
        CallFromServer("noviRacun");
    }
    public void DodajArtiklNaRacun(Artikl artikl, Racun racun){
        WebService webService = retrofit.create(WebService.class);
        call=webService.DodajStavkuNaRacun(artikl.getId(),racun.getID(),artikl.getKolicina());
        CallFromServer("dodajStavkeNaRacun");

    }
    public void DodajCijenuRacunu(Racun racun, float cijena) {
        WebService webService = retrofit.create(WebService.class);
        call=webService.DodajCijenuNaRacun(racun.getID(),cijena);
        Log.i("AIR_WebServiceCaller",String.valueOf(cijena));
        CallFromServer("cijenaNaRacun");
    }
    public void CallForRacun(int korisnikID,String kod) {
        WebService webService=retrofit.create(WebService.class);
        call=webService.DohvatiRacunZaProvjeru(korisnikID,kod);
        CallFromServer("racunZaProvjeru");


    }
    private void CallFromServer(final String method) {
        if (call != null) {
            call.enqueue(new Callback<WebServiceResponse>() {
                @Override
                public void onResponse(Response<WebServiceResponse> response, Retrofit retrofit) {
                    try {
                        if (response.isSuccess()) {
                            if (method == "prijava" || method == "zaboravljenalozinka" || method == "registracija" || method == "aktivacijski") {
                                HandlePojedinacanZapis(response);
                            }
                            if (method == "iskoristiBodove" || method=="iskoristenje") {
                                UpravljajBodovimaVjernosti(response);
                            }

                            if (method=="noviRacun" || method=="cijenaNaRacun" || method=="dodajStavkeNaRacun" || method=="racunZaProvjeru"){

                                try{
                                    UpravljajRacunom(response);
                                }catch (Exception ex){
                                    ex.printStackTrace();
                                }

                            }

                            if(method == "prijava" || method == "zaboravljenalozinka" || method == "registracija" || method == "aktivacijski" || method=="azurirajKorisnika") {
                                HandlePojedinacanZapis(response);
                            }
                            else if(method == "dohvatiracunekorisnika"){
                                HandlePojedinacanRacun(response);
                            }
                            else if(method == "DohvatiArtiklePoKategoriji"){
                                HandleArtiklePoKategoriji(response);
                            }
                            else if(method == "dohvatiartikleracuna"){
                                HandleArtikleRacuna(response);
                            }
                            else if(method == "dodajpovratnu"){
                                HandlePovratnaInformacija(response);
                            }
                            else if(method == "dohvatitrenutnebodove"){
                                HandleResponse(response, "dohvatitrenutnebodove");
                            }
                            else if(method == "dohvatisvenagrade"){
                                HandleResponse(response, "dohvatisvenagrade");
                            }
                            else if (method== "PosaljiMail"){
                                try{
                                    Gson gson = new Gson();
                                    Object object = gson.fromJson(gson.toJson(response.body().getPodaci()),Object.class);
                                    if (webServiceHandler != null) {
                                        webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), object);
                                    }
                                } catch (Exception ex) {
                                    ex.getMessage();
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                @Override
                public void onFailure(Throwable t) {
                    Log.e("SS", t.getMessage());
                }
            });
        }
    }


    public void HandleResponseFromCall(final String method) {
        if (call != null) {
            call.enqueue(new Callback<WebServiceResponse>() {
                @Override
                public void onResponse(Response<WebServiceResponse> response, Retrofit retrofit) {
                    try {
                        if (response.isSuccess()) {
                            if (webServiceHandler != null) {
                                if (method == "DohvatiArtiklePoKategoriji") {
                                    HandleArtiklePoKategoriji(response);
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.e("SS", t.getMessage());
                }
            });
        }
    }


    private void HandlePojedinacanZapis(Response<WebServiceResponse> response){
        try{
            Gson gson = new Gson();
            Korisnik[] korisnik = gson.fromJson(gson.toJson(response.body().getPodaci()), Korisnik[].class);
            if (webServiceHandler != null) {
                webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), Arrays.asList(korisnik));
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void HandlePovratnaInformacija(Response<WebServiceResponse> response){
        Gson gson = new Gson();
        PovratnaInformacija[] povratna = gson.fromJson(response.body().getPodaci().toString(), PovratnaInformacija[].class);
        webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), Arrays.asList(povratna));
    }

    private void HandlePojedinacanRacun(Response<WebServiceResponse> response){
        Gson gson = new Gson();
        Racun[] racuni = gson.fromJson(response.body().getPodaci().toString(), Racun[].class);
        webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), Arrays.asList(racuni));
    }

    private void HandleArtikleRacuna(Response<WebServiceResponse> response){
        try{
            Gson gson = new Gson();
            StavkeRacuna[] stavke = gson.fromJson( gson.toJson(response.body().getPodaci()),StavkeRacuna[].class);
            if (webServiceHandler != null){
                webServiceHandler.onDataArrived(response.body().getPoruka(),response.body().getStatus(), Arrays.asList(stavke) );
                Log.i("tag", "stavke" + stavke);
            }
        }catch (Exception ex) {
            ex.getMessage();
            Log.i("tag", "stavke" + ex.getMessage());
        }
    }

    private void HandleArtiklePoKategoriji(Response<WebServiceResponse> response) {
        Gson gson = new Gson();
        Artikl[] artikli = gson.fromJson(response.body().getPodaci().toString(), Artikl[].class);
        webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), Arrays.asList(artikli));
    }

    private void UpravljajBodovimaVjernosti(Response<WebServiceResponse> response) {
        try {
            Gson gson = new Gson();
            BodoviVjernostiView bodoviVjernosti = gson.fromJson(gson.toJson(response.body().getPodaci()), BodoviVjernostiView.class);
            if (webServiceHandler != null) {
                webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), bodoviVjernosti);
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }

    private void  UpravljajRacunom(Response<WebServiceResponse> response){
        try {
            Gson gson = new Gson();
             Racun racun = gson.fromJson(gson.toJson(response.body().getPodaci()), Racun.class);
            if (webServiceHandler != null) {
                webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), racun);
            }
        } catch (Exception ex) {
            ex.getMessage();
        }
    }


    private void HandleResponse(Response<WebServiceResponse> response, String method){
        if(method == "dohvatitrenutnebodove"){
            Gson gson = new Gson();
            Korisnik[] korisnici = gson.fromJson(response.body().getPodaci().toString(), Korisnik[].class);
            webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), Arrays.asList(korisnici));
        }
        if(method == "dohvatisvenagrade"){
            Gson gson = new Gson();
            Nagrada[] nagrade = gson.fromJson(response.body().getPodaci().toString(), Nagrada[].class);
            webServiceHandler.onDataArrived(response.body().getPoruka(), response.body().getStatus(), Arrays.asList(nagrade));
        }
        //tu stavljate sve HandleResponsove po metodama
    }


    public void CallPosaljiMail(int id) {
        WebService webService = retrofit.create(WebService.class);
        call=webService.PosaljiRacunNaMail(id);
        CallFromServer("PosaljiMail");
    }
}
