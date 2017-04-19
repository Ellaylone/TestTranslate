package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ellaylone on 16.04.17.
 */

public class SwipeViewPager extends ViewPager {
    private boolean enabled;

    public SwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        enabled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return enabled ? super.onTouchEvent(ev) : false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return enabled ? super.onInterceptTouchEvent(ev) : false;
    }

    /**
     * @return boolean is swipe enabled
     */
    @Override
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Switch swipe on/off
     *
     * @param enabled boolean desired swipe status
     */
    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}