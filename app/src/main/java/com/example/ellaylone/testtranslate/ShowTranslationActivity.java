package com.example.ellaylone.testtranslate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.ellaylone.testtranslate.fragments.TranslationFragment;

public class ShowTranslationActivity extends AppCompatActivity {

    Intent intent;
    TextView translationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_fullscreen);

        intent = getIntent();

        String translation = intent.getStringExtra(TranslationFragment.EXTRA_TRANSLATION_TO_SHOW);

        translationTextView = (TextView) findViewById(R.id.translation_text_view);
        translationTextView.setText(translation);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        NavUtils.navigateUpFromSameTask(ShowTranslationActivity.this);
        return false;
    }
}
