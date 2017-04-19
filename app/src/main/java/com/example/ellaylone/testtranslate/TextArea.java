package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Created by ellaylone on 18.04.17.
 */

public class TextArea extends AppCompatEditText {
    private Context mContext;
    private boolean hasFocus;

    public TextArea(Context context) {
        super(context);
        mContext = context;
        hasFocus = false;
    }

    public TextArea(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        hasFocus = false;
    }

    public TextArea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        hasFocus = false;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        hasFocus = focused;
        setFocusBorder(focused);
    }

    private void setFocusBorder(boolean focused) {
        this.setBackgroundResource(focused ?
                R.drawable.text_area_border_focus :
                R.drawable.text_area_border_unfocus);
    }

    public boolean isHasFocus() {
        return hasFocus;
    }
}
