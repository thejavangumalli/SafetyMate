package com.safetymate.safetymate;


import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBConnection extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="SAFETYMATE_HEART_RATE";
    public static final String TAG = "DBConnection";

    public DBConnection(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        //To store the user's emergency contacts
        database.execSQL("CREATE TABLE CONTACTS_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, phoneNumber TEXT)");
        //To store the user's last known location
        database.execSQL("CREATE TABLE LOCATION_INFO_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, userLocation TEXT)");
        //To store the heart rate for a particular activity
        database.execSQL("CREATE TABLE HEART_RATE_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT, hrThreshold REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS CONTACTS_TABLE");
        db.execSQL("DROP TABLE IF EXISTS LOCATION_INFO_TABLE");
        db.execSQL("DROP TABLE IF EXISTS HEART_RATE_TABLE");
        onCreate(db);

    }

    public void addLocation(String loca)
    {
        System.out.println(loca+"jaf");
        ContentValues values=new ContentValues(1);

        values.put("userLocation", loca);
        getWritableDatabase().insert("LOCATION_INFO_TABLE", "name", values);
    }

    public void addContacts(String phoneNumber)
    {
        ContentValues values=new ContentValues(1);

        values.put("phoneNumber", phoneNumber);
        getWritableDatabase().insert("CONTACTS_TABLE", "name", values);
    }

    public void addThreshold(int threshold)
    {
        ContentValues values=new ContentValues(1);
        values.put("hrThreshold", threshold);
        getWritableDatabase().insert("HEART_RATE_TABLE", "name", values);

    }

    public void getThreshold()
    {
        String selectQuery = "SELECT hrThreshold FROM HEART_RATE_TABLE";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
            cursor.moveToFirst();
        Log.d(TAG,cursor.getString(0));

    }

    public List<String> getContacts()
    {
        List<String> contacts=new ArrayList<String>();

        String selectQuery = "SELECT * FROM CONTACTS_TABLE";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                contacts.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }
        return contacts;
    }


    public List<String> getLocation()
    {
        List<String> locationInfo=new ArrayList<String>();

        String selectQuery = "SELECT  * FROM LOCATION_INFO_TABLE WHERE _id=(SELECT MAX(_id) FROM LOCATION_INFO_TABLE)";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                locationInfo.add(cursor.getString(1));
                Log.d("TAG",cursor.getString(1));
            } while (cursor.moveToNext());

        }

        return locationInfo;
    }


}