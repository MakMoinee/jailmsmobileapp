package com.aclc.thesis.jmsapp.common;

import android.content.Context;
import android.util.Log;

import com.aclc.thesis.jmsapp.MainActivity;
import com.aclc.thesis.jmsapp.models.Routes;
import com.aclc.thesis.jmsapp.preference.ConfigPrefImpl;
import com.aclc.thesis.jmsapp.preference.ConfigPreference;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {
    public static String IP_ADDRESS = "192.168.1.9:8443";
    public static String PROTOCOL = "http://";
    public static String FULL_URL = PROTOCOL + IP_ADDRESS;
    public static String LOGIN_PATH = "/get/jms/users";
    public static String CREATE_ACCOUNT_PATH = "/create/jms/user";
    public static ConfigPreference config = new ConfigPrefImpl();
    public static String CONTENT_BODY = "application/json; charset=utf-8";
    public static Map<String, String> routeMap;

    public static Boolean setIp(Context mContext) {
        try {
            String ip = Constants.config.getIP(mContext);
            if (!ip.equals("")) {
                Constants.IP_ADDRESS = ip;
                Log.i("setIP()", "successfully set ip");
                return true;
            }
        } catch (Exception e) {
            Log.e("setIP() >>", e.getMessage());
        }
        return false;
    }
}
