package com.aclc.thesis.jmsapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class LogoutPref {
    Context mContext;
    SharedPreferences pref;

    public LogoutPref(Context mContext) {
        this.mContext = mContext;
        this.pref = this.mContext.getSharedPreferences("logout", Context.MODE_PRIVATE);
    }

    public void storeLogout(int visitorID) {
        SharedPreferences.Editor editor = pref.edit();
        String date = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault()).format(new Date());
        editor.putString(Integer.toString(visitorID), date);
        editor.commit();
        editor.apply();
    }

    public String getOut(int visitorID) {
        return pref.getString(Integer.toString(visitorID), "");
    }
}
