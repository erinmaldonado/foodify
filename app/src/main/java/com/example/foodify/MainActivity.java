package com.example.foodify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    ImageView btSignInGoogle;
    Button btnRegister;
    Button btnLogin;

    FirebaseAuth mAuth;
    FirebaseUser mUser;
    TextInputEditText email;
    TextInputEditText password;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btSignInGoogle = (ImageView) findViewById(R.id.google_btn);
        btnRegister = (Button) findViewById(R.id.register_btn);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnLogin = (Button) findViewById(R.id.loginBtn);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);



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

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://foodify-3513e-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");
    }

    private void loginGoogle(){
        Intent intent = new Intent(MainActivity.this, GoogleSignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

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

            mAuth.signInWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT);
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, ""+task.getException(), Toast.LENGTH_SHORT);
                    }
                }
            });
        }
    }

    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
