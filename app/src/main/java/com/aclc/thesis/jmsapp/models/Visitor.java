package com.aclc.thesis.jmsapp.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Visitor {
    int visitorID;
    int userID;
    String firstName;
    String lastName;
    String middleName;
    String address;
    String birthPlace;
    String birthDate;
    String contactNumber;
    String lastModifiedDate;
    String createdDate;
}
