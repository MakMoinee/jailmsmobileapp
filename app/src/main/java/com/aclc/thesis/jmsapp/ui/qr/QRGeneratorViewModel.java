package com.aclc.thesis.jmsapp.ui.qr;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class QRGeneratorViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public QRGeneratorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is qr fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}
