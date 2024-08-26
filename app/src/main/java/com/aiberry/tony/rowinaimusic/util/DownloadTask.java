//package com.aiberry.tony.rowinaimusic.util;
//
//import android.os.AsyncTask;
//import android.os.Environment;
//import android.util.Log;
//import android.widget.ProgressBar;
//
//import com.aiberry.tony.rowinaimusic.music.VideoFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class DownloadTask extends AsyncTask<String, Integer, String> {
//
//    final String TAG = "AIMUSIC-DownloadTask";
//    @Override
//    protected void onPreExecute() {
//        super.onPreExecute();
////        progressBar.setVisibility(ProgressBar.VISIBLE);
//    }
//
//    @Override
//    protected String doInBackground(String... urls) {
//        String url = urls[0];
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder().url(url).build();
//        try (Response response = client.newCall(request).execute()) {
//            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
////            File videoFile = new File(getExternalFilesDir(null), "downloaded_video.mp4");
//
//
//
//            Log.d(TAG,VideoFile.localVideoFile);
//
////            File backupfile = new File(destFileDir + "/" + VideoFile.title + ".mp4");
////            Log.d(TAG, backupfile.getAbsolutePath());
//
//            File videoFile = new File(VideoFile.localVideoFile);
//
//            try (InputStream inputStream = response.body().byteStream();
//                 FileOutputStream outputStream = new FileOutputStream(videoFile)) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                long totalBytesRead = 0;
//                long contentLength = response.body().contentLength();
//
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    totalBytesRead += bytesRead;
//                    if (contentLength > 0) {
//                        publishProgress((int) ((totalBytesRead * 100) / contentLength));
//                    }
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            }
//            return videoFile.getAbsolutePath();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Override
//    protected void onProgressUpdate(Integer... values) {
//        super.onProgressUpdate(values);
////        progressBar.setProgress(values[0]);
//    }
//
//    @Override
//    protected void onPostExecute(String filePath) {
//        super.onPostExecute(filePath);
////        progressBar.setVisibility(ProgressBar.GONE);
//        if (filePath != null) {
////            videoView.setVideoPath(filePath);
////            videoView.start();
//        }
//    }
//}