package com.aclc.thesis.jmsapp.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.preference.VisitorPreference;
import com.aclc.thesis.jmsapp.preference.VisitorPreferenceImpl;

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private Context mContext;
    private ImageView imgProfile;
    private TextView txtAccName, txtUsername, txtAddress, txtBplace, txtFN, txtMN, txtLN, txtBdate;
    private VisitorPreference visitorPreference;
    private UserPreference userPreference;

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
        String fullName = visitorPreference.getFullName() != null ? visitorPreference.getFullName() : "";
        txtAccName.setText(fullName);
        return mView;
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
