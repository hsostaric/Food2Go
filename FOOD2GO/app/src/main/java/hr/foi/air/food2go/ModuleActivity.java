package hr.foi.air.food2go;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.pinloyalitypointsupdate.codeLoyalityPointsFragment.LoyalityPontsWithCodeFragment;
import com.example.qrcodeloyalitypointsupdate.Fragmenti.QRCodeFragmenti.QRCodeFragment;

import hr.foi.air.core.Korisnik;
import hr.foi.air.core.modularFunctionInterface.ILoyalityPointsUpdate;
import hr.foi.air.food2go.controller.LogInActivity;
import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;

public class ModuleActivity extends FragmentActivity implements ILoyalityPointsUpdate.onCallBackRecived, DataLoadedListener {
    ILoyalityPointsUpdate iLoyalityPointsUpdate ;
    private boolean mod;
    private WsDataLoader wsDataLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalitypoints);
        try {
            Bundle extras = getIntent().getExtras();
            mod=extras.getBoolean("NacinPrikaza");
            iLoyalityPointsUpdate=odrediFragment(mod);
            iLoyalityPointsUpdate.setData(Korisnik.getPrijavljeniKorisnik().getId(), "",this);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, (androidx.fragment.app.Fragment) iLoyalityPointsUpdate)
                    .addToBackStack(null)
                    .commit();
        }catch (Exception ex){
            Log.e("ErrorMod",ex.getMessage());
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ModuleActivity.this, GlavniActivity.class);
        startActivity(intent);
        this.finish();
    }
    public ILoyalityPointsUpdate odrediFragment(boolean mod){
         return (mod==true)?new QRCodeFragment(): new LoyalityPontsWithCodeFragment();

    }
    @Override
    public void Update() {
        String kod =iLoyalityPointsUpdate.getData();
        //Toast.makeText(getApplicationContext(),kod,Toast.LENGTH_SHORT).show();
        wsDataLoader = new WsDataLoader();
        wsDataLoader.DohvatiRacun(Korisnik.getPrijavljeniKorisnik().getId(),kod,this);
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if(status.equals("OK")){
            Toast.makeText(getApplicationContext(),"Bodovi su uspješno ažurirani !",Toast.LENGTH_SHORT).show();
        }
        else {
          Toast.makeText(getApplicationContext(),"Unijeli te krive podatke ili je pin već iskorišten",Toast.LENGTH_SHORT).show();
        }
    }
}



