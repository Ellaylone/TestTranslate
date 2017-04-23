package com.example.ellaylone.testtranslate.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.ui.fragments.HistoryFragment;
import com.example.ellaylone.testtranslate.ui.fragments.SettingsFragment;
import com.example.ellaylone.testtranslate.ui.fragments.TranslationFragment;

/**
 * Created by ellaylone on 16.04.17.
 */

public class CategoryAdapter extends FragmentPagerAdapter {

    private final int TABS_COUNT = 2;
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TranslationFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new SettingsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.fragment_category_translation);
            case 1:
                return mContext.getString(R.string.fragment_category_history);
            case 2:
                return mContext.getString(R.string.fragment_category_settings);
            default:
                return "";
        }
    }
}