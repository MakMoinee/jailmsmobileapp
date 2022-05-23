package com.aclc.thesis.jmsapp.models;

import lombok.Data;

@Data
public class UserVisitor {
    int userID;
    String userName;
    String userPassword;
    int userType;
    int visitorID;
    String firstName;
    String lastName;
    String middleName;
    String address;
    String birthPlace;
    String birthDate;
    String lastModifiedDate;
    String createdDate;
}
