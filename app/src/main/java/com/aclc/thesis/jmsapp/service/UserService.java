package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;

import com.aclc.thesis.jmsapp.models.Users;

public interface UserService {
    void retrieveUser(Context mContext, ProgressDialog progressDialog, RestRequest mReq);

    void createUser(Context mContext, Users user, ProgressDialog progressDialog, RestRequest mReq);

    void logUser(Context mContext, Users users, ProgressDialog progressDialog, RestRequest mReq);
}
