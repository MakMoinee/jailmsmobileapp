package com.aclc.thesis.jmsapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editUN, editPW, editConfirm, editFN, editMN, editLN;
    private Button btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_create_account);
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setViews();
        setListeners();
    }

    private void setListeners() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editUN.getText().toString().equals("") ||
                        editPW.getText().toString().equals("") ||
                        editConfirm.getText().toString().equals("") ||
                        editFN.getText().toString().equals("") ||
                        editMN.getText().toString().equals("") ||
                        editLN.getText().toString().equals("")) {
                    Toast.makeText(CreateAccountActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (editConfirm.getText().toString().equals(editPW.getText().toString())) {
                        Users users = new Users();
                        users.setUserPassword(editPW.getText().toString());
                        users.setUserName(editUN.getText().toString());
                        users.setUserType(2);

                        Visitor visitor = new Visitor();
                    } else {
                        Toast.makeText(CreateAccountActivity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void setViews() {
        editUN = findViewById(R.id.editUN);
        editPW = findViewById(R.id.editPW);
        editConfirm = findViewById(R.id.editConfirm);
        editFN = findViewById(R.id.editFN);
        editLN = findViewById(R.id.editLN);
        editMN = findViewById(R.id.editMN);
        btnCreate = findViewById(R.id.btnCreateAccount);

        Constants.setIp(CreateAccountActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
