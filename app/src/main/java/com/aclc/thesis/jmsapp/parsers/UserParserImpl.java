package com.aclc.thesis.jmsapp.parsers;

import com.aclc.thesis.jmsapp.models.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserParserImpl implements UserParserIntf {
    List<Users> usersList = new ArrayList<>();

    @Override
    public Boolean parseUser(String response) {
        if(response.equals("")){
            return false;
        }
        return true;
    }

    @Override
    public List<Users> parseUsersList(String response) {
        usersList = new ArrayList<>();
        try {

            JSONArray arr = new JSONArray();
            for(int i=0;i<arr.length();i++){
                JSONObject obj = arr.getJSONObject(i);
                Users user = new Users();
                user.setUserID(obj.getInt("userID"));
                user.setUserName(obj.getString("userName"));
                user.setPassword(obj.getString("password"));
                user.setUserType(obj.getInt("userType"));
                usersList.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usersList;
    }
}
