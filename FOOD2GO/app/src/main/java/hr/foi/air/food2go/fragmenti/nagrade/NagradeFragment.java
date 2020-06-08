package hr.foi.air.food2go.fragmenti.nagrade;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.core.Korisnik;
import hr.foi.air.food2go.GlavniActivity;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.Internet;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;
import hr.foi.air.core.Nagrada;
import hr.foi.air.food2go.recyclerview.NagradeRecyclerAdapter;

public class NagradeFragment extends Fragment implements DataLoadedListener {

    View view;
    private WsDataLoader wsDataLoader;
    private ArrayList<Nagrada> nagrade;
    private Korisnik korisnik;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = container;
        return inflater.inflate(R.layout.fragment_nagrade, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if (Internet.isNetworkAvailable(getContext()) == true) {
            nagrade = new ArrayList<>();
            wsDataLoader = new WsDataLoader();
            korisnik = new Korisnik();
            korisnik.setUsername(getSharedPref());
            wsDataLoader.DohvatiTrenutneBodove(korisnik, this);
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

    private String getSharedPref(){
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        return pref.getString("korisnickoIme", "userNotFound");
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = view.findViewById(R.id.nagrade_recyclerview);
        NagradeRecyclerAdapter adapter = new NagradeRecyclerAdapter(getActivity(), nagrade, position);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void nadiNajvecuNagradu(){
        int brojBodova = korisnik.getBrojBodova();
        position = -1;
        int brojac = 0;
        for(Nagrada n : nagrade){
            if(brojBodova >= n.getBrojBodova())
                position = brojac;
            brojac++;
        }
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if(status.equals("OK")){
            if(message.equals("Bodovi su dohvaceni!")){
                List<Korisnik> kor = (List<Korisnik>) data;
                for(Korisnik k : kor){
                    korisnik = k;
                }
                wsDataLoader.DohvatiSveNagrade(this);
            }
            else if(message.equals("Nagrade su dohvacene")) {
                List<Nagrada> nag = (List<Nagrada>) data;
                for (Nagrada n : nag) {
                    nagrade.add(n);
                }
                nadiNajvecuNagradu();
                initRecyclerView();
            }
        }
        else{
            Toast.makeText(getActivity(), "Postoji problem.", Toast.LENGTH_SHORT).show();
        }
    }
}