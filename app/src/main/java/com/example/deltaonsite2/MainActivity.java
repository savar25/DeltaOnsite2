package com.example.deltaonsite2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SubscriptionManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        checkPermission();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)+
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED){
            Intent intent=new Intent(MainActivity.this,base.class);
            startActivity(intent);
        }


    }

    public void checkPermission(){
        {
            if (!(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)+
                    ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)== PackageManager.PERMISSION_GRANTED)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS)&&
                ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {
                    new AlertDialog.Builder(this)
                            .setTitle("Permission Needed")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_CONTACTS}, 1);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    finish();
                                    System.exit(0);
                                }
                            }).create().show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_CONTACTS}, 1);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 &&
                grantResults[0] + grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(MainActivity.this,base.class);
                    startActivity(intent);
                }
            },2000);


        }
    }
}