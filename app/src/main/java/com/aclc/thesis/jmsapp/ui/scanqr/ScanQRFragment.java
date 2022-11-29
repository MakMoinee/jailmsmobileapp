package com.aclc.thesis.jmsapp.ui.scanqr;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.VisitorDetailsActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

@SuppressLint("ValidFragment")
public class ScanQRFragment extends Fragment {
    private Context mContext;
    private Button btnScan;
    private TextView txtContent;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() == null) {
            Toast.makeText(mContext, "Cancelled", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(mContext, "Scan Successfully", Toast.LENGTH_SHORT).show();
            Log.e("Scanned ", result.getContents());
            showDetails(result.getContents());
        }
    });

    private void showDetails(String contents) {
        Log.e("CONTENT", contents);
        Intent intent = new Intent(mContext, VisitorDetailsActivity.class);
        intent.putExtra("content", contents);
        startActivity(intent);
    }

    public ScanQRFragment(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_scan_qr, container, false);
        initViews(mView);
        initListeners(mView);
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 123);
        }
        return mView;
    }

    private void initListeners(View mView) {
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 123);
                } else {
                    //                FragmentIntentIntegrator intentIntegrator = new FragmentIntentIntegrator(ScanQRFragment.this);
//                    IntentIntegrator intentIntegrator = new FragmentIntentIntegrator(ScanQRFragment.this);
//                    intentIntegrator.setPrompt("Scan a barcode or QR Code");
//                    intentIntegrator.setOrientationLocked(true);
//                    intentIntegrator.initiateScan();
                    ScanOptions options = new ScanOptions();
                    options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                    options.setOrientationLocked(false);
                    options.setTimeout(50000);
                    options.setPrompt("Scan a barcode");
                    options.setCameraId(0);  // Use a specific camera of the device
                    options.setBeepEnabled(false);
                    options.setBarcodeImageEnabled(true);
                    barcodeLauncher.launch(options);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 123) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "Camera permission granted", Toast.LENGTH_LONG).show();
//                IntentIntegrator intentIntegrator = new FragmentIntentIntegrator(ScanQRFragment.this);
//                intentIntegrator.setPrompt("Scan a barcode or QR Code");
//                intentIntegrator.setOrientationLocked(true);
//                intentIntegrator.initiateScan();
                ScanOptions options = new ScanOptions();
                options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
                options.setOrientationLocked(false);
                options.setTimeout(50000);
                options.setPrompt("Scan a barcode");
                options.setCameraId(0);  // Use a specific camera of the device
                options.setBeepEnabled(false);
                options.setBarcodeImageEnabled(true);
                barcodeLauncher.launch(options);
            } else {
                Toast.makeText(mContext, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initViews(View mView) {
        btnScan = mView.findViewById(R.id.btnStartScan);
        txtContent = mView.findViewById(R.id.txtContent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(mContext, "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                // if the intentResult is not null we'll set
                // the content and format of scan message
                txtContent.setText(intentResult.getContents());
//                messageFormat.setText(intentResult.getFormatName());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
