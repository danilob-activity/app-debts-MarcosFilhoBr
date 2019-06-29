package com.example.danilo.appdebts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BD = "debts.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context, BD, null, DB_VERSION);
    }
//    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
//        super(context, name, factory, version);
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(ScriptDLL.createTableCategory());
        sqLiteDatabase.execSQL(ScriptDLL.createTableDividas());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
