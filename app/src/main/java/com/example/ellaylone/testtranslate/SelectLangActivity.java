package com.example.ellaylone.testtranslate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ellaylone.testtranslate.fragments.TranslationFragment;
import com.example.ellaylone.testtranslate.toolbar.ToolBarSimple;

public class SelectLangActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lang);

        Intent intent = getIntent();

        ToolBarSimple toolBar = (ToolBarSimple) findViewById(R.id.toolbar);
        toolBar.setToolbarTitle(intent.getStringExtra(TranslationFragment.EXTRA_TITLE));
        toolBar.setOnBackClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(SelectLangActivity.this);
            }
        });
    }
}
