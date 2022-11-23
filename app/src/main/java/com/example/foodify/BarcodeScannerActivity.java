package com.example.foodify;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;

import com.google.mlkit.vision.common.InputImage;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.util.List;

public class BarcodeScannerActivity extends CaptureActivity {


}
