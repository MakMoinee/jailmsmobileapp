package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;

public interface UserService {
    void retrieveUser(Context mContext, ProgressDialog progressDialog, RestRequest mReq);
}
