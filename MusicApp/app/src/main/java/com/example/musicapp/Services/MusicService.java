package com.example.musicapp.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.example.musicapp.Repo.MusicRepo;

import java.util.ArrayList;

public class MusicService extends Service {

    private final IBinder binder = new MusicBinder();

    public class MusicBinder extends Binder {
        public MusicService getService() {
            // Return this instance of LocalService so clients can call public methods
            return MusicService.this;
        }
    }

    MediaPlayer mediaPlayer;
    private ArrayList <Integer> songs = new MusicRepo().songs;
    int currentIndex = 0;
    int currentTime = 0;

    @Override
    public void onCreate() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void playSong(){
        mediaPlayer = MediaPlayer.create(getApplicationContext(), songs.get(currentIndex));
        mediaPlayer.seekTo(currentTime);
        mediaPlayer.start();

        LOG_TOAST("Playing new Song");
    }

    public void pauseSong(){
        mediaPlayer.pause();
        currentTime = mediaPlayer.getCurrentPosition();
        LOG_TOAST("Pause Song");
    }

    public void nextSong (){
        mediaPlayer.stop();
        currentTime = 0;
        if (currentIndex >= songs.size() - 1){
            currentIndex = 0;
        }
        else {
            currentIndex += 1;
        }
        LOG_TOAST("Next Song");
    }

    public void prevSong(){
        mediaPlayer.stop();
        currentTime = 0;
        if (currentIndex  <= 0){
            currentIndex = songs.size() - 1;
        }
        else {
            currentIndex -= 1;
        }
        LOG_TOAST("Prev Song");
    }

    public String getNameCurrentSong(){
        return new MusicRepo().getNameSongById(currentIndex);
    }

    public void LOG_TOAST(String content){
        Toast.makeText(getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

}