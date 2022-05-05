package com.aclc.thesis.jmsapp.service;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

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
