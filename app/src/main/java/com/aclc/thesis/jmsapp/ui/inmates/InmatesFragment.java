package com.aclc.thesis.jmsapp.ui.inmates;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.adapters.InmatesAdapter;
import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.Inmates;
import com.aclc.thesis.jmsapp.parsers.InmatesParser;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.SimpleRequest;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class InmatesFragment extends Fragment {

    Context mContext;
    List<Inmates> inmatesList = new ArrayList<>();
    ListView lv;
    InmatesAdapter adapter;
    FloatingActionButton btnAddInmates;

    public InmatesFragment(Context c) {
        this.mContext = c;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_inmates, container, false);
        lv = mView.findViewById(R.id.lv);
        btnAddInmates = mView.findViewById(R.id.btnAddInmates);
        initListeners(mView);
        loadInmates();
        return mView;
    }

    private void initListeners(View mView) {
        btnAddInmates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loadInmates() {
        String path = Constants.routeMap.get("GetInmates");
        new SimpleRequest().SendSimpleGet(mContext, path, null, new RestRequest() {
            @Override
            public void onSuccess(String response, ProgressDialog progressDialog) {
                inmatesList = InmatesParser.parseFeed(response);
                if (inmatesList != null) {
                    adapter = new InmatesAdapter(mContext, inmatesList);
                    lv.setAdapter(adapter);
                }
            }

            @Override
            public void onError(VolleyError e, ProgressDialog progressDialog) {
                Log.e("ERROR_GETTING_INMATES", e.getMessage());
            }
        });
    }
}
