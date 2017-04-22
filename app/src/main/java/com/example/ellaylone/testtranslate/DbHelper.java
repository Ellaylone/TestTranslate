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
        db.execSQL("CREATE TABLE " + DbProvider.LANGS_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, LANG_CODE TEXT, LANG_NAME TEXT);");
        db.execSQL("CREATE TABLE " + DbProvider.ACTIVE_LANGS_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, LANG_CODE TEXT, LANG_TYPE INTEGER);");
        db.execSQL("CREATE TABLE " + DbProvider.HISTORY_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, SOURCE_TEXT TEXT, TRANSLATED_TEXT TEXT, LANG_CODE_SOURCE TEXT, LANG_CODE_TRANSLATION TEXT, IS_FAV INTEGER);");
        db.execSQL("CREATE TABLE " + DbProvider.FAVOURITES_TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, SOURCE_TEXT TEXT, TRANSLATED_TEXT TEXT, LANG_CODE_SOURCE TEXT, LANG_CODE_TRANSLATION TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbProvider.LANGS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + DbProvider.ACTIVE_LANGS_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + DbProvider.HISTORY_TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + DbProvider.FAVOURITES_TABLE_NAME +";");
        onCreate(db);
    }
}
