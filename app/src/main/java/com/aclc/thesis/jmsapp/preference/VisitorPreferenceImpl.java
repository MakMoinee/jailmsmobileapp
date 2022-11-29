package com.aclc.thesis.jmsapp.preference;

import android.content.Context;
import android.content.SharedPreferences;

import com.aclc.thesis.jmsapp.models.Visitor;

public class VisitorPreferenceImpl implements VisitorPreference {

    private Context context;
    private SharedPreferences pref;

    public VisitorPreferenceImpl(Context mContext) {
        this.context = mContext;
        pref = context.getSharedPreferences("visitor", Context.MODE_PRIVATE);
    }

    @Override
    public void storeVisitor(Visitor visitor) {
        if (visitor == null) return;
        pref = context.getSharedPreferences("visitor", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("visitorID", visitor.getVisitorID());
        editor.putInt("userID", visitor.getUserID());
        editor.putString("firstName", visitor.getFirstName());
        editor.putString("lastName", visitor.getLastName());
        editor.putString("middleName", visitor.getMiddleName());
        editor.putString("address", visitor.getAddress());
        editor.putString("birthPlace", visitor.getBirthPlace());
        editor.putString("birthDate", visitor.getBirthDate());
        editor.putString("contactNumber", visitor.getContactNumber());
        editor.putString("lastModifiedDate", visitor.getLastModifiedDate());
        editor.putString("createdDate", visitor.getCreatedDate());
        editor.commit();
        editor.apply();
    }

    @Override
    public String getFullName() {
        pref = context.getSharedPreferences("visitor", Context.MODE_PRIVATE);
        String fn = pref.getString("firstName", "");
        String ln = pref.getString("lastName", "");
        String mn = pref.getString("middleName", "");
        if (fn.length() > 0 && ln.length() > 0 && mn.length() > 0) {
            String fullName = fn + " " + mn + " " + ln;
            return fullName;
        }
        return "";
    }

    @Override
    public Visitor getVisitor() {
        pref = context.getSharedPreferences("visitor", Context.MODE_PRIVATE);
        Visitor visitor = new Visitor();
        visitor.setFirstName(pref.getString("firstName", ""));
        visitor.setMiddleName(pref.getString("middleName", ""));
        visitor.setLastName(pref.getString("lastName", ""));
        visitor.setAddress(pref.getString("address", ""));
        visitor.setBirthPlace(pref.getString("birthPlace", ""));
        visitor.setBirthDate(pref.getString("birthDate", ""));
        visitor.setContactNumber(pref.getString("contactNumber", ""));
        return visitor;
    }
}
