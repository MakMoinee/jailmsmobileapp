package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;

import com.aclc.thesis.jmsapp.models.Users;
import com.android.volley.VolleyError;

public interface PostRequest {
    void onSuccess(String response, Users users, ProgressDialog progressDialog);

    void onError(VolleyError error, ProgressDialog progressDialog);
}
