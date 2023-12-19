package com.sahanhus.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String CHANNEL_ID = "0";
    private final String CHANNEL_NAME = "demo";

    private PlayMp3 playMp3 = new PlayMp3();

    private BroadcastReceiver receiver;


    int i = 0;

    MediaPlayer mediaPlayer;
    String time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notification();

        receiver = new BroadcastReceiver() {  //Diğer Receiverdaki olayı dinle
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction() != null) {
                    switch (intent.getAction()) {
                        case "START":
                            // Burada "START" action'ına göre işlemleri gerçekleştir
                            Toast.makeText(context, "START broadcast received", Toast.LENGTH_SHORT).show();
                            if(!playMp3.isPlaying){
                                playMp3.play("https://www.singlix.com/mp3/Ahmet_Kaya/Kum_Gibi.mp3");
                                time = String.valueOf(playMp3.duration());
                                notification();
                            }
                            break;
                        case "STOP":
                            // Burada "STOP" action'ına göre işlemleri gerçekleştir
                            Toast.makeText(context, "STOP broadcast received", Toast.LENGTH_SHORT).show();
                            if(playMp3.isPlaying){
                                playMp3.stop();
                            }
                            break;
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(); //Gelen inten methodlarını filtrele
        filter.addAction("START"); //Start methodu
        filter.addAction("STOP"); //Stop methodu
        registerReceiver(receiver, filter); //filtreleri kayıt et
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
                .setContentText(time)
                .setShowWhen(false)
                .addAction(R.drawable.button,"Stop",pendingIntent_stop)
                .addAction(R.drawable.button, "Start", pendingIntent_start)
                .setColor(ContextCompat.getColor(this, R.color.purple_200)) // Renk kaynağını belirtin
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.big))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        managerCompat.notify(1, builder.build());

    }


}