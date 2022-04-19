package com.aclc.thesis.jmsapp.models;

import lombok.Data;

@Data
public class Users {
    int userID;
    String userName;
    String userPassword;
    int userType;
}
