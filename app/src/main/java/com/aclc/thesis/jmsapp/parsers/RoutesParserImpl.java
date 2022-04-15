package com.aclc.thesis.jmsapp.parsers;

import android.content.Context;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.models.Routes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RoutesParserImpl implements RoutesParserIntf {

    @Override
    public List<Routes> getRouteList(Context mContext, String response) {
        List<Routes> routesList = new ArrayList<>();
        if (response.length() > 0) {
            try {
                JSONObject fObj = new JSONObject(response);
                JSONObject infoObj = fObj.getJSONObject("Info");
                JSONArray arr = infoObj.getJSONArray("routes");
                for (int j = 0; j < arr.length(); j++) {
                    JSONObject obj = arr.getJSONObject(j);
                    Routes routes = new Routes();
                    routes.setRouteName(obj.getString("RouteName"));
                    routes.setRouteValue(obj.getString("RouteValue"));
                    routesList.add(routes);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return routesList;
    }
}
