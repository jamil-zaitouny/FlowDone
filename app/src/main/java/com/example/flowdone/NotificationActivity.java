package com.example.flowdone;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

class NotificationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
        //play video on notification
        VideoView videoView = findViewById(R.id.videoView);
        String videPath = "android.resource://" + getPackageName() + "/" + R.raw.readvid;
        Uri uri = Uri.parse(videPath);
        videoView.setVideoURI(uri);

        MediaController mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);
    }
}
