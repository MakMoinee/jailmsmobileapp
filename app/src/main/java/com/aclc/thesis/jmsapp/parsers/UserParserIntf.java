package com.aclc.thesis.jmsapp.parsers;

import com.aclc.thesis.jmsapp.models.Users;

import java.util.List;

public interface UserParserIntf {
    List<Users> parseUsersList(String response);
}
