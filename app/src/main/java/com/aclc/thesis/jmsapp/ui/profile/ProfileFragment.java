package com.aclc.thesis.jmsapp.ui.profile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.preference.VisitorPreference;
import com.aclc.thesis.jmsapp.preference.VisitorPreferenceImpl;

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private Context mContext;
    private ImageView imgProfile;
    private TextView txtAccName, txtUsername;
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
        String fullName = visitorPreference.getFullName() != null ? visitorPreference.getFullName() : "";
        txtAccName.setText(fullName);
        return mView;
    }

    private void initViews(View mView) {
        imgProfile = mView.findViewById(R.id.imgProfile);
        txtAccName = mView.findViewById(R.id.txtAccName);
        txtUsername = mView.findViewById(R.id.txtAccUN);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
