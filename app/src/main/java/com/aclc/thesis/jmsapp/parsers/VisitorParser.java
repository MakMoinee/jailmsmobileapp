package com.aclc.thesis.jmsapp.parsers;

import android.content.Context;

import com.aclc.thesis.jmsapp.models.Visitor;

public interface VisitorParser {
    Visitor getVisitor(Context mContext, String response);
}
