package com.aclc.thesis.jmsapp.models;

import lombok.Data;

@Data
public class VisitorHistory {
    int visitorHistoryID;
    int userID;
    String firstName;
    String middleName;
    String lastName;
    String remarks;
    String visitDate;

}
