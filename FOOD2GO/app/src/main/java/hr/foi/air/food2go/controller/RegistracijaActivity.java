package hr.foi.air.food2go.controller;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.foi.air.core.Korisnik;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;

public class RegistracijaActivity extends AppCompatActivity implements DataLoadedListener {

    private EditText ime, prezime, korisnickoIme, lozinka, oib, email, adresa, brojMobitela;

    private WsDataLoader wsDataLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registracija);
        ButterKnife.bind(this);

        ime = findViewById(R.id.uiInputIme);
        prezime = findViewById(R.id.uiInputPrezime);
        korisnickoIme = findViewById(R.id.uiKorisnickoIme);
        lozinka = findViewById(R.id.uiInputLozinka);
        oib = findViewById(R.id.uiInputOib);
        email = findViewById(R.id.uiInputEmail);
        adresa = findViewById(R.id.uiInputAdresa);
        brojMobitela = findViewById(R.id.uiInputBrojMobitela);
    }

    @OnClick(R.id.registriraj)
    public void RegistracijaKlik(View v){
        if(Internet.isNetworkAvailable(this) == true) {
            String mIme = ime.getText().toString().trim();
            String mPrezime = prezime.getText().toString().trim();
            String mKorIme = korisnickoIme.getText().toString().trim();
            String mLozinka = lozinka.getText().toString().trim();
            String mOib = oib.getText().toString().trim();
            String mEmail = email.getText().toString().trim();
            String mAdresa = adresa.getText().toString().trim();
            String mBrojMobitela = brojMobitela.getText().toString().trim();
            String mAktivacijskiKod = generirajAktivacijskiKod(10);

            if (mIme.isEmpty() || mPrezime.isEmpty() || mKorIme.isEmpty() || mLozinka.isEmpty() ||
                    mOib.isEmpty() || mEmail.isEmpty() || mAdresa.isEmpty() || mBrojMobitela.isEmpty()) {

                AlertDialog alertDialog = new AlertDialog.Builder(RegistracijaActivity.this).create();
                alertDialog.setTitle("Nisu popunjeni svi podaci!");
                alertDialog.setMessage("Molimo Vas unesite sve podatke!");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            } else {
                if (!validate(mEmail)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistracijaActivity.this).create();
                    alertDialog.setTitle("E-mail nije u ispravnom obliku!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else if (!validate_letters(mIme) || !validate_letters(mPrezime)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistracijaActivity.this).create();
                    alertDialog.setTitle("Ime i prezime smiju sadržavati samo slova!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else if (!validate_numbers_oib(mOib)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistracijaActivity.this).create();
                    alertDialog.setTitle("OIB mora sadržavati samo 11 brojeva!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else if (!validate_numbers_mobile(mBrojMobitela)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistracijaActivity.this).create();
                    alertDialog.setTitle("Neispravan broj mobitela!");
                    alertDialog.setMessage("Broj mobitela mora početi 00385 i mora sadržavati još 9 brojeva.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else if (!validate_numbers_lozinka(mLozinka)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RegistracijaActivity.this).create();
                    alertDialog.setTitle("Neispravna lozinka!");
                    alertDialog.setMessage("Lozinka mora sadržavati minimalno 6 znakova, jedno veliko slovo, jedno malo slovo, jednu brojku i jedan specijalni znak");
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Korisnik korisnik = new Korisnik(mIme, mPrezime, mKorIme,
                            mLozinka, mOib, mEmail, mAdresa,
                            mBrojMobitela, mAktivacijskiKod);
                    wsDataLoader = new WsDataLoader();
                    wsDataLoader.Registracija(korisnik, this);
                }
            }
        }
        else {
            AlertDialog alertDialog = new AlertDialog.Builder(RegistracijaActivity.this).create();
            alertDialog.setTitle("Pogreška u internet vezi");
            alertDialog.setMessage("Molimo Vas omogućite internetsku vezu kako biste se registrirali.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    public static final Pattern email_check =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern letters_only_check =
            Pattern.compile("^[a-zA-Z]+$", Pattern.CASE_INSENSITIVE);


    public static final Pattern numbers_only_check_oib =
            Pattern.compile("^\\s*(?<NUM>[0-9]{10})", Pattern.CASE_INSENSITIVE);

    public static final Pattern numbers_only_check_mobile =
            Pattern.compile("^00385[0-9]{9,}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern numbers_only_check_lozinka =
            Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{6,}$", Pattern.CASE_INSENSITIVE);

    public static boolean validate(String emailStr) {
        Matcher matcher = email_check.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validate_letters(String letters) {
        Matcher matcher = letters_only_check.matcher(letters);
        return matcher.find();
    }

    public static boolean validate_numbers_oib(String letters) {
        Matcher matcher = numbers_only_check_oib.matcher(letters);
        return matcher.find();
    }

    public static boolean validate_numbers_mobile(String letters) {
        Matcher matcher = numbers_only_check_mobile.matcher(letters);
        return matcher.find();
    }

    public static boolean validate_numbers_lozinka(String letters) {
        Matcher matcher = numbers_only_check_lozinka.matcher(letters);
        return matcher.find();
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if (status.equals("OK")){
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Uspješna registracija!");
            alertDialog.setMessage("E-mail s aktivacijskim kodom Vam je poslan.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(RegistracijaActivity.this, AktivacijskiKodActivity.class));
                        }
                    });
            alertDialog.show();
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Korisnik postoji!");
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

    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();

    public static String generirajAktivacijskiKod(int len) {
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }
}
