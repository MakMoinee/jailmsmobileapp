package com.aclc.thesis.jmsapp.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aclc.thesis.jmsapp.R;

@SuppressLint("ValidFragment")
public class HomeFragment extends Fragment {

    private Context mContext;

    public HomeFragment(Context mContext) {
        this.mContext = mContext;
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_home, container, false);

        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}