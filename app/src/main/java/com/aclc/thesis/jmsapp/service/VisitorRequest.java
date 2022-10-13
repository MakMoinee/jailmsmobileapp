package com.aclc.thesis.jmsapp.service;

import android.app.ProgressDialog;
import android.content.Context;

import com.aclc.thesis.jmsapp.models.Visitor;

public interface VisitorRequest {
    void SendGetVisitorDetails(Context context, ProgressDialog progressDialog, Visitor visitor, RestRequest restRequest);
}
