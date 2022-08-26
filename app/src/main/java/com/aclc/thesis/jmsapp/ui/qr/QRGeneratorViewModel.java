package com.aclc.thesis.jmsapp.ui.qr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
