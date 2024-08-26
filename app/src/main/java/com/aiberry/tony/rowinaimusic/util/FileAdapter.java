package com.aiberry.tony.rowinaimusic.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.aiberry.tony.rowinaimusic.R;
import com.aiberry.tony.rowinaimusic.music.VideoFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {
    final String TAG = "AIMUSIC-FileAdapter";
    private List<File> fileList;
    private Context context;

    public FileAdapter(List<File> fileList, Context context) {
        this.fileList = fileList;
        this.context = context;
    }

    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_file, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, int position) {
        File file = fileList.get(position);
        holder.fileName.setText(file.getName());
        holder.fileSize.setText(String.valueOf(file.length()));
        holder.fileDate.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(file.lastModified())));

        holder.playIcon.setOnClickListener(v -> {
            VideoFile.localVideoFile = file.getAbsolutePath();
            Log.d(TAG,VideoFile.localVideoFile);
//
            NavController navController = Navigation.findNavController((Activity) context, R.id.nav_host_fragment_activity_main);
            navController.popBackStack();
            navController.navigate(R.id.navigation_play);
        });
    }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        TextView fileName, fileSize, fileDate;
        ImageView playIcon;

        public FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileName = itemView.findViewById(R.id.file_name);
            fileSize = itemView.findViewById(R.id.file_size);
            fileDate = itemView.findViewById(R.id.file_date);
            playIcon = itemView.findViewById(R.id.play_icon);
        }
    }
}
