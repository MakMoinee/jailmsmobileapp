package com.aclc.thesis.jmsapp.parsers;

import com.aclc.thesis.jmsapp.models.Inmates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InmatesParser {
    public static List<Inmates> parseFeed(String response) {
        List<Inmates> inmatesList = new ArrayList<>();

        try {
            JSONArray arr = new JSONArray(response);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                Inmates inmates = new Inmates();
                inmates.setName(obj.getString("lastName") + ", " + obj.getString("firstName") + " " + obj.getString("middleName"));
                inmatesList.add(inmates);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return inmatesList;
    }
}
