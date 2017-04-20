package com.example.ellaylone.testtranslate;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

/**
 * Created by ellaylone on 19.04.17.
 */

public class TranslateApp extends Application {
    final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();

        setupDb();
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    private void setupDb() {
        DbHelper dbHelper = new DbHelper(this, "translate", null, DATABASE_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
    }
}
