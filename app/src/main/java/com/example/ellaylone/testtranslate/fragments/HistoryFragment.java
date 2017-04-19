package com.example.ellaylone.testtranslate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.SwipeViewPager;
import com.example.ellaylone.testtranslate.adapters.HistoryAdapter;
import com.example.ellaylone.testtranslate.toolbar.ToolBarHistory;

/**
 * Created by ellaylone on 16.04.17.
 */

public class HistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_history, container, false);

        ToolBarHistory toolBar = (ToolBarHistory) view.findViewById(R.id.toolbar);
        toolBar.hideToolbarBackIcon();
        toolBar.hideToolbarDeleteIcon();

        SwipeViewPager swipeViewPager = (SwipeViewPager) view.findViewById(R.id.history_pager);

        HistoryAdapter historyAdapter = new HistoryAdapter(getContext(), getChildFragmentManager());
        swipeViewPager.setOffscreenPageLimit(2);
        swipeViewPager.setAdapter(historyAdapter);
        swipeViewPager.setEnabled(false);
        swipeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.v("change", "scroll");
            }

            @Override
            public void onPageSelected(int position) {
                Log.v("change", "scroll");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.v("change", "scroll");
            }
        });

        toolBar.setupTabsWithPager(swipeViewPager);

        return view;
    }
}
