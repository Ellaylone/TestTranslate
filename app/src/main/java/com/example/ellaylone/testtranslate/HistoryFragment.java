package com.example.ellaylone.testtranslate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by ellaylone on 16.04.17.
 */

public class HistoryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_history, container, false);

        SimpleToolBar toolBar = (SimpleToolBar) view.findViewById(R.id.toolbar);
        toolBar.setToolbarTitle("История");
        toolBar.hideToolbarBackIcon();

        return view;
    }
}
