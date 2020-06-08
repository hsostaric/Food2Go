package hr.foi.air.food2go.fragmenti.moje_narudzbe;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air.core.StavkeRacuna;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.DataLoaderPovratnaInformacija.DataLoaderPovratnaInformacija;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;
import hr.foi.air.food2go.recyclerview.DetaljiNarudzbeAdapter;

public class DetaljiNarudzbeFragment extends Fragment implements DataLoadedListener, View.OnClickListener {

    View v;
    private WsDataLoader wsDataLoader;
    public ArrayList<StavkeRacuna> artikli = new ArrayList<StavkeRacuna>();
    private String korisnickoIme;
    private int racunid;
    private String ukupnoCijena;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = container;
        return inflater.inflate(R.layout.fragment_detalji_narudzbe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button b = (Button) v.findViewById(R.id.uiActionOcijeni);
        b.setOnClickListener(this);

        wsDataLoader = new WsDataLoader();
        wsDataLoader.IspisiArtikleRacuna(getRacunID(), this);
    }

    private int getRacunID(){
        racunid = MojeNarudzbeFragment.odabraniRacun.getID();
        return racunid;
    }

    private void DohvatiIzgled() {
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.detaljinarudzbe_recyclerview);
        DetaljiNarudzbeAdapter adapter = new DetaljiNarudzbeAdapter(getActivity(), artikli, racunid);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public String izracunajUkupnuCijenu(){
        int ukupno = 0;
        for (StavkeRacuna stavka: artikli) {
            ukupno += Integer.parseInt(stavka.getKolicina()) * Integer.parseInt(stavka.getArtiikl_Temporalno_Cijena());
        }
        ukupnoCijena = String.valueOf(ukupno);

        return ukupnoCijena;
    }

    public void setText(String text) {
        TextView t = (TextView) v.findViewById(R.id.uiukupno);  //UPDATE
        t.setText(text);
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if(status.equals("OK")){
            List<StavkeRacuna> art = (List<StavkeRacuna>) data;
            for (StavkeRacuna a : art) {
                artikli.add(a);
                Log.i("tag", "artikli: " + a.getNaziv());
            }
            DohvatiIzgled();
            setText(izracunajUkupnuCijenu() + ",00 kn");
        }
        else{
            Toast.makeText(getActivity(), "Postoji problem.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.uiActionOcijeni:
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View layout = null;
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                layout = inflater.inflate(R.layout.povratna_informacija, null);
                final RatingBar ratingBar = (RatingBar) layout.findViewById(R.id.ratingBar);
                ratingBar.setStepSize(1);
                final EditText userInput = (EditText) layout.findViewById(R.id.uiKomentar);
                builder.setTitle("Povratna informacija");
                builder.setPositiveButton("Ocijeni", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Float ocjena = ratingBar.getRating();
                        Editable komentar = userInput.getText();
                        String text = komentar.toString();

                        if(!text.isEmpty() && ocjena != 0) {
                            DataLoaderPovratnaInformacija data = new DataLoaderPovratnaInformacija(getContext());
                            data.DodajPovratnuInfo(racunid, text, ocjena);
                        }else {
                            Toast.makeText(getContext(), "Neuspješno.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Zatvori", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setCancelable(false);
                builder.setView(layout);
                builder.show();
                break;
        }
    }
}
