package com.aclc.thesis.jmsapp.common;

import android.content.Context;
import android.util.Log;

import com.aclc.thesis.jmsapp.MainActivity;
import com.aclc.thesis.jmsapp.preference.ConfigPrefImpl;
import com.aclc.thesis.jmsapp.preference.ConfigPreference;

public class Constants {
    public static String IP_ADDRESS = "192.168.1.1";
    public static String PROTOCOL = "http:";
    public static String FULL_URL = PROTOCOL + IP_ADDRESS;
    public static String LOGIN_PATH = "/get/jms/login";
    public static ConfigPreference config = new ConfigPrefImpl();

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
