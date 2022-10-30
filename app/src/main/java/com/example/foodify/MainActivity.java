package com.example.foodify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private EditText inputName , inputEmail, inputPass, inputPassRepeat ;
    private Button btnRegister;
    private TextView txtWarnName,txtWarmEmail,txtWarmPass,txtWarnPassRepeat;
    private ConstraintLayout parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews(){
        Log.d(TAG,"initCiews: Started");
    }
}