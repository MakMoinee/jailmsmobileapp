package com.aclc.thesis.jmsapp.lifecycle;

import android.content.Context;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanIntentResult;
import com.journeyapps.barcodescanner.ScanOptions;

public class MyLifeCycle implements DefaultLifecycleObserver {
    private final ActivityResultRegistry mRegistry;
    private ActivityResultLauncher<ScanOptions> barcodeLauncher;
    private Context context;

    public MyLifeCycle(Context mContext, ActivityResultRegistry mRegistry) {
        this.mRegistry = mRegistry;
        this.context = mContext;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        barcodeLauncher = mRegistry.register("scanqr", new ScanContract(), new ActivityResultCallback<ScanIntentResult>() {
            @Override
            public void onActivityResult(ScanIntentResult result) {
                if(result.getContents()==null){
                    Toast.makeText(context, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void scanQR(ScanOptions options){
        barcodeLauncher.launch(options);
    }
}
