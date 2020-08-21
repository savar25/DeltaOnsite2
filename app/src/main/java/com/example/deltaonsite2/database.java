package com.example.deltaonsite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class database extends SQLiteOpenHelper {
    public static final String MESSAGE = "message";
    public static final String NUM = "num";
    public static final String TABLE_NAME = "table2";
    public static final String ID = "id";
    public static final String hour = "hour";
    public static final String min = "min";
    private static final String TAG = "database";
    public static final String DATABASE_NAME = "db2.db";

    public database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+TABLE_NAME+ "("+MESSAGE+" varchar,"+NUM+" varchar,"+hour+" integer,"+min+" integer,"+ID+" integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addEntry(ScheduledModel scheduledModel){
     SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put(MESSAGE,scheduledModel.getMessage());
        contentValues.put(NUM,scheduledModel.getPnum());
        contentValues.put(ID,scheduledModel.getID());
        contentValues.put(hour,scheduledModel.getHr());
        contentValues.put(min,scheduledModel.getMin());

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
        return true;
    }

    public ArrayList<ScheduledModel> getVals(){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;

        ArrayList<ScheduledModel> models=new ArrayList<>();
        Cursor c=sqLiteDatabase.rawQuery(query,null);
        while(c.moveToNext()){
            models.add(new ScheduledModel(c.getInt(c.getColumnIndex(ID)),c.getString(c.getColumnIndex(MESSAGE)),c.getString(c.getColumnIndex(NUM)),c.getInt(c.getColumnIndex(hour)),c.getInt(c.getColumnIndex(min))));
        }
        return models;
    }

    public boolean delEntry(Integer id){
        SQLiteDatabase sqLiteDatabase=this.getWritableDatabase();
        if (check(id)) {
            sqLiteDatabase.delete(TABLE_NAME,ID+" = "+String.valueOf(id),null);
        }
        sqLiteDatabase.close();
        return true;
    }

    public boolean check(Integer id){
        SQLiteDatabase sqLiteDatabase=this.getReadableDatabase();
        String query="SELECT * FROM "+TABLE_NAME;
        Cursor c=sqLiteDatabase.rawQuery(query,null);
        while(c.moveToNext()){
            if(c.getInt(c.getColumnIndex(ID))==id){
                return  true;
            }
        }
        return false;
    }
}
