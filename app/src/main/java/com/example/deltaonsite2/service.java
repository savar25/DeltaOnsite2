package com.example.deltaonsite2;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

public class service extends Service {
    private static int REQUEST_CODE =0 ;
    public static final String stepCover="channel1";
    Notification notification;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        String phNum=intent.getStringExtra("number");
        String message=intent.getStringExtra("message");
        REQUEST_CODE=intent.getIntExtra("request",0);





        startForeground(1,notification);

        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            SmsManager smsManager=null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                smsManager = SmsManager.getSmsManagerForSubscriptionId(SubscriptionManager.getDefaultSubscriptionId());
            }
            smsManager.sendTextMessage(phNum,null,message,null,null);
            Toast.makeText(getApplicationContext(), "Message Sent ", Toast.LENGTH_SHORT).show();
            startForeground(1,notification);


        }
        for(int i=0;i<base.scheduledModels.size();i++){
            if(base.scheduledModels.get(i).getID()==REQUEST_CODE){
                base.scheduledModels.remove(i);
                base.adapter.notifyItemRemoved(i);
            }

        }



        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel stepCheck = new NotificationChannel(stepCover,"Message Notice", NotificationManager.IMPORTANCE_HIGH);
            stepCheck.setDescription("Trial for channel");
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(stepCheck);

             notification=new NotificationCompat.Builder(getApplicationContext(),stepCover)
                    .setSmallIcon(android.R.drawable.sym_action_chat)
                    .setContentTitle("Message Sent")
                    .setContentText("Message Successfully Sent")
                    .build();startForeground(1,notification);

        }
    }




    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
    }
}
