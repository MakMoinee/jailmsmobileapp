package com.aclc.thesis.jmsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.models.Inmates;

import java.util.List;

public class InmatesAdapter extends BaseAdapter {

    Context mContext;
    List<Inmates> inmatesList;
    TextView txtName;

    public InmatesAdapter(Context c, List<Inmates> l) {
        this.mContext = c;
        this.inmatesList = l;
    }

    @Override
    public int getCount() {
        return inmatesList.size();
    }

    @Override
    public Object getItem(int position) {
        return inmatesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_inmates, parent, false);
        initViews(mView);
        Inmates inmates = inmatesList.get(position);
        txtName.setText(inmates.getName());
        return mView;
    }

    private void initViews(View mView) {
        txtName = mView.findViewById(R.id.txtInmateName);
    }
}
