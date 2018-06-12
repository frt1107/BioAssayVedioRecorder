package com.didi.bioassay.Activity;

import android.Manifest;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.didi.bioassay.R;
import com.werb.permissionschecker.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    private Button video1, video2, video3;
    private PermissionChecker permissionChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        permissionChecker = new PermissionChecker(this); // initialize，must need

        video1 = findViewById(R.id.btn_video1);
        video2 = findViewById(R.id.btn_video2);
        video3 = findViewById(R.id.btn_video3);
//        loadView();
    }

    /**
     * 加载视图
     */
    protected void loadView(){

        permissionChecker = new PermissionChecker(this); // initialize，must need

        video1 = findViewById(R.id.btn_video1);
        video2 = findViewById(R.id.btn_video2);
        video3 = findViewById(R.id.btn_video3);
    }

    /**
     * 按钮点击事件
     * @param v
     */
    public void start(View v){
        if (permissionChecker.isLackPermissions(PERMISSIONS)) {
            permissionChecker.requestPermissions();
        } else {
            startVideo();
        }
    }

    /**
     * 跳转到VideoRecordActivity
     */
    private void startVideo(){
        startActivity(new Intent(MainActivity.this, VideoRecordActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                    startVideo();
                } else {
                    permissionChecker.showDialog();
                }
                break;
        }
    }
}
