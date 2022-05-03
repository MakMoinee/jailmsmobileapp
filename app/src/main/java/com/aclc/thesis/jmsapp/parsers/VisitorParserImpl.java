package com.aclc.thesis.jmsapp.parsers;

import android.content.Context;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.models.Visitor;

import org.json.JSONException;
import org.json.JSONObject;

public class VisitorParserImpl implements VisitorParser {
    @Override
    public Visitor getVisitor(Context mContext, String response) {
        Visitor visitor = new Visitor();
        try {
            JSONObject obj = new JSONObject(response);

//            for (int i = 0; i < array.length(); i++) {
//                JSONObject obj = array.getJSONObject(i);
            visitor.setVisitorID(obj.getInt("visitorID"));
            visitor.setUserID(obj.getInt("userID"));
            visitor.setFirstName(obj.getString("firstName"));
            visitor.setMiddleName(obj.getString("lastName"));
            visitor.setLastName(obj.getString("middleName"));
            visitor.setAddress(obj.getString("address"));
            visitor.setBirthPlace(obj.getString("birthPlace"));
            visitor.setBirthDate(obj.getString("birthDate"));
            visitor.setLastModifiedDate(obj.getString("lastModifiedDate"));
            visitor.setCreatedDate(obj.getString("createdDate"));
//                break;
//            }
            return visitor;
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(mContext, "Error in parser:getVisitor() ->" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}
