package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

/**
 * Created by ellaylone on 18.04.17.
 */

public class TextAreaWrap extends RelativeLayout {
    RelativeLayout viewRoot;

    public TextAreaWrap(Context context) {
        super(context);
        setupLayout(context);
    }

    public TextAreaWrap(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupLayout(context);
    }

    public TextAreaWrap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setupLayout(context);
    }

    private void setupLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        viewRoot = (RelativeLayout) inflater.inflate(R.layout.text_area, this);
    }
}
