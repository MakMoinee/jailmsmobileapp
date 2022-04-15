package com.aclc.thesis.jmsapp.parsers;

import android.content.Context;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserParserImpl implements UserParserIntf {
    List<Users> usersList = new ArrayList<>();

    @Override
    public Users parseUser(String response) {
        Users user = new Users();
        if (response.equals("")) {
            return user;
        }

        try {
            JSONObject obj = new JSONObject(response);
            user.setUserID(obj.getInt("userID"));
            user.setUserName(obj.getString("userName"));
            user.setPassword(obj.getString("userPassword"));
            user.setUserType(obj.getInt("userType"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Users> parseUsersList(String response, Context mContext) {
        usersList = new ArrayList<>();
        try {

            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Users user = new Users();
                user.setUserID(obj.getInt("userID"));
                user.setUserName(obj.getString("userName"));
                user.setPassword(obj.getString("userPassword"));
                user.setUserType(obj.getInt("userType"));
                usersList.add(user);
            }
            return usersList;
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "", Toast.LENGTH_SHORT).show();
            return new ArrayList<>();
        }

    }
}
