package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.VisitorHistoryRequest;
import com.aclc.thesis.jmsapp.utility.LocalUtil;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class VisitorRequest {
    public static void SendVisitorRequest(Context mContext, String userID, boolean withUser, ProgressDialog progressDialog, RestRequest mReq) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            mReq.onError(e, progressDialog);
            return;
        }

        String path = Constants.routeMap.get("GetVisitorByUserID");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PROTOCOL + Constants.IP_ADDRESS + path + "?uid=" + userID + "&withUser=" + withUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mReq.onSuccess(response, progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mReq.onError(error, progressDialog);
            }
        });
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 3;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    public static void SendInsertVisitorHistory(Context mContext, VisitorHistoryRequest visitorHistoryRequest, ProgressDialog progressDialog, RestRequest mReq) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            mReq.onError(e, progressDialog);
            return;
        }
        String req = new Gson().toJson(visitorHistoryRequest);
        String path = Constants.routeMap.get("InsertVisitorHistory");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PROTOCOL + Constants.IP_ADDRESS + path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mReq.onSuccess(response, progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mReq.onError(error, progressDialog);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return Constants.CONTENT_BODY;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                return req.getBytes();
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 3;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}
