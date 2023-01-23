package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.CreateRequest;
import com.aclc.thesis.jmsapp.models.UserVisitor;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.models.Visitor;
import com.aclc.thesis.jmsapp.utility.LocalUtil;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class UserServiceImpl implements UserService {

    private final String boundary = "apiclient-" + System.currentTimeMillis();

    @Override
    public void retrieveUser(Context mContext, ProgressDialog progressDialog, RestRequest restRequest) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            restRequest.onError(e, progressDialog);
            return;
        }
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
                Toast.makeText(mContext, "UserServiceImpl -> " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void logUser(Context mContext, Users users, ProgressDialog progressDialog, RestRequest mReq) {
//        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
//        if (!isConnected) {
//            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
//            VolleyError e = new VolleyError("Not connected to internet");
//            mReq.onError(e, progressDialog);
//            return;
//        }
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    @Override
    public void createUser(Context mContext, Users user, ProgressDialog progressDialog, RestRequest mReq) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            mReq.onError(e, progressDialog);
            return;
        }
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
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    @Override
    public void createUser(Context mContext, Users users, Visitor visitor, ProgressDialog progressDialog, RestRequest mReq) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            mReq.onError(e, progressDialog);
            return;
        }
        String path = Constants.routeMap.get("CreateUser");
        CreateRequest createRequest = new CreateRequest();
        createRequest.setUser(users);
        createRequest.setVisitor(visitor);
        String finalReq = new Gson().toJson(createRequest);
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
                return finalReq.getBytes();
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }

    @Override
    public void forgotPass(Context mContext, UserVisitor userVisitor, ProgressDialog progressDialog, RestRequest mReq) {
        boolean isConnected = LocalUtil.isNetworkConnected(mContext);
        if (!isConnected) {
            Toast.makeText(mContext, "Please make sure you are connected to internet", Toast.LENGTH_SHORT).show();
            VolleyError e = new VolleyError("Not connected to internet");
            mReq.onError(e, progressDialog);
            return;
        }

        String path = Constants.routeMap.get("ForgotPassword");
        String finalReq = new Gson().toJson(userVisitor);
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
                return finalReq.getBytes();
            }
        };

        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
    }
}
