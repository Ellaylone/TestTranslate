package com.example.ellaylone.testtranslate.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.fragments.HistoryFavoritesFragment;

/**
 * Created by ellaylone on 16.04.17.
 */
public class HistoryAdapter extends FragmentPagerAdapter {

    private final int TABS_COUNT = 2;
    private Context mContext;

    public HistoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = new HistoryFavoritesFragment();

        Bundle arguments = new Bundle();
        arguments.putBoolean("IS_HISTORY", position == 1 ? false : true);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.fragment_category_history);
            case 1:
                return mContext.getString(R.string.fragment_category_favourites);
            default:
                return null;
        }
    }
}
