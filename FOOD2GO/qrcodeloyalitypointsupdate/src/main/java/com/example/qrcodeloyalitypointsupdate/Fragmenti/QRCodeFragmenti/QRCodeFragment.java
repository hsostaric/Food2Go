package com.example.qrcodeloyalitypointsupdate.Fragmenti.QRCodeFragmenti;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import com.example.qrcodeloyalitypointsupdate.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;

import hr.foi.air.core.Racun;
import hr.foi.air.core.modularFunctionInterface.ILoyalityPointsUpdate;

public class QRCodeFragment extends Fragment  implements ILoyalityPointsUpdate
{
    private  ILoyalityPointsUpdate iLoyalityPointsUpdate;
    private int KorisnikID;
    public ArrayList<Racun> racuni = new ArrayList<Racun>();
    private String QRkod;
    private String Poruka;
    onCallBackRecived mCallback;
    View view;
    Racun racun;
    TextView text;
    SurfaceView cameraView;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    SurfaceHolder surfaceHolder;
    Button  buttonBodoviQR;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = container;
        return inflater.inflate(R.layout.fragment_qrcode, container, false);
    }

    public void setFragmentCallback(ILoyalityPointsUpdate callback) {
        this.iLoyalityPointsUpdate = callback;
    }
    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        text=(TextView) view.findViewById(R.id.barCodeResult);
        buttonBodoviQR=(Button) view.findViewById(R.id.buttonIskoristiQRkod);
        cameraView= (SurfaceView) view.findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        surfaceHolder=cameraView.getHolder();
        barcodeDetector= new BarcodeDetector.Builder(getContext()).setBarcodeFormats(Barcode.QR_CODE| Barcode.DATA_MATRIX).build();
        if(!barcodeDetector.isOperational()){

        }
        else {

        }
        cameraSource = new CameraSource.Builder(getContext(),barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedFps(24)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(640,480)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED){
                        cameraSource.start(cameraView.getHolder());
                       // Toast.makeText(getContext(),"Kamera", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        requestPermissions(new String[]{Manifest.permission.CAMERA},200);
                        Toast.makeText(getContext(),"Molim vas otvorite ponovno ovaj prozor!", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size()!=0){
                    text.post(new Runnable() {
                        @Override
                        public void run() {
                            text.setText(barcodes.valueAt(0).displayValue);
                            String message= (String) text.getText();

                        }
                    });
                }
            }
        });

        buttonBodoviQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String poruka = (String) text.getText();
                QRkod=poruka;
                mCallback.Update();

            }
        });

    }


    @Override
    public void setData(int korisnikID, String code, onCallBackRecived mCallback) {
        this.KorisnikID=korisnikID;
        this.QRkod= code;
        this.mCallback=mCallback;
    }

    @Override
    public String getData() {
        return QRkod;
    }

}
