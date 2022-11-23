package com.example.foodify;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;


public class ForgotPassword extends Activity {
    ProgressDialog progressDialog;
    Button forgotPassword;
    TextView email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email = findViewById(R.id.editTextTextEmailAddress);
        forgotPassword = (Button) findViewById(R.id.forgotPassword);

        progressDialog = new ProgressDialog(this);

        forgotPassword.setOnClickListener(view ->{
            sendPassword();
        });
    }

    private void sendPassword(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email.getText().toString();

        if(!TextUtils.isEmpty(emailAddress) && !Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
            email.setError("Enter a valid email");
        } else {
            progressDialog.setMessage("Sending email...");
            progressDialog.setTitle("Reset Password");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener(task -> {
                            Intent intent = new Intent(ForgotPassword.this, MainActivity.class);
                            startActivity(intent);
                    });
        }

    }
}
