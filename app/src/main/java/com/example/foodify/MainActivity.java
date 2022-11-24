package com.example.foodify;

import static android.content.ContentValues.TAG;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    ImageView btSignInGoogle;
    Button btnRegister;
    Button btnLogin;

    Button btnTestScan;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextInputEditText email;
    TextInputEditText password;
    ProgressDialog progressDialog;

    TextView forgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSignInGoogle = findViewById(R.id.google_btn);
        btnRegister = findViewById(R.id.register_btn);
        btnTestScan = findViewById(R.id.testScan);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnLogin = findViewById(R.id.loginBtn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        forgotPassword = (TextView) findViewById(R.id.forgetPass);

        progressDialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();


        btSignInGoogle.setOnClickListener(view -> {
            loginGoogle();
        });

        btnRegister.setOnClickListener(view ->{
            startActivity(new Intent(MainActivity.this, Register.class));
        });

        btnLogin.setOnClickListener(view -> {
            login();
        });

        forgotPassword.setOnClickListener(view ->{
            sendPasswordReset();
        });

        btnTestScan.setOnClickListener(view ->{
            scan();
            sendUserToBarCodeInfo();
        });
    }

    private void sendUserToBarCodeInfo(){
        Intent intent = new Intent(MainActivity.this, JsonResult.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
            Toast.makeText(MainActivity.this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).show();
        } else{
            Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
        }
    });


    private void sendPasswordReset(){
        Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // google login
    private void loginGoogle(){
        Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // regular login
    private void login() {
        String emailStr = email.getText().toString();
        String passwordStr = password.getText().toString();

        if (!TextUtils.isEmpty(emailStr) && !Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            email.setError("Enter Correct email");
        } else if (passwordStr.isEmpty() || password.length() < 6) {
            password.setError("Incorrect Password");
        } else {
            progressDialog.setMessage("Logging in...");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    sendUserToNextActivity();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT);
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT);
                }
            });
        }
    }

    // sends user to profile
    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this, HomeScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
