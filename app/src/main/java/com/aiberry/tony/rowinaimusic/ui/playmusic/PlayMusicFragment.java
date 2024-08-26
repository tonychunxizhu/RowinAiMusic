package com.aiberry.tony.rowinaimusic.ui.playmusic;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aiberry.tony.rowinaimusic.databinding.PlayMusicBinding;
import com.aiberry.tony.rowinaimusic.music.VideoFile;
import com.aiberry.tony.rowinaimusic.util.LogFile;

public class PlayMusicFragment extends Fragment {
    final private String TAG = "AIMUSIC-PlayMusicFrag";
    private PlayMusicBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        PlayMusicViewModel dashboardViewModel =
                new ViewModelProvider(this).get(PlayMusicViewModel.class);

        binding = PlayMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        VideoView videoView = binding.musicVideoView;
        Log.d(TAG, VideoFile.localVideoFile);
        LogFile.log(TAG + VideoFile.localVideoFile);
        videoView.setVideoPath(VideoFile.localVideoFile);

        videoView.start();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}