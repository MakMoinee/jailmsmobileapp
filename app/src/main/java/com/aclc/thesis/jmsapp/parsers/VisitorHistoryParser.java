package com.aclc.thesis.jmsapp.parsers;

import com.aclc.thesis.jmsapp.models.VisitorHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VisitorHistoryParser {

    public static List<VisitorHistory> parseFeed(String content) {
        List<VisitorHistory> visitorHistoryList = new ArrayList<>();
        try {
            JSONArray arr = new JSONArray(content);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                VisitorHistory visitorHistory = new VisitorHistory();
                visitorHistory.setVisitorHistoryID(obj.getInt("visitorHistoryID"));
                visitorHistory.setUserID(obj.getInt("userID"));
                visitorHistory.setFirstName(obj.getString("firstName"));
                visitorHistory.setMiddleName(obj.getString("middleName"));
                visitorHistory.setLastName(obj.getString("lastName"));
                visitorHistory.setRemarks(obj.getString("remarks"));
                visitorHistory.setVisitDate(obj.getString("visitDateTime"));
                visitorHistoryList.add(visitorHistory);
            }

            return visitorHistoryList;
        } catch (JSONException e) {
            e.printStackTrace();
            return visitorHistoryList;
        }


    }
}
