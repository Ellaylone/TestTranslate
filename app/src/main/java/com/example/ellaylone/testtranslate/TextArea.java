package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ellaylone on 18.04.17.
 */

public class TextArea extends AppCompatEditText implements View.OnFocusChangeListener {
    AppCompatEditText textArea;

    public TextArea(Context context) {
        super(context);
    }

    public TextArea(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextArea(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == R.id.source_text_area) {
            setInputFocusState(hasFocus);
        }
    }

    private void setInputFocusState(boolean hasFocus) {
        setFocusBorder(hasFocus);
    }

    private void setFocusBorder(boolean active) {
        textArea.setBackgroundResource(active ?
                R.drawable.text_area_border_focus :
                R.drawable.text_area_border_unfocus);
    }
}
