package com.aclc.thesis.jmsapp.ui.profile;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.preference.VisitorPreference;
import com.aclc.thesis.jmsapp.preference.VisitorPreferenceImpl;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.SimpleRequest;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private Context mContext;
    private ImageView imgProfile;
    private TextView txtAccName, txtUsername, txtAddress, txtBplace, txtFN, txtMN, txtLN, txtBdate, txtContactNum;
    private Button btnUpdateProfile, btnUpdate;
    private EditText editAddress, editContactNum;
    private VisitorPreference visitorPreference;
    private UserPreference userPreference;
    private AlertDialog dialog;


    public ProfileFragment(Context mContext) {
        this.mContext = mContext;
        visitorPreference = new VisitorPreferenceImpl(mContext);
        userPreference = new UserPreferenceImpl(mContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_profile, container, false);
        initViews(mView);
        String username = userPreference.getUsers().getUserName() != null ? userPreference.getUsers().getUserName() : "";
        txtUsername.setText(username);
        Visitor visitor = visitorPreference.getVisitor();
        txtLN.setText(visitor.getLastName());
        txtMN.setText(visitor.getMiddleName());
        txtFN.setText(visitor.getFirstName());
        txtAddress.setText(visitor.getAddress());
        txtBplace.setText(visitor.getBirthPlace());
        txtBdate.setText(visitor.getBirthDate());
        txtContactNum.setText(visitor.getContactNumber());
        String fullName = visitorPreference.getFullName() != null ? visitorPreference.getFullName() : "";
        txtAccName.setText(fullName);
        initListener();
        return mView;
    }

    private void initListener() {
        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(mContext);
                View mView = getLayoutInflater().inflate(R.layout.dialog_update_profile, null, false);
                initDialogViews(mView);
                initDialogListener();
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    private void initDialogListener() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editAddress.getText().toString().equals("") || editContactNum.getText().toString().equals("")) {
                    Toast.makeText(mContext, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    Visitor visitor = new Visitor();
                    Visitor currentVisitor = new VisitorPreferenceImpl(mContext).getVisitor();
                    visitor.setVisitorID(currentVisitor.getVisitorID());
                    visitor.setAddress(editAddress.getText().toString());
                    visitor.setContactNumber(editContactNum.getText().toString());
                    String path = Constants.routeMap.get("UpdateVisitor");
                    String finalBody = new Gson().toJson(visitor);
                    new SimpleRequest().SendSimplePost(mContext, finalBody, path, null, new RestRequest() {
                        @Override
                        public void onSuccess(String response, ProgressDialog progressDialog) {
                            if (response.equals("")) {
                                Toast.makeText(mContext, "Failed To Update Profile", Toast.LENGTH_SHORT).show();
                            } else {
                                dialog.dismiss();
                                Toast.makeText(mContext, "Successfully Updated Profile", Toast.LENGTH_SHORT).show();

                                txtAddress.setText(visitor.getAddress());
                                txtContactNum.setText(visitor.getContactNumber());

                            }
                        }

                        @Override
                        public void onError(VolleyError e, ProgressDialog progressDialog) {
                            Toast.makeText(mContext, "Failed To Update Profile", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void initDialogViews(View mView) {
        editAddress = mView.findViewById(R.id.editAddress);
        editContactNum = mView.findViewById(R.id.editContactNum);
        btnUpdate = mView.findViewById(R.id.btnUpdate);

        editAddress.setText(txtAddress.getText().toString());
        editContactNum.setText(txtContactNum.getText().toString());
    }

    private void initViews(View mView) {
        imgProfile = mView.findViewById(R.id.imgProfile);
        txtAccName = mView.findViewById(R.id.txtAccName);
        txtUsername = mView.findViewById(R.id.txtAccUN);
        txtAddress = mView.findViewById(R.id.txtAddress);
        txtBplace = mView.findViewById(R.id.txtBplace);
        txtFN = mView.findViewById(R.id.txtFN);
        txtMN = mView.findViewById(R.id.txtMN);
        txtLN = mView.findViewById(R.id.txtLN);
        txtBdate = mView.findViewById(R.id.txtBdate);
        txtContactNum = mView.findViewById(R.id.txtContactNum);
        btnUpdateProfile = mView.findViewById(R.id.btnUpdateProfile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
