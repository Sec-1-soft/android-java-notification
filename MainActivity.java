package com.sahanhus.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "0";
    private final String CHANNEL_NAME = "demo";

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(view -> notification());
    }

    private void notificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("Bildirim kanalı");
            NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
            if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                manager.createNotificationChannel(notificationChannel);
            }
        }

    }

    private void notification() {
        notificationChannel();

        Intent intent_start = new Intent(this,Receiver.class).putExtra("KEY_1","START");
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent_start = PendingIntent.getBroadcast(this, 0, intent_start, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent_stop = new Intent(this,Receiver.class).putExtra("KEY_2","STOP");
        PendingIntent pendingIntent_stop = PendingIntent.getBroadcast(this,1,intent_stop,PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Bildiri")
                .setContentText("Gövde kısmı")
                .addAction(R.drawable.ic_launcher_foreground,"Stop",pendingIntent_stop)
                .addAction(R.drawable.ic_launcher_foreground, "Start", pendingIntent_start)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        managerCompat.notify(1, builder.build());

    }
}