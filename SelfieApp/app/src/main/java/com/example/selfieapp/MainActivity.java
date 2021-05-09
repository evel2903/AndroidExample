package com.example.selfieapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageButton camera;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        camera = findViewById(R.id.camera);
        imageView = findViewById(R.id.imgView);

        bindCamera();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Selfie App","Open Camera", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Hey, Take the picture for you now!");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                sendNotification();
            }
        },0,1000 * 60 * 60); //after 1 hour

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 100);
            }
        });


    }

    public void bindCamera(){
        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                        Manifest.permission.CAMERA
                    },
                    100
            );
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 100) {
            Bitmap bitmapImg = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmapImg);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void sendNotification(){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "Selfie App")
                .setSmallIcon(R.drawable.ic_baseline_camera_alt_24)
                .setContentTitle("Open Camera")
                .setContentText("Hey, Take the picture for you now!")
                .setAutoCancel(true);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);

        notificationManagerCompat.notify(0, builder.build());

    }
}