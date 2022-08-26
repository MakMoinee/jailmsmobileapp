package com.aclc.thesis.jmsapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.UserService;
import com.aclc.thesis.jmsapp.service.UserServiceImpl;
import com.android.volley.VolleyError;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText editUN, editPW, editConfirm, editFN, editMN, editLN, editBirthPlace, editAddress, editContactNum;
    private EditText editMonth, editDay, editYear;
    private Button btnCreate;
    private ProgressDialog progressDialog;
    private UserService userService = new UserServiceImpl();

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
                        editLN.getText().toString().equals("") ||
                        editContactNum.getText().toString().equals("") ||
                        editAddress.getText().toString().equals("") ||
                        editBirthPlace.getText().toString().equals("") ||
                        editMonth.getText().toString().equals("") ||
                        editDay.getText().toString().equals("") ||
                        editYear.getText().toString().equals("")) {
                    Toast.makeText(CreateAccountActivity.this, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    if (editConfirm.getText().toString().equals(editPW.getText().toString())) {
                        Users users = new Users();
                        users.setUserPassword(editPW.getText().toString());
                        users.setUserName(editUN.getText().toString());
                        users.setUserType(2);

                        Visitor visitor = new Visitor();
                        visitor.setFirstName(editFN.getText().toString());
                        visitor.setMiddleName(editMN.getText().toString());
                        visitor.setLastName(editLN.getText().toString());
                        visitor.setBirthPlace(editBirthPlace.getText().toString());
                        String bdate = editYear.getText().toString() + "-" + editMonth.getText().toString() + "-" + editDay.getText().toString();
                        visitor.setBirthDate(bdate);
                        visitor.setAddress(editAddress.getText().toString());
                        visitor.setContactNumber(editContactNum.getText().toString());
                        progressDialog.show();
                        btnCreate.setEnabled(false);
                        userService.createUser(CreateAccountActivity.this, users, visitor, progressDialog, new RestRequest() {
                            @Override
                            public void onSuccess(String response, ProgressDialog progressDialog) {
                                progressDialog.dismiss();
                                btnCreate.setEnabled(true);
                                Toast.makeText(CreateAccountActivity.this, response, Toast.LENGTH_SHORT).show();
                                clearFields();
                                finish();
                            }

                            @Override
                            public void onError(VolleyError e, ProgressDialog progressDialog) {
                                progressDialog.dismiss();
                                btnCreate.setEnabled(true);
                                Toast.makeText(CreateAccountActivity.this, "Error ->" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
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
        editBirthPlace = findViewById(R.id.editBirthPlace);
        editAddress = findViewById(R.id.editAddress);
        editContactNum = findViewById(R.id.editContact);
        editMonth = findViewById(R.id.bdMonth);
        editDay = findViewById(R.id.bdDay);
        editYear = findViewById(R.id.bdYear);
        progressDialog = new ProgressDialog(CreateAccountActivity.this);
        progressDialog.setMessage("Creating User ...");

        Constants.setIp(CreateAccountActivity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearFields();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void clearFields() {
        editUN.setText("");
        editPW.setText("");
        editConfirm.setText("");
        editFN.setText("");
        editMN.setText("");
        editLN.setText("");
        editContactNum.setText("");
        editAddress.setText("");
        editBirthPlace.setText("");
        editMonth.setText("");
        editDay.setText("");
        editYear.setText("");
    }
}
