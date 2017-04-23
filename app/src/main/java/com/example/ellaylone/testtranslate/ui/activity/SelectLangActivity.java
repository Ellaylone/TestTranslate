package com.example.ellaylone.testtranslate.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.TranslateApp;
import com.example.ellaylone.testtranslate.ui.fragments.LangListFragment;
import com.example.ellaylone.testtranslate.ui.fragments.TranslationFragment;
import com.example.ellaylone.testtranslate.toolbar.ToolBarSimple;

import java.util.ArrayList;
import java.util.Map;

public class SelectLangActivity extends AppCompatActivity {

    SQLiteDatabase db;

    Intent intent;

    public SQLiteDatabase getDb() {
        return db;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lang);

        intent = getIntent();
        db = ((TranslateApp) getApplicationContext()).getDb();

        setupToolbar();

        ArrayList<Map> list = (ArrayList<Map>) intent.getSerializableExtra(TranslationFragment.EXTRA_LANGS);
        String activeLang = intent.getStringExtra(TranslationFragment.EXTRA_ACTIVE_LANG);
        Integer activeLangType = intent.getIntExtra(TranslationFragment.EXTRA_ACTIVE_LANG_TYPE, 1);

        Fragment fragment = new LangListFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(TranslationFragment.EXTRA_LANGS, list);
        arguments.putString(TranslationFragment.EXTRA_ACTIVE_LANG, activeLang);
        arguments.putInt(TranslationFragment.EXTRA_ACTIVE_LANG_TYPE, activeLangType);
        fragment.setArguments(arguments);

        FragmentManager frgManager = getFragmentManager();
        frgManager.beginTransaction().replace(R.id.lang_list, fragment).commit();
    }

    private void setupToolbar() {
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
