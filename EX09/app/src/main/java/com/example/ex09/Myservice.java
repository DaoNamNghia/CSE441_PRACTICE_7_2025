package com.example.ex09;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class Myservice extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        MediaPlayer mymedia = MediaPlayer.create(Myservice.this, R.raw.class.getModifiers());
        mymedia.setLooping(true);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        MediaPlayer mymeida = null;
        MediaPlayer mymedia = null;
        if (mymeida.isPlaying())
            mymedia.stop();
        else
            mymeida.start();
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
