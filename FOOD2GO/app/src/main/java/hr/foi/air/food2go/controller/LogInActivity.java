package hr.foi.air.food2go.controller;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.Korisnik;
import hr.foi.air.food2go.GlavniActivity;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;

public class LogInActivity extends AppCompatActivity implements DataLoadedListener {

    private EditText email, lozinka;

    private WsDataLoader wsDataLoader;

    private boolean prijavljen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        ButterKnife.bind(this);

        email = findViewById(R.id.korisnickoIme);
        lozinka = findViewById(R.id.lozinka);
    }

    @OnClick(R.id.prijava)
    public void KlikPrijava(View v) {
        if(Internet.isNetworkAvailable(this) == true) {
            String korisnickoIme = email.getText().toString().trim();
            String password = lozinka.getText().toString().trim();

            if(korisnickoIme.isEmpty() || password.isEmpty()){
                AlertDialog alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
                alertDialog.setTitle("Nisu popunjeni svi podaci!");
                alertDialog.setMessage("Molimo Vas unesite korisničko ime i lozinku!");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
            else {
                Korisnik korisnik = new Korisnik(korisnickoIme, password);
                wsDataLoader = new WsDataLoader();
                wsDataLoader.Prijava(korisnik, this);
            }
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(LogInActivity.this).create();
            alertDialog.setTitle("Pogreška u internet vezi");
            alertDialog.setMessage("Molimo Vas omogućite internetsku vezu kako biste se prijavili u aplikaciju.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    @OnClick(R.id.zaboravljenaLozinka)
    public void ZaboravljenaLozinkaKlik(View v){
        startActivity(new Intent(LogInActivity.this, ZaboravljenaLozinkaActivity.class));
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if (status.equals("OK")){
            setSharedPrefs(email.getText().toString(), lozinka.getText().toString());
            if(checkLoginPersistence() == true){
                try{
                    List lista =Arrays.asList(data);
                    List korisnik = (List) lista.get(0);
                   Korisnik korisnik1=(Korisnik)(korisnik.get(0));
                    setStaticLoginUserObject(korisnik1);
                }catch (Exception ex){
                    ex.getMessage();
                }
            //    setStaticLoginUserObject((ArrayList<Korisnik>) data);
                Intent i = new Intent(this, GlavniActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivityForResult(i, 1);
                finish();
            }
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Pogrešni podaci za prijavu!");
            alertDialog.setMessage(message);
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void setSharedPrefs(String korisnickoIme, String lozinka){
        long ts = System.currentTimeMillis()/1000;
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("korisnickoIme", korisnickoIme);
        editor.putString("lozinka", lozinka);
        editor.putBoolean("prijavljen", true);
        editor.apply();
    }

    private Boolean checkLoginPersistence(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prijavljen = prefs.getBoolean("prijavljen",false);
        return prijavljen;
    }

    private void setStaticLoginUserObject(Korisnik k){
       try{
           Korisnik.setPrijavljeniKorisnik(k);
       }catch (Exception ex){
           ex.printStackTrace();
       }

    }
}
