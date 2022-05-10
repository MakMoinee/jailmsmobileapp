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

import com.aclc.thesis.jmsapp.R;

@SuppressLint("ValidFragment")
public class ProfileFragment extends Fragment {

    private Context mContext;
    private ImageView imgProfile;

    public ProfileFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_profile, container, false);
        initViews(mView);
        return mView;
    }

    private void initViews(View mView) {
        imgProfile = mView.findViewById(R.id.imgProfile);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
