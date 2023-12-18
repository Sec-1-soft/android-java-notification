package com.sahanhus.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class Receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       String intent_data_2 = intent.getStringExtra("KEY_2");
        String intent_data_1 = intent.getStringExtra("KEY_1");
       if(intent_data_1 != null){
          Toast.makeText(
                  context,
                  intent_data_1,
                  Toast.LENGTH_SHORT
          ).show();


       } else if (intent_data_2 != null) {
           Toast.makeText(
                   context,
                   intent_data_2,
                   Toast.LENGTH_SHORT
           ).show();
       }
    }
}
