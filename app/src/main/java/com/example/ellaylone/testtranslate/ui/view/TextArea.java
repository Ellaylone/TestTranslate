package com.example.ellaylone.testtranslate.ui.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.example.ellaylone.testtranslate.R;

/**
 * Created by ellaylone on 18.04.17.
 */

public class TextArea extends AppCompatEditText {
    static final int MIN_DISTANCE = 150;
    private Context mContext;
    private boolean hasFocus;
    private float x1, x2;
    private String sourceBackup = "";

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if (this.getText().toString().equals("")) {
                        this.setText(sourceBackup);
                        sourceBackup = "";
                    } else {
                        sourceBackup = this.getText().toString();
                        this.setText("");
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
