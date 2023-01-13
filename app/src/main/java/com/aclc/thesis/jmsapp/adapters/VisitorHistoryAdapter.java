package com.aclc.thesis.jmsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.models.VisitorHistory;

import java.util.List;

public class VisitorHistoryAdapter extends BaseAdapter {

    Context mContext;
    List<VisitorHistory> visitorHistoryList;
    TextView txtName, txtVisitDate;

    public VisitorHistoryAdapter(Context mContext, List<VisitorHistory> visitorHistoryList) {
        this.mContext = mContext;
        this.visitorHistoryList = visitorHistoryList;
    }

    @Override
    public int getCount() {
        return visitorHistoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return visitorHistoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_visit_history, null, false);
        initViews(mView);
        setValues(position);
        return mView;
    }

    private void setValues(int position) {
        VisitorHistory visitorHistory = visitorHistoryList.get(position);
        txtName.setText(visitorHistory.getLastName() + ", " + visitorHistory.getFirstName() + " " + visitorHistory.getMiddleName());
        txtVisitDate.setText(visitorHistory.getVisitDate());
    }

    private void initViews(View mView) {
        txtName = mView.findViewById(R.id.txtName);
        txtVisitDate = mView.findViewById(R.id.txtVisitDate);
    }
}
