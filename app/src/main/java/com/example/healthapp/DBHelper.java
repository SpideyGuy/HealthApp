package com.example.healthapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "HealthApp.db";
    public static final String USERS_TABLE_NAME = "ha_users";
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_FULLNAME = "fullname";
    public static final String USERS_COLUMN_DOB = "dob";
    public static final String USERS_COLUMN_GENDER = "gender";
    public static final String USERS_COLUMN_CONTACT = "contact";
    public static final String USERS_COLUMN_EMAIL = "email";
    public static final String USERS_COLUMN_PASSWORD = "password";
    private HashMap hp;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table " + USERS_TABLE_NAME +
                        "(id integer primary key, fullname text,dob text,gender text, contact text,email text, password text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + USERS_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String fullname, String dob, String gender, String contact, String email, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_FULLNAME,fullname);
        contentValues.put(USERS_COLUMN_DOB,dob);
        contentValues.put(USERS_COLUMN_GENDER,gender);
        contentValues.put(USERS_COLUMN_CONTACT,contact);
        contentValues.put(USERS_COLUMN_EMAIL,email);
        contentValues.put(USERS_COLUMN_PASSWORD,password);
        database.insert(USERS_TABLE_NAME,null,contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE id=" + id,null);
        return res;
    }

    public Cursor getUser(String email, String password){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor res = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + " WHERE email= '" + email + "' AND password='" + password + "'",null);
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase database = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(database, USERS_TABLE_NAME);
        return numRows;
    }

    public boolean updateUser(int id, String fullname, String dob, String gender, String contact, String email, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_FULLNAME,fullname);
        contentValues.put(USERS_COLUMN_DOB,dob);
        contentValues.put(USERS_COLUMN_GENDER,gender);
        contentValues.put(USERS_COLUMN_CONTACT,contact);
        contentValues.put(USERS_COLUMN_EMAIL,email);
        contentValues.put(USERS_COLUMN_PASSWORD,password);
        database.update(USERS_TABLE_NAME, contentValues, "id=?", new String[] {Integer.toString(id)});
        return true;
    }

    public int deleteUser(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(USERS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

}
