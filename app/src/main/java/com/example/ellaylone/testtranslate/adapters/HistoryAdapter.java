package com.example.ellaylone.testtranslate.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.fragments.HistoryFavoritesFragment;

/**
 * Created by ellaylone on 16.04.17.
 */
public class HistoryAdapter extends FragmentPagerAdapter {

    private Context mContext;

    public HistoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        return HistoryFavoritesFragment.getInstance();
    }

    @Override
    public int getCount() {
        return 2;
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
