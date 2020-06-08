package hr.foi.air.food2go.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.Korisnik;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;

public class ZaboravljenaLozinkaActivity extends AppCompatActivity implements DataLoadedListener {

    private EditText email, korisnickoIme;
    private WsDataLoader wsDataLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zaboravljena_lozinka);
        ButterKnife.bind(this);

        email = (EditText) findViewById(R.id.email);
        korisnickoIme = (EditText) findViewById(R.id.uiInputKorisnickoIme);
    }

    @OnClick(R.id.uiActionLozinka)
    public void KlikZaboravljenaLozinka(View v) {
        if(Internet.isNetworkAvailable(this) == true) {
            String mEmail = email.getText().toString().trim();
            String mKorime = korisnickoIme.getText().toString().trim();

            if(mEmail.isEmpty() || mKorime.isEmpty()){
                AlertDialog alertDialog = new AlertDialog.Builder(ZaboravljenaLozinkaActivity.this).create();
                alertDialog.setTitle("Nisu popunjeni svi podaci!");
                alertDialog.setMessage("Molimo Vas unesite korisničko ime i e-mail!");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            else {
                Korisnik korisnik = new Korisnik(mKorime, mEmail);
                wsDataLoader = new WsDataLoader();
                wsDataLoader.ZaboravljenaLozinka(korisnik, this);
            }
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(ZaboravljenaLozinkaActivity.this).create();
            alertDialog.setTitle("Pogreška u internet vezi");
            alertDialog.setMessage("Molimo Vas omogućite internetsku vezu kako biste promjenili lozinku.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if (status.equals("OK")){
            AlertDialog alertDialog = new AlertDialog.Builder(ZaboravljenaLozinkaActivity.this).create();
            alertDialog.setTitle("Provjerite svoj e-mail!");
            alertDialog.setMessage("Poslan Vam je e-mail s novom lozinkom.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(ZaboravljenaLozinkaActivity.this).create();
            alertDialog.setTitle("Neispravni podaci!");
            alertDialog.setMessage("Uneseni podaci nisu točni, molimo Vas unesite ispravne podatke.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }
}
