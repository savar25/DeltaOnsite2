package com.example.deltaonsite2;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.view.MenuInflater;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class reciever extends BroadcastReceiver {
    private static int REQUEST_CODE =0 ;


    @Override
    public void onReceive(Context context, Intent intent) {
        String phNum=intent.getStringExtra("number");
        String message=intent.getStringExtra("message");
        REQUEST_CODE=intent.getIntExtra("request",0);

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            SmsManager smsManager=null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                smsManager = SmsManager.getSmsManagerForSubscriptionId(SubscriptionManager.getDefaultSubscriptionId());
            }
            smsManager.sendTextMessage(phNum,null,message,null,null);
            Toast.makeText(context, "Message Sent ", Toast.LENGTH_SHORT).show();
        }
        for(int i=0;i<base.scheduledModels.size();i++){
            if(base.scheduledModels.get(i).getID()==REQUEST_CODE){
                base.scheduledModels.remove(i);
                base.adapter.notifyItemRemoved(i);
            }
        }

        MediaPlayer mediaPlayer=MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();




    }

}
