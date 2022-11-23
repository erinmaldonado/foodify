package com.example.foodify;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

public class HomeScreen extends AppCompatActivity {

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    TextView name,email;
    Button signOutBtn;
    Button scanBtn;

    // Signs in user via google login
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        signOutBtn = findViewById(R.id.signOut);
        scanBtn = findViewById(R.id.scan);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);


        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if(acct!=null){
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            name.setText(personName);
            email.setText(personEmail);
        }

        signOutBtn.setOnClickListener(view -> signOut());
        scanBtn.setOnClickListener(view-> scan());
    }
    void signOut(){
        gsc.signOut().addOnCompleteListener(task -> startActivity(new Intent(HomeScreen.this,MainActivity.class)));
    }

    void scan(){
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a barcode");
        options.setCameraId(0);
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(BarcodeScannerActivity.class);
        barLauncher.launch(options);
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
       if(result.getContents() != null){
           AlertDialog.Builder builder = new AlertDialog.Builder(HomeScreen.this);
           builder.setTitle("Result");
           builder.setMessage(result.getContents());
           builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
               }
           }).show();
       }
    });


}