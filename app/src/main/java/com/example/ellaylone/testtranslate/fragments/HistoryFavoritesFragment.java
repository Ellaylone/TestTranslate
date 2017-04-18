package com.example.ellaylone.testtranslate.fragments;


import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ellaylone.testtranslate.R;


public class HistoryFavoritesFragment extends Fragment {
    public HistoryFavoritesFragment() {
    }

    public static HistoryFavoritesFragment getInstance() {
        return new HistoryFavoritesFragment();
    }

    View setupFragment(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(
                R.layout.fragment_category_history, container, false);
        return view;
    }
}