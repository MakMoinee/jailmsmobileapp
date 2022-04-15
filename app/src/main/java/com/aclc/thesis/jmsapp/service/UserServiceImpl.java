package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Users;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {


    @Override
    public void retrieveUser(Context mContext, ProgressDialog progressDialog, RestRequest restRequest) {
        String path = Constants.routeMap.get("RetrieveUsers");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.PROTOCOL + Constants.IP_ADDRESS + path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                restRequest.onSuccess(response, progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("retrieveUsersList()", error.toString());
//                Toast.makeText(mContext, "Unexpected Error Occurred", Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, "UserServiceImpl -> " + error.getMessage(), Toast.LENGTH_SHORT).show();
                restRequest.onError(error, progressDialog);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);

    }

    @Override
    public void logUser(Context mContext, Users users, ProgressDialog progressDialog, RestRequest mReq) {
        String path = Constants.routeMap.get("LogUser");
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
                String mData = new Gson().toJson(users);
                return mData.getBytes();
            }
        };
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    @Override
    public void createUser(Context mContext, Users user, ProgressDialog progressDialog, RestRequest mReq) {
        String userReq = new Gson().toJson(user);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.PROTOCOL + Constants.IP_ADDRESS + Constants.CREATE_ACCOUNT_PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                mReq.onSuccess(response, progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "Unexpected error happen in Creating User, Please Try Again Later!", Toast.LENGTH_SHORT).show();
                mReq.onError(error, progressDialog);
            }
        }) {

            @Override
            public byte[] getBody() throws AuthFailureError {
                return userReq.getBytes();
            }

            @Override
            public String getBodyContentType() {
                return Constants.CONTENT_BODY;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}
