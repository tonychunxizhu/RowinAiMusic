package com.huawei.tony.rowinaimusic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.huawei.tony.rowinaimusic.databinding.ActivityMainBinding;
import com.huawei.tony.rowinaimusic.jsondata.JsonData;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    final private String TAG = "AIMUSIC";
    JsonData jsonData = new JsonData();
    private static final int PERMISSION_REQUEST_CODE = 886;

    // Used to load the 'rowinaimusic' library on application startup.
    static {
        System.loadLibrary("rowinaimusic");
    }

    EditText inputText;
    Button button;
    TextView reply;
    String chat;

    String prompt;
    String id0, id1;
    String videoUrl0,  videoUrl1;

    String audeoUrl0, audeoUrl1;
    String status;
    String title, lyric;
    private VideoView videoView;
    private ProgressBar progressBar;
    private TextView progressText;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermission();

        inputText = findViewById(R.id.inputText);
        inputText.setMovementMethod(ScrollingMovementMethod.getInstance());
        button = findViewById(R.id.button);
        reply=findViewById(R.id.replyText);
        reply.setMovementMethod(ScrollingMovementMethod.getInstance());

        videoView = findViewById(R.id.videoView);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        progressBar.setVisibility(View.VISIBLE);
        progressText.setVisibility(View.VISIBLE);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setEnabled(false);
                int  creditLeft= getCredit();
                if(creditLeft <10){
                    Toast.makeText(MainActivity.this,"你的Credit只剩下 "+String.valueOf(creditLeft)+"无法继续使用。请明天再继续尝试。",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    int times = creditLeft/10;
                    Toast.makeText(MainActivity.this,"你的Credit剩下 "+String.valueOf(creditLeft)+", 还可以生成"+String.valueOf(times)+"首音乐",Toast.LENGTH_SHORT).show();
                }

                prompt = inputText.getText().toString();

                if (prompt.length() < 10) {
                    Toast.makeText(MainActivity.this,"提示词应不少于10个字符",Toast.LENGTH_LONG).show();
                    return;
                }

                //new Thread(new Runnable() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            createMusicandPlay();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        button.setEnabled(true);
                        reply.setText(title+"\n"+lyric);

                        //new ChatTask().execute();
                    }
                });//.start();
            }
        });

    }


    void createMusicandPlay() throws InterruptedException {


        genMusic(prompt);
        String videoUrl = videoUrl1;
        String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download";
        String destFileName = "video.mp4";




        DownloadUtil.get().download(videoUrl, destFileDir, destFileName, new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    progressText.setVisibility(View.GONE);
                    videoView.setVideoPath(file.getAbsolutePath());
                    videoView.start();
                });
            }

            @Override
            public void onDownloading(int progress) {
                runOnUiThread(() -> {
                    progressBar.setProgress(progress);
                    progressText.setText(progress + "%");
                });
            }

            @Override
            public void onDownloadFailed(Exception e) {
                e.printStackTrace();
            }
        });
    }
    /**
     * A native method that is implemented by the 'rowinaimusic' native library,
     * which is packaged with this application.
     */
    public native String nativeGetMusicById();
    public native String nativeGenMusic(String prompt);
    public native String nativeChat();
    public native String nativeGetCredit();

    public int getCredit(){
        return jsonData.getCredit(nativeGetCredit());
    }
    public boolean genMusic(String prompt) throws InterruptedException {


        String ret;
        ret = nativeGenMusic(prompt);
        if(null == ret){
            Log.i(TAG,"Failed to Generate Music");
        }
        do {
            id0 = jsonData.getIDbyGenRet(ret).get(0).id;
            id1 = jsonData.getIDbyGenRet(ret).get(1).id;
            Thread.sleep(500);
        } while(id0 == null || id1 == null);

        ret = nativeGetMusicById();
        //wait until the music completes
        do {
            status = jsonData.getMusicItems(ret).get(0).status;
            Log.d(TAG,"STATUS: "+status);
            ret = nativeGetMusicById();
            Thread.sleep(1000);
        }while (!status.equals("complete")); //if the music is still under generating


        videoUrl0 = jsonData.getVideoUrlByID(id0);
        videoUrl1 = jsonData.getVideoUrlByID(id1);

        audeoUrl0 = jsonData.getAudioUrlByID(id0);
        audeoUrl1 = jsonData.getAudioUrlByID(id1);

        lyric = jsonData.getMusicItems(ret).get(0).lyric;
        title = jsonData.getMusicItems(ret).get(0).title;

        Log.d(TAG,"id: "+id0+" "+id1);
        Log.d(TAG,"video "+videoUrl0+" "+videoUrl1);
        Log.d(TAG,"audio "+ audeoUrl0+" "+audeoUrl0);







        return true;
    }

    public void checkPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.INTERNET,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被授予，执行相应操作
                } else {
                    // 权限被拒绝，处理逻辑
                    Toast.makeText(this, "权限被拒绝！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    class ChatTask extends AsyncTask<Void, Void, String> {

        // String chat;
        @Override
        protected void onPreExecute() {
            //在开始任务之前执行的一个方法
            //用于进行一些界面的初始化操作，比如显示一个进度条对话框
            //Log.d("MainActivity", "onPostExecute: 我要开始干活啦");
        }

        @Override
        protected String doInBackground(Void... voids) {
            //在任务开始时执行的方法
            //执行耗时操作，如果AsyncTask任务一旦完成 通过return语句将结果返回 如果为void则不返回结果。
            //在此方法 中不能执行UI操作 若需要，调用publishProgress（Progress...）方法来完成
            //在调用这个方法之后onProgressUpdate会得到执行
            chat = nativeChat();
            Log.i("SUMOTEST",chat);
            publishProgress();

            return chat;
        }

        //在doInBackground中调用了publishProgress方法之后，onProgressUpdate会得到执行 该方法中携带的参数就在后台任务汇总传递过来的 在这个方法中对uI进行操作
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        //在后再任务执行完毕并通过return语句进行返回时，这个方法就很快会调用。利用返回数据进行UI操作。如，提醒任务执行结果。以及关闭掉进度条对话框等。
        @Override
        protected void onPostExecute(String result) {
            //任务执行完毕之后
            //Log.d("CHAT", result);
//            String mymusic="";
//            button.setEnabled(true);
//            String jsonString = result;
//            ObjectMapper objectMapper = new ObjectMapper();
//            try {
//                List<Musics> musicList = objectMapper.readValue(jsonString, new TypeReference<List<Musics>>(){});
//                for (Musics music : musicList) {
//                    mymusic=mymusic+music.getVideoUrl()+"\nTitle: "+ music.getTitle() + " " +"\nLyric: " + music.getLyric();
//                    // 其他字段的输出
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            reply.setText(mymusic);
            reply.setText(result);
        }
    }


}