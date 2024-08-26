package com.aiberry.tony.rowinaimusic.ui.genmusic;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aiberry.tony.rowinaimusic.music.VideoFile;

public class GenMusicViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
//    private final MutableLiveData<Button> mGenButton;

    public GenMusicViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue(String.valueOf(VideoFile.credit));
    }

    public LiveData<String> getText() {
        return mText;
    }
//    public LiveData<String> getGenButton() {
//        return mText;
//    }
}