package com.didi.bioassay.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.didi.bioassay.MediaUtils;
import com.didi.bioassay.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by frt on 2018/6/7.
 */
public class VideoRecordActivity extends AppCompatActivity{
    private MediaUtils mediaUtils;

    private Timer timer;
    private long startTime;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_recorder);
        SurfaceView surfaceView = findViewById(R.id.main_surface_view);
        // setting
        mediaUtils = new MediaUtils(this);
        mediaUtils.setRecorderType(MediaUtils.MEDIA_VIDEO);
        mediaUtils.setTargetDir(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
        mediaUtils.setTargetName(UUID.randomUUID() + ".mp4");
        mediaUtils.setSurfaceView(surfaceView);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //timer和task最好在onResume()方法中初始化，否则从VideoSaveActivity返回到该页面时task已经执行过了。
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                //这里可以执行定时非UI操作
                long endTime = System.currentTimeMillis();
                //默认超过10秒就自动跳转至VideoSaveActivity
                if (endTime - startTime >= 10 * 1000){
                    System.out.print("endTime:");
                    System.out.println(endTime);
                    timer.cancel();
                    startActivity(new Intent(VideoRecordActivity.this, VideoSaveActivity.class));
                }
            }
        };
        startTime = System.currentTimeMillis();
        startTimer();
    }

    //当VideoSaveActivity覆盖VideoRecordActivity时，会回调onPause()方法，将VideoRecordActivity的数据存起来
    @Override
    protected void onPause() {
        super.onPause();
        mediaUtils.stopRecordSave();
    }

    //如果想让VideoRecordActivity不显示的话，回调onStop()方法；但如果想让它重新回到前台的话，回调onResume()方法
    @Override
    protected void onStop() {
        super.onStop();
        //mediaUtils.stopRecordSave();
    }

    //处理UI操作
    private Handler handler = new Handler(){

    };

    private void startTimer(){
        timer.schedule(task, 1000, 1000);
    }
}
