package hr.foi.air.food2go.fragmenti.odjava;

import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;

import hr.foi.air.core.Korisnik;
import hr.foi.air.food2go.GlavniActivity;
import hr.foi.air.food2go.R;
import hr.foi.air.food2go.controller.Internet;
import hr.foi.air.food2go.controller.LogInActivity;
import hr.foi.air.food2go.controller.dataLoaders.WsDataLoader;

public class OdjavaFragment extends Fragment {
    private boolean prijavljen = true;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_odjava, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (Internet.isNetworkAvailable(getContext()) == true) {
            deleteSharedPrefs();
            if (checkLoginPersistence() == false) {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
                alertDialog.setTitle("Uspješno ste se odjavili");
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Korisnik.setPrijavljeniKorisnik(null);
                                Intent i = new Intent(getActivity().getApplicationContext(), LogInActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivityForResult(i, 1);

                            }
                        });
                alertDialog.show();
            } else {
                Toast.makeText(getContext(), "Neuspješna odjava", Toast.LENGTH_LONG).show();
            }
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Pogreška u internet vezi");
            alertDialog.setMessage("Molimo Vas omogućite internetsku vezu kako biste se odjavili iz aplikacije.");
            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
        }
    }

    private void deleteSharedPrefs() {//potrebno kasnije za odjavu
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("prijavljen", false);
        editor.remove("korisnickoIme");
        editor.remove("lozinka");
        editor.apply();
    }

    private Boolean checkLoginPersistence() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        prijavljen = prefs.getBoolean("prijavljen", true);
        return prijavljen;
    }
}
