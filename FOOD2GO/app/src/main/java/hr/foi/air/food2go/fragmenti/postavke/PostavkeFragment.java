package hr.foi.air.food2go.fragmenti.postavke;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import hr.foi.air.core.Korisnik;
import hr.foi.air.food2go.GlavniActivity;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.Internet;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;

public class PostavkeFragment extends Fragment implements DataLoadedListener {
    @BindView(R.id.prijavljeni_korisnik_ime)
    public EditText uiKorisnikIme;
    @BindView(R.id.prijavljeni_korisnik_prezime)
    public EditText uiKorisnikPrezime;
    @BindView(R.id.prijavljeni_korisnik_adresa)
    public EditText uiKorisnikAdresa;
    @BindView(R.id.prijavljeni_korisnik_username)
    public EditText uiKorisnikUsername;
    @BindView(R.id.prijavljeni_korisnik_email)
    public EditText uiKorisnikEmail;
    @BindView(R.id.prijavljeni_korisnik_lozinka)
    public EditText uiKorisnikLozinka;
    @BindView(R.id.prijavljeni_korisnik_ponovi_lozinku)
    public EditText uiKorisnikPonoviLozinku;
    @BindView(R.id.prijavljeni_korisnik_mobitel)
    public EditText uiKorisnikMobitel;

    @BindView(R.id.uredi_korisnicke_postavke)
    public Button urediKorisnika;

    @BindView(R.id.modulSwitch)
    public Switch modulSwitch;



    private Unbinder unbinder;

    private WsDataLoader dataLoader;
    private Korisnik noviKorisnik = null;

    public static Boolean Modul=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_postavke, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (Internet.isNetworkAvailable(getContext()) == true) {
            bindData();
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Pogreška u internet vezi");
            alertDialog.setMessage("Molimo Vas omogućite internetsku vezu kako biste koristili aplikaciju.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
        PromijeniStanjeSwitch();
    }
    public void PromijeniStanjeSwitch(){
        if(Modul){
            modulSwitch.setChecked(Modul);
        }
        else {
            modulSwitch.setChecked(false);
        }
    }

    public void bindData() {
        uiKorisnikIme.setText(Korisnik.getPrijavljeniKorisnik().getIme());
        uiKorisnikPrezime.setText(Korisnik.getPrijavljeniKorisnik().getPrezime());
        uiKorisnikEmail.setText(Korisnik.getPrijavljeniKorisnik().getEmail());
        uiKorisnikUsername.setText(Korisnik.getPrijavljeniKorisnik().getUsername());
        uiKorisnikLozinka.setText(Korisnik.getPrijavljeniKorisnik().getLozinka());
        uiKorisnikPonoviLozinku.setText(Korisnik.getPrijavljeniKorisnik().getLozinka());
        uiKorisnikAdresa.setText(Korisnik.getPrijavljeniKorisnik().getAdresa());
        uiKorisnikMobitel.setText(Korisnik.getPrijavljeniKorisnik().getMobitel());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private boolean ispravnostPodataka() {
        return (PostavkeValidacije.PraznoPolje(uiKorisnikIme.getText().toString().trim()) == true && PostavkeValidacije.PraznoPolje(uiKorisnikPrezime.getText().toString().trim()) == true
                && PostavkeValidacije.ProvijeriEmail(uiKorisnikEmail.getText().toString().trim()) == true && PostavkeValidacije.PraznoPolje(uiKorisnikUsername.getText().toString().trim()) == true
                && PostavkeValidacije.PraznoPolje(uiKorisnikLozinka.getText().toString().trim()) == true && PostavkeValidacije.PraznoPolje(uiKorisnikPonoviLozinku.getText().toString().trim()) == true
                && PostavkeValidacije.IspravnostLozinki(uiKorisnikLozinka.getText().toString().trim(), uiKorisnikPonoviLozinku.getText().toString().trim()) == true) ? true : false;
    }

    private void instanceNewUserData() {
        noviKorisnik = new Korisnik();
        noviKorisnik.setId(Korisnik.getPrijavljeniKorisnik().getId());
        noviKorisnik.setIme(uiKorisnikIme.getText().toString().trim());
        noviKorisnik.setPrezime(uiKorisnikPrezime.getText().toString().trim());
        noviKorisnik.setUsername(uiKorisnikUsername.getText().toString());
        noviKorisnik.setEmail(uiKorisnikEmail.getText().toString());
        noviKorisnik.setAdresa(uiKorisnikAdresa.getText().toString());
        noviKorisnik.setMobitel(uiKorisnikMobitel.getText().toString());
        noviKorisnik.setLozinka(uiKorisnikLozinka.getText().toString());
        noviKorisnik.setBrojBodova(Korisnik.getPrijavljeniKorisnik().getBrojBodova());
        noviKorisnik.setBrojPokusaja(0);
        noviKorisnik.setOib(Korisnik.getPrijavljeniKorisnik().getOib());
        noviKorisnik.setStatus(Korisnik.getPrijavljeniKorisnik().getStatus());

    }

    @OnClick(R.id.uredi_korisnicke_postavke)
    void AzurirajPodatke() {

        if (Internet.isNetworkAvailable(getContext()) == false) {
            Toast.makeText(getContext(), "Nema interneta", Toast.LENGTH_LONG).show();
        } else {
            if (!ispravnostPodataka()) {
                Toast.makeText(getContext(), "Podaci nisu ispravni !", Toast.LENGTH_SHORT).show();

            } else {

                    instanceNewUserData();
                    dataLoader = new WsDataLoader();
                    dataLoader.AzurirajKorisnika(noviKorisnik, this);

                }
            }

    }
    @OnCheckedChanged(R.id.modulSwitch)
    void ProvjeriStanjeSwitcha(){
        Modul= modulSwitch.isChecked();
        if(Modul==true){
            Toast.makeText(getContext(),"True",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getContext(),"False",Toast.LENGTH_LONG).show();
        }
        
        PromijeniStanjeSwitch();
    }



    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if (status.equals("OK")) {
            Toast.makeText(getContext(), "Podaci su uspješno ažurirani", Toast.LENGTH_LONG).show();
            Korisnik.setPrijavljeniKorisnik(noviKorisnik);
        } else {
            Toast.makeText(getContext(), "Podaci nisu ažurirani", Toast.LENGTH_LONG).show();
        }
    }
}