package com.aclc.thesis.jmsapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.UserService;
import com.aclc.thesis.jmsapp.service.UserServiceImpl;
import com.android.volley.VolleyError;

public class MainActivity extends AppCompatActivity {

    private EditText editUN, editPW, editIP;
    private Button btnLogin, btnCreateAccount, btnProceed;
    private UserService userService = new UserServiceImpl();
    private UserParserIntf userParser = new UserParserImpl();
    private AlertDialog dialog;
    private ProgressDialog processDialog;

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
                    processDialog.show();
                    userService.retrieveUser(MainActivity.this, processDialog, new RestRequest() {
                        @Override
                        public void onSuccess(String response, ProgressDialog progressDialog) {
                            Boolean isValid = userParser.parseUser(response);
                            if (isValid) {
                                processDialog.dismiss();
                                Intent intent = new Intent(MainActivity.this, MainFormActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                processDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Wrong username or password.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(VolleyError e, ProgressDialog progressDialog) {
                            processDialog.dismiss();
                        }
                    });

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
        processDialog = new ProgressDialog(MainActivity.this);
        processDialog.setMessage("Logging in ...");
        processDialog.setCancelable(false);

        Constants.setIp(MainActivity.this);
    }


    private void clearFields() {
        editUN.setText("");
        editPW.setText("");
    }
}