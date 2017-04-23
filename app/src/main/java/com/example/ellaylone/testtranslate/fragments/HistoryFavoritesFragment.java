package com.example.ellaylone.testtranslate.fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ellaylone.testtranslate.DbProvider;
import com.example.ellaylone.testtranslate.MainActivity;
import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.TranslationItem;
import com.example.ellaylone.testtranslate.adapters.HistoryFavItemAdapter;

import java.util.ArrayList;


public class HistoryFavoritesFragment extends Fragment {
    private boolean isHistory;

    private SQLiteDatabase db;

    private ArrayList<TranslationItem> listData = new ArrayList<>();
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.history_list, container, false);

        listView = (ListView) rootView.findViewById(R.id.list);

        db = ((MainActivity) getActivity()).getDb();

        isHistory = getArguments().getBoolean("IS_HISTORY");

        if(isHistory) {
            setupHistory();
        } else {
            setupFavourites();
        }

        return rootView;
    }

    private void populateList() {
        if(listData.size() == 0) {
            listData.add(new TranslationItem(null, null, null, null, null, 0));
        }
        HistoryFavItemAdapter adapter = new HistoryFavItemAdapter(listData, isHistory, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, null, null, null, null, null);
                //TODO
            }
        });
        listView.setAdapter(adapter);
    }

    private void setupHistory() {
        Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, null, null, null, null, null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                listData.add(new TranslationItem(
                        c.getString(c.getColumnIndex("SOURCE_TEXT")),
                        c.getString(c.getColumnIndex("TRANSLATED_TEXT")),
                        c.getString(c.getColumnIndex("LANG_CODE_SOURCE")),
                        c.getString(c.getColumnIndex("LANG_CODE_TRANSLATION")),
                        c.getInt(c.getColumnIndex("_id")),
                        c.getInt(c.getColumnIndex("IS_FAV"))
                ));
                c.moveToNext();
            }
            c.close();
        }
        populateList();
    }

    private void setupFavourites() {
        Cursor c = db.query(DbProvider.FAVOURITES_TABLE_NAME, null, null, null, null, null, null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                listData.add(new TranslationItem(
                        c.getString(c.getColumnIndex("SOURCE_TEXT")),
                        c.getString(c.getColumnIndex("TRANSLATED_TEXT")),
                        c.getString(c.getColumnIndex("LANG_CODE_SOURCE")),
                        c.getString(c.getColumnIndex("LANG_CODE_TRANSLATION")),
                        c.getInt(c.getColumnIndex("_id")),
                        1
                ));
                c.moveToNext();
            }
            c.close();
        }
        populateList();
    }
}