package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;

import com.android.volley.VolleyError;

public interface RestRequest {
    void onSuccess(String response, ProgressDialog progressDialog);

    void onError(VolleyError e,ProgressDialog progressDialog);
}
