package com.example.pinloyalitypointsupdate.codeLoyalityPointsFragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.pinloyalitypointsupdate.R;

import java.io.Console;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import hr.foi.air.core.Racun;
import hr.foi.air.core.modularFunctionInterface.ILoyalityPointsUpdate;

public class LoyalityPontsWithCodeFragment extends Fragment implements ILoyalityPointsUpdate {

    public Button confirmButton;
    View view;
    private Racun racun;
    private int korisnikID;
    private String passCode = "";
    private EditText lozinka;
    onCallBackRecived mCallback;

    @Override
    public void setData(int korisnikID, String code,onCallBackRecived listener) {
        this.korisnikID = korisnikID;
        this.passCode = code;
        this.mCallback=listener;
    }

    @Override
    public String getData() {
        return passCode;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pin_loyalty, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

            confirmButton = view.findViewById(R.id.pinConfirmLoyalityPoints);
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                   lozinka =  view.findViewById(R.id.pinLoyalityText);
                   passCode = lozinka.getText().toString();

                }catch (Exception ex){
                    Log.e("ErrorKlik",ex.getMessage());
                }

            if(passCode.isEmpty()){

              AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
              alertDialog.setTitle("Greška!");
              alertDialog.setMessage("Polje za PIN je prazno!");
              alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK",
                      new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.dismiss();
                          }
                      });
              alertDialog.show();
          }else {

                mCallback.Update();
          }
                }
            });

    }



}
