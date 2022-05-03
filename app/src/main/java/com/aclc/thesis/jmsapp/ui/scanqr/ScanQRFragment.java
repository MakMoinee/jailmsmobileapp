package com.aclc.thesis.jmsapp.ui.scanqr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aclc.thesis.jmsapp.R;
import com.google.zxing.integration.android.IntentIntegrator;

@SuppressLint("ValidFragment")
public class ScanQRFragment extends Fragment {

    private Context mContext;
    private Button btnScan;

    public ScanQRFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_scan_qr, container, false);
        initViews(mView);
        initListeners(mView);
        return mView;
    }

    private void initListeners(View mView) {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });
    }

    private void initViews(View mView) {
        btnScan = mView.findViewById(R.id.btnStartScan);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
