package com.qwerty.qroad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
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
    private List<String> availableNames;
    private LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        availableNames = Arrays.asList(getResources().getStringArray(R.array.available_names));
        errorLayout = findViewById(R.id.errorLayout);

        surfaceView = findViewById(R.id.surfaceView);
        detector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        source = new CameraSource
                .Builder(this, detector)
                .setAutoFocusEnabled(true)
                .build();

        holder = surfaceView.getHolder();
        holder.addCallback(new CameraCallback());

        detector.setProcessor(new QRDetector());

    }


    private class QRDetector implements Detector.Processor<Barcode> {

        @Override
        public void release() {

        }

        @Override
        public void receiveDetections(Detector.Detections<Barcode> detections) {
            SparseArray<Barcode> barcode = detections.getDetectedItems();

            if (barcode.size() != 0) {

                if (availableNames.contains(barcode.valueAt(0).displayValue)) {
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(250);

                    String qrString = barcode.valueAt(0).displayValue;
                    int point = Integer.parseInt(qrString.split(" ")[1]);
                    Intent intent = new Intent(CameraActivity.this, MapActivity.class);
                    intent.putExtra("point", point);
                    startActivity(intent);
                } else {
                    //TODO: Поправить вывод ошибки
                    errorLayout.setVisibility(View.VISIBLE);
                }
            }
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
