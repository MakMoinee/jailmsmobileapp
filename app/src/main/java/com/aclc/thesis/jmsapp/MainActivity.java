package com.aclc.thesis.jmsapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Routes;
import com.aclc.thesis.jmsapp.models.UserVisitor;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.parsers.RoutesParserImpl;
import com.aclc.thesis.jmsapp.parsers.RoutesParserIntf;
import com.aclc.thesis.jmsapp.parsers.UserParserImpl;
import com.aclc.thesis.jmsapp.parsers.UserParserIntf;
import com.aclc.thesis.jmsapp.parsers.VisitorParser;
import com.aclc.thesis.jmsapp.parsers.VisitorParserImpl;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.preference.VisitorPreference;
import com.aclc.thesis.jmsapp.preference.VisitorPreferenceImpl;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.SimpleRequest;
import com.aclc.thesis.jmsapp.service.UserService;
import com.aclc.thesis.jmsapp.service.UserServiceImpl;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText editUN, editPW, editIP;
    private EditText editForgotUN, editNewPass, editConfirmPass, editBirthPlace, editFN, editLN, editMN;
    private Button btnLogin, btnCreateAccount, btnProceed, btnResetPassword;
    private UserService userService = new UserServiceImpl();
    private UserParserIntf userParser = new UserParserImpl();
    private RoutesParserIntf routesParser = new RoutesParserImpl();
    private UserPreference userPreference;
    private List<Routes> routesList = new ArrayList<>();
    private AlertDialog dialog;
    private ProgressDialog processDialog;
    private VisitorParser visitorParser;
    private VisitorPreference visitorPreference;
    private TextView lblForgot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setViews();
        getRoutes();
        setListeners();
    }

    private void getRoutes() {
        SimpleRequest simpleRequest = new SimpleRequest();
        processDialog.setMessage("Setting up ...");
        processDialog.show();
        processDialog.setCancelable(true);

        simpleRequest.SendRequest(MainActivity.this, processDialog, new RestRequest() {
            @Override
            public void onSuccess(String response, ProgressDialog progressDialog) {
                routesList = routesParser.getRouteList(MainActivity.this, response);
                Map<String, String> routeMap = new HashMap<>();
                for (int i = 0; i < routesList.size(); i++) {
                    Routes routes = routesList.get(i);
                    routeMap.put(routes.getRouteName(), routes.getRouteValue());
                }

                Constants.routeMap = routeMap;
                processDialog.dismiss();
                processDialog.setMessage("Logging in ...");
            }

            @Override
            public void onError(VolleyError e, ProgressDialog progressDialog) {
                processDialog.dismiss();
                processDialog.setMessage("Logging in ...");
            }
        });
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
                    Users users = new Users();
                    users.setUserName(editUN.getText().toString());
                    users.setUserPassword(editPW.getText().toString());
                    userService.logUser(MainActivity.this, users, processDialog, new RestRequest() {
                        @Override
                        public void onSuccess(String response, ProgressDialog progressDialog) {
                            Users userLogin = userParser.parseUser(response);
                            processDialog.dismiss();
                            if (userLogin != null) {
                                userPreference.setUsers(userLogin);
                                int type = userLogin.getUserType();
                                if (Integer.toString(type).equals("1")) {
                                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, AdminFormActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    getData();
                                }


                            } else {
                                Toast.makeText(MainActivity.this, "Wrong username or password.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(VolleyError e, ProgressDialog progressDialog) {
                            processDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Login attempt failed. Please contact system administrator", Toast.LENGTH_SHORT).show();
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

        lblForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editUN.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please fill the username first", Toast.LENGTH_SHORT).show();
                } else {
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_forgot_pass, null, false);
                    initForgotPassView(mView);
                    initForgotPassListener(mView);
                    mBuilder.setView(mView);
                    mBuilder.setCancelable(true);
                    dialog = mBuilder.create();
                    dialog.show();
                }
            }
        });
    }

    private void clearForgotWidgets() {
        editForgotUN.setText("");
        editNewPass.setText("");
        editConfirmPass.setText("");
        editBirthPlace.setText("");
        editFN.setText("");
        editLN.setText("");
        editMN.setText("");
    }

    private void initForgotPassListener(View mView) {
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editForgotUN.getText().toString().equals("")
                        || editNewPass.getText().toString().equals("")
                        || editConfirmPass.getText().toString().equals("")
                        || editBirthPlace.getText().toString().equals("")
                        || editFN.getText().toString().equals("")
                        || editLN.getText().toString().equals("")
                        || editMN.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (editNewPass.getText().toString().equals(editConfirmPass.getText().toString())) {
                        UserVisitor userVisitor = new UserVisitor();
                        userVisitor.setUserName(editForgotUN.getText().toString());
                        userVisitor.setUserPassword(editNewPass.getText().toString());
                        userVisitor.setFirstName(editFN.getText().toString());
                        userVisitor.setLastName(editLN.getText().toString());
                        userVisitor.setMiddleName(editMN.getText().toString());
                        userVisitor.setBirthPlace(editBirthPlace.getText().toString());

                        processDialog.setMessage("Resetting Password ...");
                        processDialog.show();

                        userService.forgotPass(MainActivity.this, userVisitor, processDialog, new RestRequest() {
                            @Override
                            public void onSuccess(String response, ProgressDialog progressDialog) {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
                                clearForgotWidgets();
                                dialog.dismiss();
                                processDialog.setMessage("Logging in ...");
                            }

                            @Override
                            public void onError(VolleyError e, ProgressDialog progressDialog) {
                                progressDialog.dismiss();
                                processDialog.setMessage("Logging in ...");
                                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(MainActivity.this, "Password Doesn't Match", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void initForgotPassView(View mView) {
        editForgotUN = mView.findViewById(R.id.editForgotUN);
        editNewPass = mView.findViewById(R.id.editNewPass);
        editConfirmPass = mView.findViewById(R.id.editConfirmPass);
        editBirthPlace = mView.findViewById(R.id.editBirthPlace);
        editFN = mView.findViewById(R.id.editFN);
        editLN = mView.findViewById(R.id.editLN);
        editMN = mView.findViewById(R.id.editMN);
        btnResetPassword = mView.findViewById(R.id.btnResetPass);

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
                    getRoutes();
                }
            }
        });
    }

    private void initDialogViews(View mView) {
        editIP = mView.findViewById(R.id.editIP);
        btnProceed = mView.findViewById(R.id.btnSet);
    }

    private void setViews() {
        userPreference = new UserPreferenceImpl(MainActivity.this);
        int id = userPreference.getUserID();
        int type = userPreference.getUserType();
        if (id != 0 && type != 0) {
            Constants.setIp(MainActivity.this);
            if (type == 1) {
                Intent intent = new Intent(MainActivity.this, AdminFormActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(MainActivity.this, MainFormActivity.class);
                startActivity(intent);
                finish();
            }
        }
        editUN = findViewById(R.id.editUN);
        editPW = findViewById(R.id.editPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        lblForgot = findViewById(R.id.lblForgot);
        processDialog = new ProgressDialog(MainActivity.this);
        processDialog.setMessage("Logging in ...");
        processDialog.setCancelable(false);
        Constants.setIp(MainActivity.this);

    }


    private void clearFields() {
        editUN.setText("");
        editPW.setText("");
    }

    private void getData() {
        userPreference = new UserPreferenceImpl(MainActivity.this);
        int userid = userPreference.getUserID();
        int type = userPreference.getUserType();
        if (userid == 0 || type == 1) return;
        SimpleRequest simpleRequest = new SimpleRequest();
        String path = Constants.routeMap.get("GetVisitorByUserID");

        String fullPath = path + "?uid=" + Integer.toString(userid);

        simpleRequest.SendSimpleGet(MainActivity.this, fullPath, null, new RestRequest() {
            @Override
            public void onSuccess(String response, ProgressDialog progressDialog) {
                visitorParser = new VisitorParserImpl();
                Visitor visitor = visitorParser.getVisitor(MainActivity.this, response);
                if (visitor != null) {
                    visitorPreference = new VisitorPreferenceImpl(MainActivity.this);
                    visitorPreference.storeVisitor(visitor);
                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, MainFormActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError(VolleyError e, ProgressDialog progressDialog) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}