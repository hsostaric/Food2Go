package hr.foi.air.food2go.fragmenti.odabir_potkategorije;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

import hr.foi.air.core.Artikl;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.fragmenti.odabir_kategorije.OdabirKategorije;


public class OdabirPotkategorijeFragment extends Fragment implements View.OnClickListener {

    View view;
    public static ArrayList<Artikl> listaArtikalaUKosarici = new ArrayList<>();
    private Artikl odabraniArtikl;
    private int brojac;

    private TextView artikl;
    private ImageView artiklSlika;
    private TextView artiklOpis;
    private TextView artiklCijena;

    private Button gumbPlus;
    private TextView artiklKolicina;
    private Button gumbMinus;

    private Button dodajUKosaricu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_odabir_potkategorije, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        odabraniArtikl = OdabirKategorije.Artikl;

        artikl = view.findViewById(R.id.artikl);
        artiklSlika = view.findViewById(R.id.artikl_slika);
        artiklOpis = view.findViewById(R.id.artikl_opis);
        artiklCijena = view.findViewById(R.id.artikl_cijena2);
        gumbPlus = view.findViewById(R.id.gumb_plus);
        artiklKolicina = view.findViewById(R.id.artikl_kolicina);
        gumbMinus = view.findViewById(R.id.gumb_minus);
        dodajUKosaricu = view.findViewById(R.id.gumb_dodaj_u_kosaricu);

        gumbPlus.setOnClickListener(this);
        gumbMinus.setOnClickListener(this);
        dodajUKosaricu.setOnClickListener(this);

        ucitajPodatke();
    }

    private void ucitajPodatke(){
        artikl.setText(odabraniArtikl.getNaziv());
        //slika
        Glide.with(this)
                .asBitmap()
                .load(odabraniArtikl.getUrlSlike())
                .into(artiklSlika);

        artiklOpis.setText("Opis: " + odabraniArtikl.getOpis());
        DecimalFormat df = new DecimalFormat("0.00");
        artiklCijena.setText("Cijena: " + df.format(odabraniArtikl.getCijena()).replace('.', ',') + " kn");
        brojac = 1;
        artiklKolicina.setText(Integer.toString(brojac));
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.gumb_plus){
            if(brojac < 100){
                brojac++;
                artiklKolicina.setText(Integer.toString(brojac));
            }
        }
        else if(v.getId()==R.id.gumb_minus){
            if(brojac > 1){
                brojac--;
                artiklKolicina.setText(Integer.toString(brojac));
            }
        }
        else if(v.getId()==R.id.gumb_dodaj_u_kosaricu){
            int kolicinaArtikla = brojac;
            boolean postoji = false;
            if(OdabirPotkategorijeFragment.listaArtikalaUKosarici.size()>0){
                for(Artikl a : OdabirPotkategorijeFragment.listaArtikalaUKosarici){
                    if(a.getId()==odabraniArtikl.getId()){
                        kolicinaArtikla += a.getKolicina();
                        a.setKolicina(kolicinaArtikla);
                        postoji=true;
                    }
                }
            }
            if(!postoji){
                odabraniArtikl.setKolicina(kolicinaArtikla);
                OdabirPotkategorijeFragment.listaArtikalaUKosarici.add(odabraniArtikl);
            }
            Toast.makeText(getActivity(), "Trenutna količina artikla u košarici: " + Integer.toString(kolicinaArtikla), Toast.LENGTH_SHORT).show();
        }
    }
}
