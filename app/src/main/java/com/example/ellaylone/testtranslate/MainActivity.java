package com.example.ellaylone.testtranslate;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupBottomNavigation();
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
