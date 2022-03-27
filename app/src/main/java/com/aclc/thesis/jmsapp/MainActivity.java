package com.aclc.thesis.jmsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editUN, editPW;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
        setListeners();
    }

    private void setListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editUN.getText().toString().equals("") || editPW.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Baho tae",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setViews() {
        editUN = findViewById(R.id.editUN);
        editPW = findViewById(R.id.editPW);
        btnLogin = findViewById(R.id.btnLogin);
    }
}