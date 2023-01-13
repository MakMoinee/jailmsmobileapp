package com.aclc.thesis.jmsapp.ui.visithistory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.VisitorDetailsActivity;
import com.aclc.thesis.jmsapp.adapters.VisitorHistoryAdapter;
import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.VisitorHistory;
import com.aclc.thesis.jmsapp.parsers.VisitorHistoryParser;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.SimpleRequest;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class VisitorHistoryFragment extends Fragment {

    Context context;
    VisitorHistoryAdapter adapter;
    ListView lv;
    List<VisitorHistory> visitorHistoryList;
    AlertDialog dialog;
    private Button btnProceed, btnSearch;
    private EditText editRemarks;
    private DatePicker dpDate;

    public VisitorHistoryFragment(Context mContext) {
        this.context = mContext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_visit_history, container, false);
        initViews(mView);
        initListeners();
        return mView;
    }

    private void initListeners() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VisitorHistory visitorHistory = visitorHistoryList.get(position);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = getLayoutInflater().inflate(R.layout.dialog_show_remarks, null, false);
                initDialogViews(mView);
                editRemarks.setText(visitorHistory.getRemarks());
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dpDate.getYear() == 0 || dpDate.getMonth() == 0 || dpDate.getDayOfMonth() == 0) {
                    Toast.makeText(context, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    visitorHistoryList = new ArrayList<>();
                    loadDataWithKey(Integer.toString(dpDate.getYear()) + "-" + Integer.toString(dpDate.getMonth()) + "-" + Integer.toString(dpDate.getDayOfMonth()));
                }
            }
        });
    }

    private void loadDataWithKey(String toString) {

    }

    private void initDialogViews(View mView) {
        btnProceed = mView.findViewById(R.id.btnProceed);
        editRemarks = mView.findViewById(R.id.editRemarks);
        btnProceed.setVisibility(View.GONE);
    }

    private void initViews(View mView) {
        lv = mView.findViewById(R.id.lv);
        btnSearch = mView.findViewById(R.id.btnSearch);
        dpDate = mView.findViewById(R.id.dpDate);
        loadData();
    }

    private void loadData() {

        String path = Constants.routeMap.get("GetAllVisitorHistory");
        new SimpleRequest().SendSimpleGet(context, path, null, new RestRequest() {
            @Override
            public void onSuccess(String response, ProgressDialog progressDialog) {
                if (response.equals("")) {
                    Toast.makeText(context, "There are no visitor history recorded yet", Toast.LENGTH_SHORT).show();
                } else {
                    visitorHistoryList = VisitorHistoryParser.parseFeed(response);

                    if (visitorHistoryList != null) {
                        adapter = new VisitorHistoryAdapter(context, visitorHistoryList);
                        lv.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onError(VolleyError e, ProgressDialog progressDialog) {
                Log.e("ERROR_GETTING_HISTORY", e.getMessage());
            }
        });
    }


}
