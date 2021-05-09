package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp.Services.MusicService;

public class MainActivity extends AppCompatActivity {

    MusicService musicService;
    boolean mBound = false;
    boolean isPause = false;
    ImageButton prev, play, next;
    TextView songTitle;

    public void init(){
        prev = findViewById(R.id.btn_prev);
        play = findViewById(R.id.btn_start_pause);
        next = findViewById(R.id.btn_next);
        songTitle = findViewById(R.id.txt_name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBound){
                    controlMusic();
                }


            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound){
                    musicService.nextSong();
                    isPause = false;
                    controlMusic();
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound){
                    musicService.prevSong();
                    isPause = false;
                    controlMusic();
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
        mBound = false;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private void controlMusic (){
        songTitle.setText(musicService.getNameCurrentSong());
        if (isPause == true){
            musicService.pauseSong();
            play.setImageResource(R.drawable.ic_baseline_play_arrow_24);
            isPause = false;
        }
        else {
            musicService.playSong();
            play.setImageResource(R.drawable.ic_baseline_pause_24);
            isPause = true;
        }
    }



}