package com.aiberry.tony.rowinaimusic.util;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.aiberry.tony.rowinaimusic.R;

public class FileViewHolder extends RecyclerView.ViewHolder {
    TextView fileName;

    public FileViewHolder(View itemView) {
        super(itemView);
        fileName = itemView.findViewById(R.id.file_name);
    }
}
