package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ellaylone on 19.04.17.
 */

public class DbHelper extends SQLiteOpenHelper {
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE LANGS (_id INTEGER PRIMARY KEY AUTOINCREMENT, LANG_CODE TEXT, LANG_NAME TEXT);");
        db.execSQL("CREATE TABLE ACTIVE_LANGS (_id INTEGER PRIMARY KEY AUTOINCREMENT, LANG_CODE TEXT, LANG_TYPE INTEGER);");
        db.execSQL("CREATE TABLE HISTORY (_id INTEGER PRIMARY KEY AUTOINCREMENT, SOURCE_TEXT TEXT, TRANSLATED_TEXT TEXT, LANG_CODE_SOURCE TEXT, LANG_CODE_TRANSLATION TEXT);");
        db.execSQL("CREATE TABLE FAVOURITES (_id INTEGER PRIMARY KEY AUTOINCREMENT, SOURCE_TEXT TEXT, TRANSLATED_TEXT TEXT, LANG_CODE_SOURCE TEXT, LANG_CODE_TRANSLATION TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS LANGS;");
        db.execSQL("DROP TABLE IF EXISTS ACIVE_LANGS;");
        db.execSQL("DROP TABLE IF EXISTS HISTORY;");
        db.execSQL("DROP TABLE IF EXISTS FAVOURITES;");
        onCreate(db);
    }
}
