package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.utility.LocalUtil;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class VisitorRequestImpl implements VisitorRequest {

    @Override
    public void SendGetVisitorDetails(Context mContext, ProgressDialog progressDialog, Visitor visitor, RestRequest restRequest) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            restRequest.onError(e, progressDialog);
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public String getBodyContentType() {
                return Constants.CONTENT_BODY;
            }
        };
    }


}
