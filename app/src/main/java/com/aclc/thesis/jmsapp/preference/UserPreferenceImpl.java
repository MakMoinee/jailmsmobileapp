package com.aclc.thesis.jmsapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.aclc.thesis.jmsapp.models.Users;

public class UserPreferenceImpl implements UserPreference {

    SharedPreferences pref;
    Context mContext;

    public UserPreferenceImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void setUsers(Users users) {
        pref = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("userID", users.getUserID());
        editor.putString("userName", users.getUserName());
        editor.putString("userPassword", users.getUserPassword());
        editor.putInt("userType", users.getUserType());
        editor.apply();
        editor.commit();
    }

    @Override
    public int getUserID() {
        pref = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        return pref.getInt("userID", 0);
    }

    @Override
    public int getUserType() {
        pref = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        return pref.getInt("userType", 0);
    }

    @Override
    public Users getUsers() {
        pref = mContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        Users users = new Users();
        users.setUserName(pref.getString("userName", ""));
        users.setUserID(pref.getInt("userID", 0));

        return users;
    }
}
