package com.aclc.thesis.jmsapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aclc.thesis.jmsapp.models.UserVisitor2;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.models.VisitorHistoryRequest;
import com.aclc.thesis.jmsapp.parsers.UserParserImpl;
import com.aclc.thesis.jmsapp.parsers.UserParserIntf;
import com.aclc.thesis.jmsapp.parsers.VisitorParser;
import com.aclc.thesis.jmsapp.parsers.VisitorParserImpl;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.VisitorRequest;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class VisitorDetailsActivity extends AppCompatActivity {
    private Visitor visitor;
    ProgressDialog pDialog;
    private VisitorParser visitorParser;
    private UserParserIntf userParser;
    private TextView txtAccName, txtFN, txtMN, txtLN, txtAddress, txtBplace, txtBdate, txtAccUN;
    private Button btnMark;
    private AlertDialog markDialog;
    private Button btnProceed;
    private TextInputEditText editRemarks;
    private UserVisitor2 globalUserVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_details_visitor);
        initViews();
        initListeners();
    }

    private void initListeners() {
        btnMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(VisitorDetailsActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_mark_visit, null, false);
                initDialogViews(mView);
                initDialogListeners();
                mBuilder.setView(mView);
                markDialog = mBuilder.create();
                markDialog.show();
            }
        });
    }

    private void initDialogListeners() {
        btnProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editRemarks.getText().toString().equals("")) {
                    Toast.makeText(VisitorDetailsActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    pDialog.show();
                    VisitorHistoryRequest visitorHistoryRequest = new VisitorHistoryRequest();
                    visitorHistoryRequest.setRemarks(editRemarks.getText().toString());
                    Visitor visitor = globalUserVisitor.getVisitor();
                    visitorHistoryRequest.setVisitorID(Integer.toString(visitor.getVisitorID()));

                    VisitorRequest.SendInsertVisitorHistory(VisitorDetailsActivity.this, visitorHistoryRequest, pDialog, new RestRequest() {
                        @Override
                        public void onSuccess(String response, ProgressDialog progressDialog) {
                            progressDialog.dismiss();
                            Toast.makeText(VisitorDetailsActivity.this, "Successfully Recorded Visitor History", Toast.LENGTH_SHORT).show();
                            markDialog.dismiss();
                            finish();
                        }

                        @Override
                        public void onError(VolleyError e, ProgressDialog progressDialog) {
                            progressDialog.dismiss();
                            Toast.makeText(VisitorDetailsActivity.this, "Failed to record visit history", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
    }

    private void initDialogViews(View mView) {
        btnProceed = mView.findViewById(R.id.btnProceed);
        editRemarks = mView.findViewById(R.id.editRemarks);
    }

    private void initViews() {
        globalUserVisitor = new UserVisitor2();
        btnMark = findViewById(R.id.btnMark);
        txtAccName = findViewById(R.id.txtAccName);
        txtAccUN = findViewById(R.id.txtAccUN);
        txtFN = findViewById(R.id.txtFN);
        txtMN = findViewById(R.id.txtMN);
        txtLN = findViewById(R.id.txtLN);
        txtAddress = findViewById(R.id.txtAddress);
        txtBplace = findViewById(R.id.txtBplace);
        txtBdate = findViewById(R.id.txtBdate);
        pDialog = new ProgressDialog(VisitorDetailsActivity.this);
        visitorParser = new VisitorParserImpl();
        userParser = new UserParserImpl();
        pDialog.setMessage("Send Request ...");
        pDialog.setCancelable(false);
        pDialog.show();
        visitor = new Visitor();
        String content = getIntent().getStringExtra("content");
        String[] contentSLice = content.split(";");
        if (contentSLice.length > 0) {
            String userID = contentSLice[0].toString().trim();
            visitor.setUserID(Integer.parseInt(userID));

        }

        loadData();
    }

    private void loadData() {
        if (visitor.getUserID() != 0) {
            VisitorRequest.SendVisitorRequest(VisitorDetailsActivity.this, Integer.toString(visitor.getUserID()), true, pDialog, new RestRequest() {
                @Override
                public void onSuccess(String response, ProgressDialog progressDialog) {
                    try {
                        JSONObject topObj = new JSONObject(response);

                        Visitor visitor = visitorParser.getVisitor(VisitorDetailsActivity.this, topObj.getJSONObject("visitor").toString());
                        if (visitor != null) {
                            Users users = userParser.parseUser(topObj.getJSONObject("users").toString());
                            pDialog.dismiss();
                            UserVisitor2 userVisitor = new UserVisitor2();
                            userVisitor.setVisitor(visitor);
                            userVisitor.setUsers(users);
                            setValues(userVisitor);
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(VisitorDetailsActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 100);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        pDialog.dismiss();
                        Toast.makeText(VisitorDetailsActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 100);
                    }

                }

                @Override
                public void onError(VolleyError e, ProgressDialog progressDialog) {
                    pDialog.dismiss();
                    Toast.makeText(VisitorDetailsActivity.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    }, 100);
                }
            });
        }
    }

    @SuppressLint("NewApi")
    private void setValues(UserVisitor2 userVisitor) {
        globalUserVisitor = userVisitor;
        Visitor visitor = userVisitor.getVisitor();
        Users users = userVisitor.getUsers();
        txtAccUN.setText(users.getUserName());
        txtAccName.setText(visitor.getFirstName() + " " + visitor.getMiddleName() + " " + visitor.getLastName());
        txtFN.setText(visitor.getFirstName());
        txtMN.setText(visitor.getMiddleName());
        txtLN.setText(visitor.getLastName());
        txtAddress.setText(visitor.getAddress());
        SimpleDateFormat detFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            LocalDateTime dateTime = LocalDateTime.parse(visitor.getBirthDate());
            txtBdate.setText(dateTime.toLocalDate().toString());
        } catch (Exception e) {
            e.printStackTrace();
            txtBdate.setText(visitor.getBirthDate());
        }

        txtBplace.setText(visitor.getBirthPlace());
    }
}
