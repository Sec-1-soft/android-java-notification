package com.sahanhus.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       String intent_data_2 = intent.getStringExtra("KEY_2");
        String intent_data_1 = intent.getStringExtra("KEY_1");
       if(intent_data_1 != null){
          Intent intent1 = new Intent("START");   //Methodu gönder
          context.sendBroadcast(intent1);

       } else if (intent_data_2 != null) {
           Intent intent2 = new Intent("STOP"); //Stop methodu gönder
           context.sendBroadcast(intent2);

       }
    }
}
