package com.aclc.thesis.jmsapp.preference;

import android.content.Context;

public interface ConfigPreference {
    /**
     * @param ip       - ip address of the web service api (e.g: 192.168.1.1)
     * @param mContext - context of the activity
     */
    void setIP(String ip, Context mContext);

    /**
     * @param mContext - context of the activity
     * @return - retrieves the current set IP from the stored preference
     */
    String getIP(Context mContext);
}
