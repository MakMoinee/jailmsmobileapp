package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.utility.LocalUtil;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class SimpleRequest {
    private UserPreference userPreference;

    public void SendRequest(Context mContext, ProgressDialog progressDialog, RestRequest restRequest) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            return;
        }
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    public void SendSimpleGet(Context mContext, String path, ProgressDialog progressDialog, RestRequest restRequest) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            restRequest.onError(e, progressDialog);
            return;
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PROTOCOL + Constants.IP_ADDRESS + path, new Response.Listener<String>() {
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}
