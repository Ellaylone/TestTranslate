package com.example.ellaylone.testtranslate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
    private View view;
    private ToolBarHistory toolBar;
    private HistoryAdapter historyAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_history, container, false);

        setupToolBar();

        return view;
    }

    /**
     * Setup tool bar for fragment
     */
    private void setupToolBar() {
        toolBar = (ToolBarHistory) view.findViewById(R.id.toolbar);
        toolBar.hideToolbarBackIcon();
        toolBar.hideToolbarDeleteIcon();

        SwipeViewPager swipeViewPager = (SwipeViewPager) view.findViewById(R.id.history_pager);

        historyAdapter = new HistoryAdapter(getContext(), getChildFragmentManager());
        swipeViewPager.setAdapter(historyAdapter);
        swipeViewPager.setEnabled(false);

        toolBar.setupTabsWithPager(swipeViewPager);

        toolBar.getTabs().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.v("tab", "selected");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Log.v("tab", "unselected");
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Log.v("tab", "reselected");
            }
        });
    }
}
