package hr.foi.air.food2go.fragmenti.trenutna_narudzba;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.interfaces.HttpResponseCallback;
import com.braintreepayments.api.internal.HttpClient;
import com.braintreepayments.api.models.PaymentMethodNonce;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.Artikl;
import hr.foi.air.core.BodoviVjernostiView;
import hr.foi.air.core.Korisnik;
import hr.foi.air.core.Racun;
import hr.foi.air.food2go.GlavniActivity;
import hr.foi.air.food2go.MainActivity;
import hr.foi.air.food2go.controller.Internet;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;
import hr.foi.air.food2go.fragmenti.odabir_potkategorije.OdabirPotkategorijeFragment;
import hr.foi.air.food2go.recyclerview.TrenutnaNarudzbaRecyclerAdapter;

import hr.foi.air.food2go.R;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class TrenutnaNarudzbaFragment extends Fragment implements DataLoadedListener, View.OnClickListener {

    private static final int REQUEST_CODE = 1234;
    final String API_GET_TOKEN = "https://airfood2go.000webhostapp.com/Servis/Braintree/main.php";
    final String API_CHECK_OUT = "https://airfood2go.000webhostapp.com/Servis/Braintree/checkout.php";
    String token;
    HashMap <String, String> paramsHash;

    private ArrayList<Artikl> artikliNarudzbe = new ArrayList<>();
    private WsDataLoader wsDataLoader;
    private float ukupnaCijena;
    private BodoviVjernostiView bodoviVjernostiView = null;
    public static boolean iskoristenPopust = false;
    private Type entityType;
    private boolean dodaneStavke = false;
    private Racun racun = null;
    View v;
    @BindView(R.id.txtCijena)
    TextView ukupno;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = container;
        View view = inflater.inflate(R.layout.fragment_trenutna_narudzba, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    public void IzracunajUkupno() {
        Float ukupno2 = 0.0f;
        for (Artikl a : artikliNarudzbe) {
            Float cijenaArtikla = a.getCijena();
            int kolicina = a.getKolicina();
            ukupno2 += (cijenaArtikla * kolicina);
        }

        ukupno.setText(ukupno2.toString());
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (Internet.isNetworkAvailable(getContext()) == true) {
            try {
                getSharedPrefs();
                artikliNarudzbe = OdabirPotkategorijeFragment.listaArtikalaUKosarici;
                IzracunajUkupno();
                wsDataLoader = new WsDataLoader();
                iskoristenPopust=false;
                dodaneStavke = false;
                DohvatiIzgled();
                wsDataLoader.DohvatiArtiklePoKategoriji(this, "1");

                new getToken().execute();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            androidx.appcompat.app.AlertDialog alertDialog = new androidx.appcompat.app.AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Pogreška u internet vezi");
            alertDialog.setMessage("Molimo Vas omogućite internetsku vezu kako biste koristili aplikaciju.");
            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                PaymentMethodNonce nonce = result.getPaymentMethodNonce();
                String strNonce = nonce.getNonce();
                if(!ukupno.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "Trenutak molim...", Toast.LENGTH_SHORT).show();
                    paramsHash = new HashMap<>();
                    paramsHash.put("amount", ukupno.getText().toString());
                    paramsHash.put("nonce", strNonce);

                    sendPayments();
                }
                else{
                    Toast.makeText(getActivity(),"Molimo Vas odaberite artikle.", Toast.LENGTH_SHORT).show();
                }
            }
            else if(resultCode == RESULT_CANCELED)
                Toast.makeText(getActivity(), "Otkazivanje...", Toast.LENGTH_SHORT).show();
            else{
                Exception error = (Exception)data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Log.d("EDMT_ERROR", error.toString());
            }
        }
    }

    private void sendPayments() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, API_CHECK_OUT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.toString().toLowerCase().contains("successful")){
                            if (iskoristenPopust == true) {
                                wsDataLoader.ZabiljeziBodoveVjernosti(Korisnik.getPrijavljeniKorisnik(), bodoviVjernostiView);
                            }
                            wsDataLoader.KreirajRacun(Korisnik.getPrijavljeniKorisnik());
                            if (racun != null) {
                                dodajArtikleNaRacun(racun);
                            }
                        }
                        else{
                            Toast.makeText(getActivity(), "Nesupješna transakcija", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("EDMT_LOG", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("EDMT_ERROR", error.toString());
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if(paramsHash == null)
                    return null;
                Map<String, String> params = new HashMap<>();
                for(String key:paramsHash.keySet())
                {
                    params.put(key,paramsHash.get(key));
                }
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private class getToken extends AsyncTask{

        ProgressDialog mDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog = new ProgressDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog);
            mDialog.setCancelable(false);
            mDialog.setMessage("Trenutak molim!");
            mDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient client = new HttpClient();
            client.get(API_GET_TOKEN, new HttpResponseCallback() {
                @Override
                public void success(final String responseBody) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            token = responseBody;
                        }
                    });
                }

                @Override
                public void failure(Exception exception) {
                    Log.d("EDMT_ERROR", exception.toString());
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            mDialog.dismiss();
        }
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {

        if (entityType == BodoviVjernostiView.class  ) {
            if(status.equals("OK")){
                uracunajPopust(status, (BodoviVjernostiView) data);

            }else {
                Toast.makeText(getContext(),"Nemate pravo na popust !",Toast.LENGTH_SHORT).show();
            }

        }

        radSNarudzbom(data);
    }

    private void radSNarudzbom(Object data) {

        if (entityType == Racun.class && dodaneStavke == false) {
            dodajArtikleNaRacun((Racun) data);
        }
    }

    private void getSharedPrefs() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    }

    private void DohvatiIzgled() {
        RecyclerView recyclerView = v.findViewById(R.id.trenutna_narudzbaRecycler);
        TrenutnaNarudzbaRecyclerAdapter adapter = new TrenutnaNarudzbaRecyclerAdapter(getActivity(), artikliNarudzbe);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setOnClickListener(this);
    }

    @OnClick(R.id.uiActionIskoristi)
    public void IskoristiBodove() {
        entityType = BodoviVjernostiView.class;
        wsDataLoader.IskoristiBodoveVjernosti(Korisnik.getPrijavljeniKorisnik());

    }

    private void uracunajPopust(String status, BodoviVjernostiView data) {
        if (status.equals("OK")) {
            bodoviVjernostiView = data;
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Iskoristi bodove");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Poštovani, imate ").append(data.getBrojBodova()).
                    append(" bodova i imate pravo na ").append(data.getPopust()).append("% popusta, želite li iskoristiti nagradu ?");
            alertDialog.setMessage(stringBuilder.toString());
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Iskoristi",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (iskoristenPopust == false) {
                                float popust = Float.parseFloat(ukupno.getText().toString()) * data.getPopust() / 100;
                                ukupnaCijena = Float.parseFloat(ukupno.getText().toString()) - popust;
                                ukupno.setText(String.valueOf(ukupnaCijena));
                                iskoristenPopust = true;
                                dialog.cancel();
                            } else {
                                AlertDialog alert = new AlertDialog.Builder(getContext()).create();
                                alert.setTitle("Upozorenje !");
                                alert.setMessage("Već ste iskoristili nagradu za ovaj račun !!!");
                                alert.setButton(AlertDialog.BUTTON_POSITIVE, "U redu", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }
                        }
                    });
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Odustani", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialog.show();
        }
    }

    @OnClick(R.id.uiActionNaruci)
    void kreirajNarudzbu() {
        entityType = Racun.class;
        if (Internet.isNetworkAvailable(getContext()) == true) {
            if(artikliNarudzbe.size()==0){
                Toast.makeText(getContext(),"Košarica je prazna !!!",Toast.LENGTH_SHORT).show();
            }else{
                submitPayment();
            }
        }
        else {
            Toast.makeText(getContext(), "Nema interneta", Toast.LENGTH_SHORT).show();
        }

    }

    private void submitPayment() {
        DropInRequest dropInRequest = new DropInRequest().clientToken(token);
        startActivityForResult(dropInRequest.getIntent(getActivity()), REQUEST_CODE);

    }

    private void dodajArtikleNaRacun(Racun racun) {

        float cijena = Float.parseFloat(ukupno.getText().toString());
        wsDataLoader.DodajCijenuNaRacun(racun, cijena);
        for (Artikl artikl : OdabirPotkategorijeFragment.listaArtikalaUKosarici) {
            if (artikl.getKolicina() > 0) {
                wsDataLoader.dodajArtikleNaNarudzbe(artikl, racun);
            }
        }
        dodaneStavke = true;
        AlertDialog alert = new AlertDialog.Builder(getContext()).create();
        alert.setTitle("Uspješna transakcija!");
        ///slanje maila

        wsDataLoader.PosaljiRacun(racun.getID(),this);
        alert.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialog, int which) {
                 dialog.dismiss();
            }
        });
        alert.show();
        artikliNarudzbe.clear();
        OdabirPotkategorijeFragment.listaArtikalaUKosarici.clear();
        DohvatiIzgled();
        IzracunajUkupno();
    }


    @Override
    public void onClick(View v) {

    }
}