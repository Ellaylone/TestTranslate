package com.example.ellaylone.testtranslate.fragments;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ellaylone.testtranslate.DbProvider;
import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.SelectLangActivity;
import com.example.ellaylone.testtranslate.adapters.LangsAdapter;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ellaylone on 17.04.17.
 */

public class LangListFragment extends Fragment {
    private Map<String, String> langs;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lang_list, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list);

        ArrayList<Map> list = (ArrayList<Map>) getArguments().getSerializable(TranslationFragment.EXTRA_LANGS);
        langs = list.get(0);

        final String activeLang = getArguments().getString(TranslationFragment.EXTRA_ACTIVE_LANG);
        final Integer activeLangType = getArguments().getInt(TranslationFragment.EXTRA_ACTIVE_LANG_TYPE);

        LangsAdapter adapter = new LangsAdapter(langs, activeLang, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = ((SelectLangActivity) getActivity()).getDb();

                ContentValues newValues = new ContentValues();

                newValues.put("LANG_CODE", ((TextView) v).getHint().toString());
                newValues.put("LANG_TYPE", activeLangType);

                db.update(DbProvider.ACTIVE_LANGS_TABLE_NAME, newValues, String.format("%s = ?", "LANG_CODE"),
                        new String[]{activeLang});
                newValues.clear();

                NavUtils.navigateUpFromSameTask(getActivity());
            }
        });
        listView.setAdapter(adapter);

        return rootView;
    }
}
