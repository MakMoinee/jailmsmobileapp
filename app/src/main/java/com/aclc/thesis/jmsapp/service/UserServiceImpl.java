package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UserServiceImpl implements UserService {


    @Override
    public void retrieveUser(Context mContext, ProgressDialog progressDialog, RestRequest restRequest) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.FULL_URL + Constants.LOGIN_PATH, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                restRequest.onSuccess(response, progressDialog);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("retrieveUsersList()", error.toString());
                Toast.makeText(mContext, "Unexpected Error Occurred", Toast.LENGTH_SHORT).show();
                restRequest.onError(error, progressDialog);
            }
        });
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);

    }
}
