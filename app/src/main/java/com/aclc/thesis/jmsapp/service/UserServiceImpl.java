package com.aclc.thesis.jmsapp.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class UserServiceImpl implements UserService {
    Boolean isResponded = false;
    String response = "";

    @Override
    public String retrieveUsersList(Context mContext) {
        isResponded = false;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                isResponded = true;
                response = response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                isResponded = true;
                Log.e("retrieveUsersList()", error.toString());
                Toast.makeText(mContext, "Unexpected Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);

        while (!isResponded) {
            Log.i("retrieveUsersList()", "still waiting for response");
        }
        return response;
    }
}
