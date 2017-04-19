package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.example.ellaylone.testtranslate.adapters.CategoryAdapter;

public class MainActivity extends AppCompatActivity {
    private LinearLayout root;
    private TabLayout tabLayout;
    private SwipeViewPager swipeViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

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
}
