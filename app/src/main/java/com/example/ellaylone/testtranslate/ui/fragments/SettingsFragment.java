package com.example.ellaylone.testtranslate.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.toolbar.ToolBarSimple;

/**
 * Created by ellaylone on 16.04.17.
 */

public class SettingsFragment extends Fragment {
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_category_settings, container, false);

        setupToolBar();

        return view;
    }

    /**
     * Setup tool bar for fragment
     */
    private void setupToolBar() {
        ToolBarSimple toolBar = (ToolBarSimple) view.findViewById(R.id.toolbar);
        toolBar.setToolbarTitle(getString(R.string.fragment_category_settings));
        toolBar.hideToolbarBackIcon();
    }
}
