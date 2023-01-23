package com.aclc.thesis.jmsapp.ui.visithistory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.print.PrintManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.adapters.PrintAdapter;
import com.aclc.thesis.jmsapp.adapters.VisitorHistoryAdapter;
import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.models.VisitorHistory;
import com.aclc.thesis.jmsapp.parsers.VisitorHistoryParser;
import com.aclc.thesis.jmsapp.preference.LogoutPref;
import com.aclc.thesis.jmsapp.service.RestRequest;
import com.aclc.thesis.jmsapp.service.SimpleRequest;
import com.android.volley.VolleyError;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VisitorHistoryFragment extends Fragment {

    Context context;
    VisitorHistoryAdapter adapter;
    ListView lv;
    List<VisitorHistory> visitorHistoryList;
    AlertDialog mDialog;
    private Button btnLogout, btnSearch;
    private EditText editRemarks;
    private EditText editDate;
    FloatingActionButton btnPrint;
    View myView;

    public VisitorHistoryFragment(Context mContext) {
        this.context = mContext;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = LayoutInflater.from(context).inflate(R.layout.fragment_visit_history, container, false);
        myView = mView;
        initViews(mView);
        initListeners();
        return mView;
    }

    private void initListeners() {
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printPDF(myView);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VisitorHistory visitorHistory = visitorHistoryList.get(position);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                View mView = getLayoutInflater().inflate(R.layout.dialog_show_remarks, null, false);
                initDialogViews(mView);
                String vhOut = new LogoutPref(context).getOut(visitorHistory.getVisitorHistoryID());
                if (vhOut != "") {
                    btnLogout.setVisibility(View.GONE);
                }
                initDialogListener(visitorHistory);
                editRemarks.setText(visitorHistory.getRemarks());
                mBuilder.setView(mView);
                mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editDate.getText().toString().equals("")) {
                    Toast.makeText(context, "Please Don't Leave Empty Fields", Toast.LENGTH_SHORT).show();
                } else {
                    loadNewData();
                }
            }
        });
    }

    private void initDialogListener(VisitorHistory visitorHistory) {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                DialogInterface.OnClickListener dListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_NEGATIVE:
                                new LogoutPref(context).storeLogout(visitorHistory.getVisitorHistoryID());
                                Toast.makeText(context, "Successfully mark as log out", Toast.LENGTH_SHORT).show();
                                visitorHistoryList = new ArrayList<>();
                                loadNewData();
                                dialog.dismiss();
                                mDialog.dismiss();
                                break;
                            default:
                                dialog.dismiss();
                                break;
                        }
                    }
                };
                mBuilder.setMessage("You are about to mark log out visitor: " + visitorHistory.getFirstName())
                        .setNegativeButton("Yes", dListener)
                        .setPositiveButton("No", dListener)
                        .setCancelable(false)
                        .show();
            }
        });
    }

    private void loadDataWithKey(String toString) {

    }

    private void initDialogViews(View mView) {
        btnLogout = mView.findViewById(R.id.btnLogout);
        editRemarks = mView.findViewById(R.id.editRemarks);
    }

    private void initViews(View mView) {
        lv = mView.findViewById(R.id.lv);
        btnSearch = mView.findViewById(R.id.btnSearch);
        editDate = mView.findViewById(R.id.editDate);
        btnPrint = mView.findViewById(R.id.btnPrint);
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
                if (e.getMessage() != null) {
                    Log.e("ERROR_GETTING_HISTORY", e.getMessage().toString());
                }
            }
        });
    }

    private void loadNewData() {

        String path = Constants.routeMap.get("GetAllVisitorHistory");
        new SimpleRequest().SendSimpleGet(context, path, null, new RestRequest() {
            @Override
            public void onSuccess(String response, ProgressDialog progressDialog) {
                if (response.equals("")) {
                    Toast.makeText(context, "There are no visitor history recorded yet", Toast.LENGTH_SHORT).show();
                } else {
                    visitorHistoryList = VisitorHistoryParser.parseFeed(response);

                    if (visitorHistoryList != null) {

                        List<VisitorHistory> vhList = new ArrayList<>();
                        if (!editDate.getText().toString().equals("")) {
                            for (VisitorHistory vh : visitorHistoryList) {

                                String searchKey = editDate.getText().toString().trim();
                                try {
                                    Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(searchKey);
                                    Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(vh.getVisitDate());

                                    if (date2.equals(date1)) {
                                        vhList.add(vh);
                                    }

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                    continue;
                                }
                            }
                            visitorHistoryList = vhList;
                        } else {
                            vhList = visitorHistoryList;
                        }
                        adapter = new VisitorHistoryAdapter(context, vhList);
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


    public void printPDF(View view) {

        PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);

        // Set job name, which will be displayed in the print queue
        String jobName = getActivity().getString(R.string.app_name) + " Document";

        if (adapter.getMyView() == null) {
            Toast.makeText(context, "Can't Print, Empty List", Toast.LENGTH_SHORT).show();
            return;
        }
        // Start a print job, passing in a PrintDocumentAdapter implementation
        // to handle the generation of a print document
        printManager.print(jobName, new PrintAdapter(context, lv),
                null); //
    }

}
