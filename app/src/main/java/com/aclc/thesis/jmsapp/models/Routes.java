package com.aclc.thesis.jmsapp.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Routes {

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteValue() {
        return routeValue;
    }

    public void setRouteValue(String routeValue) {
        this.routeValue = routeValue;
    }

    private String routeName;
    private String routeValue;
}
