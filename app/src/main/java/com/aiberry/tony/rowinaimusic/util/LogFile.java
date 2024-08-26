package com.aiberry.tony.rowinaimusic.util;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class LogFile {
    static final  String TAG = "AIMUSIC-LogFile";
    public static File logfile;
    public static FileWriter writer;
    public static void log(String s){
        try {
            writer = new FileWriter(logfile, true);
            writer.write(s+"\n");
            writer.close();

        }catch (Exception e){
            Log.d(TAG, "can't create FilerWriter: "+e.toString());
        }
    }
}
