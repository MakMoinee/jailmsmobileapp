package com.aclc.thesis.jmsapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class ConfigPrefImpl implements ConfigPreference {
    private SharedPreferences pref;

    @Override
    public void setIP(String ip, Context mContext) {
        pref = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("IP", ip);
        editor.apply();
    }

    @Override
    public String getIP(Context mContext) {
        pref = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        String ip = pref.getString("IP", "");
        return ip;
    }
}
