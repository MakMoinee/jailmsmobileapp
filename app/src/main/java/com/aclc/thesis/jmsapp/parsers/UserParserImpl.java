package com.aclc.thesis.jmsapp.parsers;

import com.aclc.thesis.jmsapp.models.Users;

import java.util.ArrayList;
import java.util.List;

public class UserParserImpl implements UserParserIntf {
    List<Users> usersList = new ArrayList<>();

    @Override
    public List<Users> parseUsersList(String response) {
        return null;
    }
}
