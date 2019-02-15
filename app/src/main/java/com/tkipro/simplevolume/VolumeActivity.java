package com.tkipro.simplevolume;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class VolumeActivity extends AppCompatActivity {
    AudioManager audioManager;
    SeekBar seekBar;
    Button button;
    Button button2;
    public static final int notiID = 1111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume);


        seekBar = findViewById(R.id.seekBar);

        button = findViewById(R.id.btn_vol);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(VolumeActivity.this, MyService2.class);
//                startService(i);

                sendNotiCustom();
//                sendNoti();
            }
        });

        button2 = findViewById(R.id.btn2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.cancel(notiID);
            }
        });


        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

/*
//        Media Volume (Music/Video etc…)
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//        Ringtone Volume
        this.setVolumeControlStream(AudioManager.STREAM_RING);
//        Alarm Volume
        this.setVolumeControlStream(AudioManager.STREAM_ALARM);
//        Notification Volume
        this.setVolumeControlStream(AudioManager.STREAM_NOTIFICATION);
//        System Volume
        this.setVolumeControlStream(AudioManager.STREAM_SYSTEM);
//        Voicecall Volume
        this.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
            */

        int nMax = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int nCurrentVolumn = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        seekBar.setMax(nMax);
        seekBar.setProgress(nCurrentVolumn);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }



    public void sendExample() {



    }
    public void sendNotiCustom() {
        // Get the layouts to use in the custom notification
        RemoteViews notificationLayout = new RemoteViews(getPackageName(), R.layout.notification_small);
        RemoteViews notificationLayoutExpanded = new RemoteViews(getPackageName(), R.layout.notification_large);

        Intent leftIntent = new Intent(this, MyService2.class);
        leftIntent.setAction("left");
        notificationLayout.setOnClickPendingIntent(R.id.btn1, PendingIntent.getService(this, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));

        Intent leftIntent2 = new Intent(this, MyService2.class);
        leftIntent2.setAction("right");
        notificationLayout.setOnClickPendingIntent(R.id.btn2, PendingIntent.getService(this, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));


// Apply the layouts to the notification
        Notification customNotification = new NotificationCompat.Builder(this, "channel")
                .setSmallIcon(android.R.drawable.star_on)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(notificationLayout)
                .setCustomBigContentView(notificationLayoutExpanded)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notiID, customNotification);

    }

    public void sendNoti() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "channel");
        Notification notification = mBuilder
                .setAutoCancel(false) // 알림 터치시 반응 후 삭제
                .setSmallIcon(android.R.drawable.star_on)
                .setTicker("title>")
                .setContentTitle("titleEE")
                .setContentText("godyEE1111")
                .setWhen(System.currentTimeMillis())
                .setLights(Color.RED, 3000, 3000)
                .setVibrate(new long[]{100, 1000, 1000, 100, 100, 100, 100, 100})
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(notiID, notification);

    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
                return true;
            case KeyEvent.KEYCODE_BACK:
                return true;
        }
        return false;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {

        AudioManager mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
                return true;

            case KeyEvent.KEYCODE_VOLUME_DOWN:
                mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
                return true;

            case KeyEvent.KEYCODE_BACK:
                this.finish();
                return true;
        }
        return false;
    }
    }

