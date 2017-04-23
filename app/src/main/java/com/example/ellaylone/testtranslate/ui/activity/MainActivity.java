package com.example.ellaylone.testtranslate.ui.activity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.TranslateApp;
import com.example.ellaylone.testtranslate.TranslationItem;
import com.example.ellaylone.testtranslate.adapters.CategoryAdapter;
import com.example.ellaylone.testtranslate.db.DbProvider;
import com.example.ellaylone.testtranslate.viewPager.SwipeViewPager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private LinearLayout root;
    private TabLayout tabLayout;
    private SwipeViewPager swipeViewPager;
    private SQLiteDatabase db;
    private CategoryAdapter categoryAdapter;

    private ArrayList<TranslationItem> listData = new ArrayList<>();

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = ((TranslateApp) getApplicationContext()).getDb();

        setContentView(R.layout.activity_main);

        setupBottomNavigation();

        setupCatchTextAreaFocus();
    }

    /**
     * On root touch catch focus from text area & hide soft keyboard
     *
     * @return boolean false - do nothing on root touch
     */
    private boolean catchFocus() {
        root.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
        return false;
    }

    /**
     * Setup main navigation
     */
    private void setupBottomNavigation() {
        swipeViewPager = (SwipeViewPager) findViewById(R.id.pager);

        categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

        swipeViewPager.setAdapter(categoryAdapter);
        swipeViewPager.setEnabled(false);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(swipeViewPager);
    }

    /**
     * Setup onTouchListener on root to catch focus when text area loses it
     * Setup catchFocus on tab selection in main navigation to remove keyboard on tab switch
     */
    private void setupCatchTextAreaFocus() {
        root = (LinearLayout) findViewById(R.id.main_parent);

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                catchFocus();
                return false;
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                catchFocus();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                catchFocus();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                catchFocus();
            }
        });
    }

    private void setupHistory() {
        Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, null, null, null, null, null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                listData.add(new TranslationItem(
                        c.getString(c.getColumnIndex("SOURCE_TEXT")),
                        c.getString(c.getColumnIndex("TRANSLATED_TEXT")),
                        c.getString(c.getColumnIndex("LANG_CODE_SOURCE")),
                        c.getString(c.getColumnIndex("LANG_CODE_TRANSLATION")),
                        c.getInt(c.getColumnIndex("_id")),
                        c.getInt(c.getColumnIndex("IS_FAV"))
                ));
                c.moveToNext();
            }
            c.close();
        }
    }

    private void setupFavourites() {
        Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, "IS_FAV=1", null, null, null, null);

        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                listData.add(new TranslationItem(
                        c.getString(c.getColumnIndex("SOURCE_TEXT")),
                        c.getString(c.getColumnIndex("TRANSLATED_TEXT")),
                        c.getString(c.getColumnIndex("LANG_CODE_SOURCE")),
                        c.getString(c.getColumnIndex("LANG_CODE_TRANSLATION")),
                        c.getInt(c.getColumnIndex("_id")),
                        1
                ));
                c.moveToNext();
            }
            c.close();
        }
    }
}
