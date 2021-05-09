package com.example.musicapp.Repo;

import com.example.musicapp.R;

import java.util.ArrayList;

public class MusicRepo {
    public ArrayList <Integer> songs = new ArrayList();

    public MusicRepo() {
        addDefaultSongs();
    }

    private void addDefaultSongs (){
        songs.add(R.raw.gapnhungkoolai);
        songs.add(R.raw.roinguoithuongcunghoanguoidung);
        songs.add(R.raw.tancungnoinho);
        songs.add(R.raw.xinloividayeunhau);
        songs.add(R.raw.yeuthuongngaydo);
    }
    public String getNameSongById(int id){
        switch (id){
            case 0:
                return "Gặp nhưng không ở lại";
            case 1:
                return "Rồi Người thương bỗng hóa người dưng";
            case 2:
                return "Tận cùng nỗi nhớ";
            case 3:
                return "Xin lỗi vì đã yêu nhau";
            case 4:
                return "Yêu thương ngày đó";
            default:
                return "Name";
        }
    }
}
