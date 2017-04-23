package com.example.ellaylone.testtranslate;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.example.ellaylone.testtranslate.db.DbHelper;
import com.example.ellaylone.testtranslate.db.DbProvider;

/**
 * Created by ellaylone on 19.04.17.
 */

public class TranslateApp extends Application {
    final int DATABASE_VERSION = 10;
    private SQLiteDatabase db;
    private TranslationItem currentHistory;
    private boolean isCurrentHistoryLoaded = false;

    @Override
    public void onCreate() {
        super.onCreate();

        setupDb();

        getHistory();
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

    private void getHistory() {
        Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, null, null, null, null, "_id DESC", "1");

        if (c.getCount() > 0) {
            c.moveToFirst();
            currentHistory = new TranslationItem(
                    c.getString(c.getColumnIndex("SOURCE_TEXT")),
                    c.getString(c.getColumnIndex("TRANSLATED_TEXT")),
                    c.getString(c.getColumnIndex("LANG_CODE_SOURCE")),
                    c.getString(c.getColumnIndex("LANG_CODE_TRANSLATION")),
                    c.getInt(c.getColumnIndex("_id")),
                    c.getInt(c.getColumnIndex("IS_FAV"))
            );
            c.close();
        }
    }

    public TranslationItem getCurrentHistory() {
        return currentHistory;
    }

    public boolean isCurrentHistoryLoaded() {
        return isCurrentHistoryLoaded;
    }

    public void setCurrentHistoryLoaded(boolean currentHistoryLoaded) {
        isCurrentHistoryLoaded = currentHistoryLoaded;
    }
}
