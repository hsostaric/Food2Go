package hr.foi.air.food2go.fragmenti.moje_narudzbe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import hr.foi.air.core.Racun;
import hr.foi.air.food2go.GlavniActivity;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.Internet;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;
import hr.foi.air.food2go.recyclerview.MojeNarudzbeAdapter;

public class MojeNarudzbeFragment extends Fragment implements DataLoadedListener, MojeNarudzbeAdapter.OnItemClickListener {

    private WsDataLoader wsDataLoader;
    public ArrayList<Racun> racuni = new ArrayList<Racun>();
    public static Racun odabraniRacun;
    private String korisnickoIme;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = container;
        return inflater.inflate(R.layout.fragment_moje_narudzbe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (Internet.isNetworkAvailable(getContext()) == true) {

            getSharedPrefs();
            racuni.clear();
            wsDataLoader = new WsDataLoader();
            wsDataLoader.IspisiRacune(getKorisnickoIme(), this);
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

    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        try {
            if(status.equals("OK")){
                List<Racun> rac = (List<Racun>) data;
                for (Racun r : rac) {
                    racuni.add(r);
                }
                DohvatiIzgled();
            }
            else{
                Toast.makeText(getActivity(), "Postoji problem.", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception ex){
            Log.e("Postoji problem.",ex.getMessage());
        }

    }

    private void getSharedPrefs(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        setKorisnickoIme(prefs.getString("korisnickoIme","test@test.test"));
    }

    private void DohvatiIzgled() {
        try {
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.moje_narudzbe_recycler);
            MojeNarudzbeAdapter adapter = new MojeNarudzbeAdapter(getActivity(),this, racuni);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }catch (Exception ex){
            Log.e("Postoji problem.",ex.getMessage());
        }

    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    @Override
    public void onItemClick(int position) {
        MojeNarudzbeFragment.odabraniRacun = racuni.get(position);
        DetaljiNarudzbeFragment detaljiNarudzbeFragment = new DetaljiNarudzbeFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, detaljiNarudzbeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}