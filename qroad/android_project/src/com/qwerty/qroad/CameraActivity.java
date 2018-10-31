package com.qwerty.qroad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.security.Policy;
import java.util.Arrays;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private BarcodeDetector detector;
    private CameraSource source;
    private List<String> availablePrefix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        skipCamera();
//
//        availablePrefix = Arrays.asList(getResources().getStringArray(R.array.available_prefix));
//        surfaceView = findViewById(R.id.surfaceView);
//
//        detector = new BarcodeDetector.Builder(this)
//                .setBarcodeFormats(Barcode.QR_CODE)
//                .build();
//
//        source = new CameraSource
//                .Builder(this, detector)
//                .setAutoFocusEnabled(true)
//                .build();
//
//        holder = surfaceView.getHolder();
//        holder.addCallback(new CameraCallback());
//
//        detector.setProcessor(new QRDetector());

    }

    //DEBUG
    private void skipCamera() {
        Intent intent = new Intent(this, MapActivity.class);
        intent.putExtra("title", "ermitaj");
        startActivity(intent);
        finish();
    }

    private class QRDetector implements Detector.Processor<Barcode> {
        private boolean isChecked = false;

        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            SparseArray<Barcode> barcode = detections.getDetectedItems();

            if (barcode.size() != 0) {

                Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                String codeValue = barcode.valueAt(0).displayValue;
                if (availablePrefix.contains(codeValue.split(" ")[0]) && !isChecked) {

                    isChecked = true;
                    vibrator.vibrate(250);
                    String qrString = barcode.valueAt(0).displayValue;
                    Intent intent = new Intent(CameraActivity.this, MapActivity.class);
                    int point = 0;
                    try {
                        Integer.parseInt(qrString.split(" ")[1]);
                    } catch (Exception ex) {

                    }
                    intent.putExtra("title", codeValue.split(" ")[0]);
                    intent.putExtra("point", point);
                    startActivity(intent);
                    finish();
                } else {
                    vibrateEror(vibrator);
                }
            }
        }
    }

    private void vibrateEror(Vibrator vibrator) {
        vibrator.vibrate(150);
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
        }
        vibrator.vibrate(200);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private class CameraCallback implements SurfaceHolder.Callback {
        @SuppressLint("MissingPermission")
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                source.start(holder);
            } catch (IOException e) {
                Toast.makeText(CameraActivity.this, "Camera error", Toast.LENGTH_LONG).show();
                System.exit(0);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            source.stop();
        }
    }

}
