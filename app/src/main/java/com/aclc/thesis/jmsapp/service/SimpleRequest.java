package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;

import com.aclc.thesis.jmsapp.common.Constants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SimpleRequest {

    public void SendRequest(Context mContext, ProgressDialog progressDialog, RestRequest restRequest) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PROTOCOL + Constants.IP_ADDRESS + "/info", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                restRequest.onSuccess(response, progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                restRequest.onError(error, progressDialog);
            }
        });

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}
