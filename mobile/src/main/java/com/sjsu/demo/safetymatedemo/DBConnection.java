package com.sjsu.demo.safetymatedemo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import java.util.List;
import java.util.ArrayList;
import android.util.Log;

public class DBConnection extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="USER_INFO3";
    public static final String TAG = "DBConnection";

    public DBConnection(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    @Override

    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE CONTACTS_TABLE (_id INTEGER PRIMARY KEY AUTOINCREMENT,phoneIndex TEXT, displayName TEXT, phoneNumber TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS CONTACTS_TABLE");
        onCreate(db);

    }

    public void addContactsToDB(String phoneIndex, String displayName, String phoneNumber)
    {
        ContentValues values=new ContentValues(1);

        values.put("phoneIndex",phoneIndex);
        values.put("displayName",displayName);
        values.put("phoneNumber", phoneNumber);
        getWritableDatabase().insert("CONTACTS_TABLE", "name", values);
    }

    public Cursor getContactsForLV()
    {
        List<String> contacts=new ArrayList<String>();

        String selectQuery = "SELECT _id,displayName, phoneNumber FROM CONTACTS_TABLE";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor!=null){
            cursor.moveToFirst();
        }
        return cursor;

    }

    public List<String> getContacts()
    {
        List<String> contacts=new ArrayList<String>();

        String selectQuery = "SELECT phoneNumber FROM CONTACTS_TABLE";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                contacts.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return contacts;
    }


}