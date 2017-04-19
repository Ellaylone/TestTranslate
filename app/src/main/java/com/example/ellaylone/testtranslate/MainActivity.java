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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();

        final LinearLayout root = (LinearLayout) findViewById(R.id.main_parent);
        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                root.requestFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(root.getWindowToken(), 0);
                return false;
            }
        });
    }

    private void setupBottomNavigation() {
        SwipeViewPager swipeViewPager = (SwipeViewPager) findViewById(R.id.pager);

        CategoryAdapter categoryAdapter = new CategoryAdapter(this, getSupportFragmentManager());

        swipeViewPager.setAdapter(categoryAdapter);
        swipeViewPager.setEnabled(false);

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(swipeViewPager);
    }
}
