package com.tkipro.simplevolume;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class MyService2 extends IntentService {

      AudioManager audioManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */


    public MyService2(String name) {
        super("MyService2");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String a = intent.getAction();
        setVolume();

    }

    @Override
    public void onCreate() {
        super.onCreate();
        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);

//        startVolumeService();
        setVolume();
    }



    public void startVolumeService() {
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_small);
        remoteViews.setTextViewText(R.id.btn1, DateUtils.formatDateTime(this, System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME));


        Intent leftIntent = new Intent(this, MyService2.class);
        leftIntent.setAction("left");
        remoteViews.setOnClickPendingIntent(R.id.btn1, PendingIntent.getService(this, 0, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT));


        Notification customNotification = new NotificationCompat.Builder(this, "channel")
                .setAutoCancel(false)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteViews)
                .build();


/*

        Notification customNotification = new NotificationCompat.Builder(this, "channel")
                .setAutoCancel(false)
                .setSmallIcon(android.R.drawable.ic_menu_more)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setContentTitle("tttitle")
                .setContentText("texttt")
                .setContentInfo("info?")
                .build();
*/


        startForeground(VolumeActivity.notiID, customNotification);
    }

    void setVolume(){

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


        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 80, 0);


    }
}
