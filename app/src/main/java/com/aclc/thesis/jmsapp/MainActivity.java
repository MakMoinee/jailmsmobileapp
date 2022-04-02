package com.aclc.thesis.jmsapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.parsers.UserParserImpl;
import com.aclc.thesis.jmsapp.parsers.UserParserIntf;
import com.aclc.thesis.jmsapp.service.UserService;
import com.aclc.thesis.jmsapp.service.UserServiceImpl;

public class MainActivity extends AppCompatActivity {

    private EditText editUN, editPW, editIP;
    private Button btnLogin, btnCreateAccount, btnProceed;
    private UserService userService = new UserServiceImpl();
    private UserParserIntf userParser = new UserParserImpl();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setViews();
        setListeners();
    }

    private void setListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editUN.getText().toString().equals("") || editPW.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else if (editUN.getText().toString().equals("config") &&
                        editPW.getText().toString().equals("config")) {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_login, null, false);
                    initDialogViews(mView);
                    initDialogListeners(mView);
                    mBuilder.setView(mView)
                            .setCancelable(true);
                    dialog = mBuilder.create();
                    dialog.show();
                } else {

//                    String data = userService.retrieveUser(MainActivity.this);
//                    Boolean isValid = userParser.parseUser(data);
//                    if (isValid) {
                        Intent intent = new Intent(MainActivity.this, MainFormActivity.class);
                        startActivity(intent);
//                    } else {
//                        Toast.makeText(MainActivity.this, "Wrong username or password.", Toast.LENGTH_SHORT).show();
//                    }
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFields();
                Intent intent = new Intent(MainActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initDialogListeners(View mView) {
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editIP.getText().equals("")) {
                    Toast.makeText(mView.getContext(), "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    Constants.config.setIP(editIP.getText().toString(), MainActivity.this);
                    Constants.setIp(MainActivity.this);
                    editIP.setText("");
                    clearFields();
                    dialog.dismiss();
                }
            }
        });
    }

    private void initDialogViews(View mView) {
        editIP = mView.findViewById(R.id.editIP);
        btnProceed = mView.findViewById(R.id.btnSet);
    }

    private void setViews() {
        editUN = findViewById(R.id.editUN);
        editPW = findViewById(R.id.editPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        Constants.setIp(MainActivity.this);
    }


    private void clearFields() {
        editUN.setText("");
        editPW.setText("");
    }
}