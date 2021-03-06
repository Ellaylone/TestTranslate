package com.example.ellaylone.testtranslate.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.adapters.HistoryAdapter;
import com.example.ellaylone.testtranslate.toolbar.ToolBarHistory;
import com.example.ellaylone.testtranslate.viewPager.SwipeViewPager;

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

        final SwipeViewPager swipeViewPager = (SwipeViewPager) view.findViewById(R.id.history_pager);

        historyAdapter = new HistoryAdapter(getContext(), getChildFragmentManager());
        swipeViewPager.setAdapter(historyAdapter);
        swipeViewPager.setEnabled(false);

        toolBar.setupTabsWithPager(swipeViewPager);

        toolBar.getTabs().addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                HistoryFavoritesFragment historyFavoritesFragment = (HistoryFavoritesFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.history_pager + ":" + tab.getPosition());
                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                ft.detach(historyFavoritesFragment);
                ft.attach(historyFavoritesFragment);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public ToolBarHistory getToolBar() {
        return toolBar;
    }

    public void loadCurrentHistory() {
        HistoryFavoritesFragment historyFavoritesFragment = (HistoryFavoritesFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.history_pager + ":" + 0);
        historyFavoritesFragment.reload();
        historyFavoritesFragment = (HistoryFavoritesFragment) getChildFragmentManager().findFragmentByTag("android:switcher:" + R.id.history_pager + ":" + 1);
        historyFavoritesFragment.reload();
    }
}
