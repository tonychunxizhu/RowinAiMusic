package com.aiberry.tony.rowinaimusic.ui.managemusic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageMusicViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ManageMusicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}