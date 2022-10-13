package com.aclc.thesis.jmsapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.aclc.thesis.jmsapp.models.Visitor;

public class VisitorDetailsActivity extends AppCompatActivity {
    private Visitor visitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_details_visitor);
        initViews();

    }

    private void initViews() {
        String content = getIntent().getExtras().getString("content");
        String[] contentSLice = content.split(";");
        visitor.setVisitorID(Integer.parseInt(contentSLice[0]));

        loadData();
    }

    private void loadData() {

    }
}
