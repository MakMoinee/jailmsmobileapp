package com.aclc.thesis.jmsapp.preference;

import android.content.Context;

import com.aclc.thesis.jmsapp.models.Users;

public interface UserPreference {
    /**
     * @param users    - user to be set in preference
     * @param mContext - context of the activity
     *                 - Sets the user in preference
     */
    void setUsers(Users users);

    /**
     * @return - retrieves the userID from the user stored in preference.
     */
    int getUserID();

    /**
     * @return - retrieves the userType from the user stored in preference.
     */
    int getUserType();
}
