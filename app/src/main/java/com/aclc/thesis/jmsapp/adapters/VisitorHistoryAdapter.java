package com.aclc.thesis.jmsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.models.VisitorHistory;
import com.aclc.thesis.jmsapp.preference.LogoutPref;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class VisitorHistoryAdapter extends BaseAdapter {

    Context mContext;
    List<VisitorHistory> visitorHistoryList;
    TextView txtName, txtVisitDate, txtVisitOut, lblVisitOut;
    View thisView;

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
        thisView = mView;
        initViews(mView);
        setValues(position);
        return mView;
    }

    private void setValues(int position) {
        VisitorHistory visitorHistory = visitorHistoryList.get(position);
        txtName.setText(visitorHistory.getLastName() + ", " + visitorHistory.getFirstName() + " " + visitorHistory.getMiddleName());
        String visitDate = "";
        try {
            Date date1 = new SimpleDateFormat("yyyy-MM-dd hh:mm a").parse(visitorHistory.getVisitDate());
            visitDate = String.valueOf(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            visitDate = visitorHistory.getVisitDate();
        }
        txtVisitDate.setText(visitDate);

        String vhOut = new LogoutPref(mContext).getOut(visitorHistory.getVisitorHistoryID());
        if (vhOut != null && vhOut != "") {
            lblVisitOut.setVisibility(View.VISIBLE);
            txtVisitOut.setVisibility(View.VISIBLE);
            txtVisitOut.setText(vhOut);
        } else {
            lblVisitOut.setVisibility(View.GONE);
            txtVisitOut.setVisibility(View.GONE);
        }
    }

    private void initViews(View mView) {
        txtName = mView.findViewById(R.id.txtName);
        txtVisitDate = mView.findViewById(R.id.txtVisitDate);
        txtVisitOut = mView.findViewById(R.id.txtVisitOut);
        lblVisitOut = mView.findViewById(R.id.lblVisitOut);
    }

    public View getMyView() {
        return thisView;
    }
}
