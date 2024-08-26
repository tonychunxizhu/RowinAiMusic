package com.aiberry.tony.rowinaimusic.ui.managemusic;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.aiberry.tony.rowinaimusic.R;
import com.aiberry.tony.rowinaimusic.databinding.ManageMusicBinding;
import com.aiberry.tony.rowinaimusic.util.FileAdapter;

public class ManageMusicFragment extends Fragment {
    final String TAG="AIMUSIC-MgmtMusic";
    private ManageMusicBinding binding;
    private RecyclerView localFilesView;

    private FileAdapter fileAdapter;
    private List<File> fileList = new ArrayList<>();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ManageMusicViewModel notificationsViewModel =
                new ViewModelProvider(this).get(ManageMusicViewModel.class);

        binding = ManageMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        View view =  inflater.inflate(R.layout.manage_music, container, false);

        localFilesView = view.findViewById(R.id.localRView);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
        localFilesView.setLayoutManager(layoutManager);

        fileAdapter = new FileAdapter(fileList, getContext());
        localFilesView.setAdapter(fileAdapter);

        loadFiles();
        return view;
//        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void loadFiles() {
        String musicDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RowinAiMusic";

//        String dir1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()+"/RowinAiMusic";
        Log.d(TAG,musicDir);
//                File downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File downloadDir = new File(musicDir);


        File[] files = downloadDir.listFiles((dir, name) -> name.endsWith(".mp3") || name.endsWith(".mp4"));
        Log.d(TAG,String.valueOf(files.length));
        for(int i=0; i<files.length;i++){
            Log.d(TAG,files[i].getAbsolutePath());
        }
        if (files != null) {
            fileList.addAll(Arrays.asList(files));
            Log.d("FileListFragment", "Files loaded: " + fileList.size());
            fileAdapter.notifyDataSetChanged();
        }
        else {
            Log.d(TAG, "No files found");
        }
    }
}