package com.aclc.thesis.jmsapp.preference;

import com.aclc.thesis.jmsapp.models.Visitor;

public interface VisitorPreference {
    void storeVisitor(Visitor visitor);

    String getFullName();

    Visitor getVisitor();
}
