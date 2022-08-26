package com.aclc.thesis.jmsapp.service;

import android.content.Intent;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;

public class FragmentIntentIntegrator extends IntentIntegrator {
    private final Fragment fragment;

    public FragmentIntentIntegrator(Fragment fragment) {
        super(fragment.getActivity());
        this.fragment = fragment;
    }

    @Override
    protected void startActivityForResult(Intent intent, int code) {
        this.fragment.startActivityForResult(intent, code);
    }


}
