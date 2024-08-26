package com.aiberry.tony.rowinaimusic.ui.genmusic;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.aiberry.tony.rowinaimusic.R;
import com.aiberry.tony.rowinaimusic.databinding.GenMusicBinding;
import com.aiberry.tony.rowinaimusic.jsondata.JsonData;
import com.aiberry.tony.rowinaimusic.music.VideoFile;
import com.aiberry.tony.rowinaimusic.ui.playmusic.PlayMusicFragment;
//import com.aiberry.tony.rowinaimusic.util.DownloadTask;
import com.aiberry.tony.rowinaimusic.util.LogFile;
import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GenMusicFragment extends Fragment {
    final String TAG = "AIMUSIC-GenMusicFrag";
    public static int credit = 0;
    JsonData jsonData = new JsonData();
    String prompt;
    String id0, id1, id, videoUrl, audioUrl, title, lyric, imgUrl;
    String localVideoFile;

    Button genButton, playButton, dlButton, mgmtButton;
    TextView statusText, lyricText,creditLeftText;
    private GenMusicBinding binding;

    public native String nativeGetMusicById(String id, String logfile);

    public native String nativeGenMusic(String prompt, String logfile);

    private PlayMusicFragment playFragment = new PlayMusicFragment();

    ProgressBar progressBar;
    ImageView videoCover;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GenMusicViewModel genMusicViewModel =
                new ViewModelProvider(this).get(GenMusicViewModel.class);

        binding = GenMusicBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        creditLeftText = binding.creditLeftText;

        genMusicViewModel.getText().observe(getViewLifecycleOwner(), creditLeftText::setText);

        EditText promptEdit = binding.promptText;

        lyricText = binding.lyricText;
        lyricText.setMovementMethod(ScrollingMovementMethod.getInstance());

        statusText = binding.statusText;

        genButton = binding.genButton;
        playButton = binding.playButton;
        mgmtButton = binding.mgmtButton;
        dlButton = binding.dlButton;
        videoCover = binding.videoCover;
        progressBar = binding.progressBar;
        progressBar.setVisibility(View.GONE);

        Activity myActivity = getActivity();

        if (VideoFile.isDownloaded) {
            Log.d(TAG, String.valueOf(VideoFile.credit));
            Log.d(TAG, VideoFile.lyric);
            Log.d(TAG, VideoFile.imgUrl);
            creditLeftText.setText(String.valueOf(VideoFile.credit));
            lyricText.setText(VideoFile.lyric);
            Glide.with(getActivity()).load(VideoFile.imgUrl).into(videoCover);

            LogFile.log(TAG + VideoFile.credit);
            LogFile.log(TAG + VideoFile.lyric);
            LogFile.log(TAG + VideoFile.imgUrl);


        }


        genButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playButton.setEnabled(false);
                dlButton.setEnabled(false);
                credit = jsonData.getCredit();
                if (credit < 10) {
                    Toast.makeText(myActivity, "你的Credit只剩下 " + String.valueOf(credit) + "无法继续使用。请明天再继续尝试。", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    int times = credit / 10;
                    Toast toast = Toast.makeText(myActivity, "你的Credit剩下 " + String.valueOf(credit) + ", 还可以生成" + String.valueOf(times) + "首音乐", Toast.LENGTH_LONG);
                    toast.show();
                }

                creditLeftText.setText(String.valueOf(credit-10));
                VideoFile.credit = credit-10;

                prompt = promptEdit.getText().toString();
                statusText.setText("音乐生成需要几分钟时间，请耐心等待...");
                progressBar.setVisibility(View.VISIBLE);

                GenMusicTask genMusicTask = new GenMusicTask();
                genMusicTask.execute();
            }
        });

        dlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!VideoFile.isDownloaded) {
                    Toast.makeText(getActivity(), "音乐尚未被生成", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                genButton.setEnabled(false);
                playButton.setEnabled(false);
                mgmtButton.setEnabled(false);
                dlButton.setEnabled(false);
                DownloadTask downloadTask = new DownloadTask();
                downloadTask.execute(VideoFile.remoteVideoUrl);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navHostController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navHostController.popBackStack();
                navHostController.navigate(R.id.navigation_play);
            }
        });


        mgmtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navHostController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navHostController.popBackStack();
                navHostController.navigate(R.id.navigation_mgmt);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
////////////////

    class DownloadTask extends AsyncTask<String, Integer, String> {

        final String TAG = "AIMUSIC-DownloadTask";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String url = urls[0];
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "download error:"+response);
                    LogFile.log(TAG + response);
                    return "";
                }
                Log.d(TAG, VideoFile.localVideoFile);
                File videoFile = new File(VideoFile.localVideoFile);

                try (InputStream inputStream = response.body().byteStream();
                     FileOutputStream outputStream = new FileOutputStream(videoFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    long totalBytesRead = 0;
                    long contentLength = response.body().contentLength();

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        totalBytesRead += bytesRead;
                        if (contentLength > 0) {
                            publishProgress((int) ((totalBytesRead * 100) / contentLength));
                        }
                        outputStream.write(buffer, 0, bytesRead);
                    }
                }
                return videoFile.getAbsolutePath();
            } catch (Exception e) {
                LogFile.log(TAG+" error in doinbackground "+e);
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(!result.isEmpty()) {
                genButton.setEnabled(true);
                playButton.setEnabled(true);
                mgmtButton.setEnabled(true);
                dlButton.setEnabled(true);
                statusText.setText("下载成功");
                progressBar.setVisibility(View.GONE);
            }
            else {
                genButton.setEnabled(true);
                playButton.setEnabled(true);
                mgmtButton.setEnabled(true);
                dlButton.setEnabled(true);
                statusText.setText("下载出错，请重新尝试");
                LogFile.log(TAG+" 下载出错");
                progressBar.setVisibility(View.GONE);

            }
        }
    }


    class GenMusicTask extends AsyncTask<String, Integer, String> {
        String TAG = "AIMUSIC-GenMusicTask";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            String s;
            try {
                s = genMusic(prompt);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Log.d(TAG, "return from doInBackground " + s);
            return s;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            genButton.setEnabled(true);
            playButton.setEnabled(true);
            mgmtButton.setEnabled(true);
            dlButton.setEnabled(true);
            statusText.setText("生成成功");
            progressBar.setVisibility(View.GONE);

            String l = title + "\n" + lyric;

            VideoFile.isDownloaded = true;
            VideoFile.title = title;
            VideoFile.remoteVideoUrl = videoUrl;
            VideoFile.credit = credit;
            VideoFile.imgUrl = imgUrl;
            VideoFile.lyric = lyric;
            VideoFile.remoteAudioUrl = audioUrl;


            String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RowinAiMusic/";
            String videoFileName = VideoFile.title + ".mp4";
            VideoFile.localVideoFile = destFileDir + videoFileName;
            String audioFileName = VideoFile.title + ".mp3";
            VideoFile.localAudioFile = destFileDir + audioFileName;

            Glide.with(getActivity()).load(imgUrl).into(videoCover);

            lyricText.setText(VideoFile.title + "\n" + VideoFile.lyric);

            statusText.setText("生成成功");
            genButton.setEnabled(true);
            playButton.setEnabled(true);
            dlButton.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        }
    }


    //////////////////////////

    /////////////////Gen Music/////////////////////////
    public int getCreditLeft() {
        return jsonData.getCredit();
    }

    public String genMusic(String prompt) {

        String ret = "";

        ret = nativeGenMusic(prompt,LogFile.logfile.getAbsolutePath());
        Log.d(TAG, "return from nativeGenMusic" + ret);
        if (null == ret) {
            Log.i(TAG, "Failed to Generate Music");
            return null;
        }
        for (; ; ) {
            id0 = jsonData.getIDbyGenRet(ret).get(0).id;
            id1 = jsonData.getIDbyGenRet(ret).get(1).id;
            if (id0 == null || id1 == null) {
                Log.d(TAG, "id from getIDbyGenRet is null");
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    Log.d(TAG, "can't sleep " + e.toString());
                }
            } else {
                Log.d(TAG, "id0:" + id0 + " id2" + id1);
                break;

            }
        }
        id = id0; //two musics are generated by SUNO by default, select the first music
//        id = "1a86e315-cebf-4b7a-a1ff-c3aa6154171a";
        ret = nativeGetMusicById(id, LogFile.logfile.getAbsolutePath());

        //only one video in a json array
        Log.d(TAG, ret);
        videoUrl = jsonData.getMusicItems(ret).get(0).videoUrl;
        audioUrl = jsonData.getMusicItems(ret).get(0).audioUrl;

        id = jsonData.getMusicItems(ret).get(0).id;
        title = jsonData.getMusicItems(ret).get(0).title;
        lyric = jsonData.getMusicItems(ret).get(0).lyric;
        imgUrl = jsonData.getMusicItems(ret).get(0).imageUrl;


        Log.d(TAG, "id: " + id);
        Log.d(TAG, "video: " + videoUrl);
        Log.d(TAG, "audio: " + audioUrl);
        Log.d(TAG, "title: " + title);
        Log.d(TAG, "lyric: " + lyric);
        LogFile.log(TAG + " id: " + id);
        LogFile.log(TAG + " video: " + videoUrl);
        LogFile.log(TAG + " audio: " + audioUrl);
        LogFile.log(TAG + " title: " + title);
        LogFile.log(TAG + " lyric: " + lyric);

        VideoFile.lyricFile = Environment.getExternalStorageDirectory().getAbsolutePath()+"/RowinAiMusic/"+title+".txt";
        try {
            FileWriter lyricWriter = new FileWriter(VideoFile.lyricFile);
            lyricWriter.write(title+"\n"+lyric);
            lyricWriter.close();
        }catch (Exception e){
            LogFile.log(TAG+" can't create lyric file");
        }
        try {
            Thread.sleep(5000); //wait 5s to make sure the video is downloadable
        } catch (Exception e) {
            Log.d(TAG, "can't sleep " + e);
        }

        return videoUrl;
    }

}