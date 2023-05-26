package com.example.todo_list;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import java.util.List;

public class PortalDB extends SQLiteOpenHelper {
    private static String databaseName = "PortalDb";
    SQLiteDatabase PortalDb;

    public PortalDB(@Nullable Context context) {
        super(context, databaseName, null, 13);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create DB");
        db.execSQL("create table User(UserName text primary key , password text not null)");
        db.execSQL("create table Task (title text,status text,priority integer,UserName text,Foreign key(UserName) References User(UserName) )");
    }

    public String validateData( String userName, String password) {
        PortalDb = getReadableDatabase();
        String[] rowdetails = {"UserName"};
        String[] args = {userName, password};
        String UserName = "Not Found";

        Cursor c = PortalDb.query("User", rowdetails, "UserName=? and password = ?", args, null, null, null);

        if (c != null) {
            c.moveToFirst();
            if (c.getCount() > 0) {
                UserName = c.getString(0);
            }
        }
        PortalDb.close();

        return UserName;
    }

    public String addNewUser(User user) {
        PortalDb = getReadableDatabase();
        Cursor c = PortalDb.rawQuery("Select * from User where UserName=? ", new String[]{user.getUsername()});
        if (c.getCount() > 0) {
            return "Username already exist";
        }

        ContentValues row = new ContentValues();
        row.put("UserName", user.getUsername());
        row.put("password", user.getPassword());
        PortalDb = getWritableDatabase();
        PortalDb.insert("User", null, row);
        PortalDb.close();
        return "Registered Successfully";
    }

    public static String login( PortalDB db, String username, String password) {

        String validate = String.valueOf(db.validateData( username, password));
        if (validate == "Not Found") {
            System.out.println("not found");
            return "Not Found";
        }
        System.out.println("validate  " + validate);
        return validate;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS Task");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }
}
