package com.example.deltaonsite2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class base extends AppCompatActivity {

    private static final int PICK_CONTACT =2 ;
    private static Integer REQUEST_CODE=0;
    FloatingActionButton fab;
    public static ArrayList<ScheduledModel> scheduledModels=new ArrayList<>();
    RecyclerView recyclerView;
    database db;
    public static rec_viewAdapter adapter;
    private static Context context;
    static NotificationManagerCompat notificationManagerCompat;
    EditText message,ph;
    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db=new database(this);
        recyclerView=findViewById(R.id.recyclerView);
        fab=findViewById(R.id.fab);
        notificationManagerCompat= NotificationManagerCompat.from(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater li = LayoutInflater.from(base.this);
                final View promptsView = li.inflate(R.layout.time_setter, null);


                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        base.this);

                alertDialogBuilder.setView(promptsView);

                 message=promptsView.findViewById(R.id.msg_alert);
                ph=promptsView.findViewById(R.id.ph_alert);
                timePicker=promptsView.findViewById(R.id.tp);
                timePicker.setIs24HourView(true);
                ImageView call=promptsView.findViewById(R.id.contact_choose);
                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        startActivityForResult(intent, PICK_CONTACT);
                    }
                });

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("Set Message", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                               scheduledModels.add(new ScheduledModel(REQUEST_CODE,message.getText().toString(),ph.getText().toString(),timePicker.getCurrentHour(),timePicker.getCurrentMinute()));
                                onSend(new View(base.this),message.getText().toString(),ph.getText().toString(),timePicker.getCurrentHour(),timePicker.getCurrentMinute());

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();

            }
        });








    }

    public void onSend(View v,String msg,String num,Integer hr,Integer min){


        if (num == null || msg == null||num.length()==0||msg.length()==0) {
            return;

        }

        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,hr);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);

        setScheduler(calendar,num,msg);

    }

    private void setScheduler(Calendar calendar,String ph,String msg) {
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent=new Intent(this,service.class);

        intent.putExtra("request",REQUEST_CODE);
        intent.putExtra("message",msg);
        intent.putExtra("number",ph);
        PendingIntent pendingIntent= null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getForegroundService(this,REQUEST_CODE,intent,0);
        }else{
            pendingIntent = PendingIntent.getService(this,REQUEST_CODE,intent,0);
        }


        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),pendingIntent);
        Toast.makeText(this, "Message Set", Toast.LENGTH_SHORT).show();
        REQUEST_CODE++;


        adapter=new rec_viewAdapter(scheduledModels,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        context=this;
    }

   /* public static void sendOnChannel(View view){

        notificationManagerCompat.notify(1,notification);
    }*/

    @Override public void onActivityResult(int reqCode, int resultCode, Intent data){ super.onActivityResult(reqCode, resultCode, data);

        switch(reqCode)
        {
            case (PICK_CONTACT):
                if (resultCode == RESULT_OK)
                {
                    Uri contactData = data.getData();
                    Cursor c = managedQuery(contactData, null, null, null, null);
                    if (c.moveToFirst())
                    {
                        String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                        String hasPhone =
                                c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                        if (hasPhone.equalsIgnoreCase("1"))
                        {
                            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                            phones.moveToFirst();
                            String cNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            // Toast.makeText(getApplicationContext(), cNumber, Toast.LENGTH_SHORT).show();
                            ph.setText(cNumber);
                        }
                    }
                }
        }
    }

}