package com.aiberry.tony.rowinaimusic.ui.playmusic;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlayMusicViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public PlayMusicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}