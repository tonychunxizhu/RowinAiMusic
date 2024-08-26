package com.aiberry.tony.rowinaimusic;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aiberry.tony.rowinaimusic.jsondata.JsonData;
import com.aiberry.tony.rowinaimusic.music.VideoFile;
import com.aiberry.tony.rowinaimusic.ui.genmusic.GenMusicFragment;
import com.aiberry.tony.rowinaimusic.util.LogFile;

public class LoginActivity extends AppCompatActivity {
    final String TAG="AIMUSIC-LoginActivity";
    public static final int FLAG_SUCCESS = 1;//创建成功
    public static final int FLAG_EXISTS = 2;//已存在
    public static final int FLAG_FAILED = 3;//创建失败

    JsonData jsonData = new JsonData();

    private static final int PERMISSION_REQUEST = 8899;

    static {
        System.loadLibrary("rowinaimusic");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        checkPermission();
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/RowinAiMusic";
                createDir(dir);

                LogFile.logfile = new File(dir+"/log.txt");
                if(!LogFile.logfile.exists()){
                    try {
                        LogFile.logfile.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
                String formattedDateTime = now.format(formatter);

                LogFile.log(formattedDateTime);
                int credit = getCreditLeft();
//                GenMusicFragment.credit = credit;
                VideoFile.credit = credit;
                Log.d(TAG,"credit left "+String.valueOf(credit));
                LogFile.log(TAG+" "+VideoFile.credit);

//                System.out.println("时间已写入文件: " + formattedDateTime);
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (validateLogin(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("CreditLeft",credit);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // 在这里添加实际的验证逻辑
//        return "user".equals(username) && "password".equals(password);
        return true;
    }

    public int getCreditLeft() {
        return jsonData.getCredit();
    }
    public native String nativeGetCreditLeft();
    public void checkPermission() {
        ActivityCompat.requestPermissions(LoginActivity.this,
                new String[]{android.Manifest.permission.INTERNET,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被授予，执行相应操作
                } else {
                    // 权限被拒绝，处理逻辑
                    Toast.makeText(this, "权限被拒绝！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    public int createDir (String dirPath) {

        File dir = new File(dirPath);
        //文件夹是否已经存在
        if (dir.exists()) {
            Log.w(TAG,"The directory [ " + dirPath + " ] has already exists");
            return FLAG_EXISTS;
        }
        if (!dirPath.endsWith(File.separator)) {//不是以 路径分隔符 "/" 结束，则添加路径分隔符 "/"
            dirPath = dirPath + File.separator;
        }
        //创建文件夹
        if (dir.mkdirs()) {
            Log.d(TAG,"create directory [ "+ dirPath + " ] success");
            return FLAG_SUCCESS;
        }

        Log.e(TAG,"create directory [ "+ dirPath + " ] failed");
        return FLAG_FAILED;
    }
}
