package com.qwerty.qroad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.security.Policy;

public class CameraActivity extends AppCompatActivity {

    private SurfaceView surfaceView;
    private SurfaceHolder holder;
    private BarcodeDetector detector;
    private CameraSource source;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        surfaceView = findViewById(R.id.surfaceView);
        detector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        source = new CameraSource
                .Builder(this, detector)
                .setAutoFocusEnabled(true)
                .build();

        holder = surfaceView.getHolder();
        holder.addCallback(new CameraView());


        //Еще работаю над этой фигней с qr кодами (Библиотека в gradle)
        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @SuppressLint("MissingPermission")
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> barcode = detections.getDetectedItems();

                if (barcode.size() != 0) {
                    Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                    vibrator.vibrate(1000);
                    System.out.println(barcode.valueAt(0).displayValue  );
                }
            }
        });

    }

    private class CameraView implements SurfaceHolder.Callback {
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
