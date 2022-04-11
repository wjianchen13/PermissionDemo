package com.example.permissiondemo;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

/**
 * name:AspectjActivity
 * desc: 使用Aspectj方式
 * author:
 * date: 2017-08-22 19:30
 * remark:
 */
public class AspectjActivity extends AppCompatActivity {

    private Button btnTest = null;
    private Button btnWrite = null;
    private Button btnRead = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aspectj);
        btnTest = (Button) findViewById(R.id.btn_test);
        btnTest.setOnClickListener(clickListener);

        btnWrite = (Button) findViewById(R.id.btn_write);
        btnWrite.setOnClickListener(clickListener);

        btnRead = (Button) findViewById(R.id.btn_read);
        btnRead.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_test:
                    checkPermission();
                    break;

                case R.id.btn_write:
                    createVideoFile("hello");
                    break;

                case R.id.btn_read:
                    Toast.makeText(AspectjActivity.this,"file exist: " + isVideoFileExists("hello"), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @NeedPermission(permissions = {Manifest.permission.RECEIVE_SMS, Manifest.permission.SEND_SMS})
    private void checkPermission() {
        Toast.makeText(AspectjActivity.this, "权限请求成功", Toast.LENGTH_SHORT).show();
    }

    @NeedPermission(permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public static boolean createVideoFile(String name) {
        boolean isSdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSdCardExist) {
            String videoFilePath = Environment.getExternalStorageDirectory().getPath() + "/95xiu/";
            File file = new File(videoFilePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String fileName = videoFilePath + name;
            File videoFile = new File(fileName);
            if (!videoFile.exists()) {
                try {
                    videoFile.createNewFile();
                    return true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }

//    @NeedPermission(permissions = {Manifest.permission.READ_EXTERNAL_STORAGE})
    public static boolean isVideoFileExists(String name) {
        boolean isSdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (isSdCardExist) {
            String videoFilePath = Environment.getExternalStorageDirectory().getPath() + "/95xiu/" + name;
            File file = new File(videoFilePath);
            if (file != null && file.exists() && file.isFile()) {
                return true;
            }
        }
        return false;
    }


}
