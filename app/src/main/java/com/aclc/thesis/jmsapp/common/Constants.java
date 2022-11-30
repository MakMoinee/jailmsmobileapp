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
    public static String IP_ADDRESS = "vps-6ce65c2c.vps.ovh.ca:8085";
    public static String PROTOCOL = "http://";
    public static String FULL_URL = PROTOCOL + IP_ADDRESS;
    public static String LOGIN_PATH = "/get/jms/users";
    public static String CREATE_ACCOUNT_PATH = "/create/jms/user";
    public static ConfigPreference config = new ConfigPrefImpl();
    public static String CONTENT_BODY = "application/json; charset=utf-8";
    public static String CONTENT_MULTIPART = "multipart/form-data;boundary=";
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

    public static final class Type {

        // Plain text. Use Intent.putExtra(DATA, string). This can be used for URLs too, but string
        // must include "http://" or "https://".
        public static final String TEXT = "TEXT_TYPE";

        // An email type. Use Intent.putExtra(DATA, string) where string is the email address.
        public static final String EMAIL = "EMAIL_TYPE";

        // Use Intent.putExtra(DATA, string) where string is the phone number to call.
        public static final String PHONE = "PHONE_TYPE";

        // An SMS type. Use Intent.putExtra(DATA, string) where string is the number to SMS.
        public static final String SMS = "SMS_TYPE";

        public static final String CONTACT = "CONTACT_TYPE";

        public static final String LOCATION = "LOCATION_TYPE";
    }
}
