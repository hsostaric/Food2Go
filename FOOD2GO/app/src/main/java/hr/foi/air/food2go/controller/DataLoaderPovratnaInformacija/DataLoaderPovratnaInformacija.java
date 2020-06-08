package hr.foi.air.food2go.controller.DataLoaderPovratnaInformacija;

import android.content.Context;
import android.widget.Toast;

import hr.foi.air.food2go.controller.dataLoaders.DataLoadedListener;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;

public class DataLoaderPovratnaInformacija implements DataLoadedListener {
    private WsDataLoader wsDataLoader;

    Context context;

    public DataLoaderPovratnaInformacija(Context context){
        this.context = context;
    }

    public void DodajPovratnuInfo(int racunId, String komentar, float ocjena){
        wsDataLoader = new WsDataLoader();
        wsDataLoader.DodajPovratnu(racunId, komentar, ocjena, this);
    }

    @Override
    public void onDataLoaded(String message, String status, Object data) {
        if(status.equals("OK")){
            Toast.makeText(context,"Uspješno.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Neuspješno.", Toast.LENGTH_SHORT).show();
        }
    }
}
