package com.aclc.thesis.jmsapp.ui.qr;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.aclc.thesis.jmsapp.R;
import com.aclc.thesis.jmsapp.common.Constants;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.service.QRCodeLocal;
import com.aclc.thesis.jmsapp.service.QRCodeLocalImpl;

import java.sql.Timestamp;


@SuppressLint("ValidFragment")
public class QRGeneratorFragment extends Fragment {

    private ImageView imgView;
    private QRCodeLocal qrCodeLocal;
    private Context mContext;
    private UserPreference userPreference;

    public QRGeneratorFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.fragment_qr_generator, container, false);
        initListener(mView);
        createQR();
        return mView;
    }

    private void createQR() {
        try {
            WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);

            int width = point.x;
            int height = point.y;
            int dimen = width < height ? width : height;
            dimen = dimen * 3 / 4;

            String data = getData();

            qrCodeLocal = new QRCodeLocalImpl(data, Constants.Type.TEXT, dimen);
            Bitmap bitmap = qrCodeLocal.getBitmap();
            imgView.setImageBitmap(bitmap);
        } catch (Exception e) {
            Toast.makeText(mContext, "Error in createQR()->" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getData() {
        userPreference = new UserPreferenceImpl(mContext);
        int userID = userPreference.getUserID();
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        String userIDStr = Integer.toString(userID);
        String[] tsStr = ts.toString().split(" ");
        String fullData = userIDStr + ";" + tsStr[1];
        return fullData;
    }

    private void initListener(View mView) {
        imgView = mView.findViewById(R.id.imgQR);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
