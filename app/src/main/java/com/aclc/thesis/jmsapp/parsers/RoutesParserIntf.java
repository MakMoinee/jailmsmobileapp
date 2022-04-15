package com.aclc.thesis.jmsapp.parsers;

import android.content.Context;

import com.aclc.thesis.jmsapp.models.Routes;

import java.util.List;

public interface RoutesParserIntf {

    List<Routes> getRouteList(Context mContext, String response);
}
