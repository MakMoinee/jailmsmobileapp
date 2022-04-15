package com.aclc.thesis.jmsapp.parsers;

import android.content.Context;

import com.aclc.thesis.jmsapp.models.Users;

import java.util.List;

public interface UserParserIntf {
    /**
     * @param response
     * @param mContext
     * @return
     */
    List<Users> parseUsersList(String response, Context mContext);

    /**
     *
     * @param response
     * @return
     */
    Users parseUser(String response);
}
