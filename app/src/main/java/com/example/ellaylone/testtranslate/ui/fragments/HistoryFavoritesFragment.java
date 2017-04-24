package com.example.ellaylone.testtranslate.ui.fragments;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.TranslateApp;
import com.example.ellaylone.testtranslate.TranslationItem;
import com.example.ellaylone.testtranslate.adapters.HistoryFavItemAdapter;
import com.example.ellaylone.testtranslate.db.DbProvider;
import com.example.ellaylone.testtranslate.ui.activity.MainActivity;

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

        reload();

        return rootView;
    }

    public void reload() {
        if (isHistory) {
            setupHistory();
        } else {
            setupFavourites();
        }
    }

    private void populateList() {
        if (listData.size() == 0) {
            listData.add(new TranslationItem(null, null, null, null, null, 0));
        }
        HistoryFavItemAdapter adapter = new HistoryFavItemAdapter(listData, isHistory, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFav;

                if (v.getId() == R.id.is_favourite) {
                    View parent = (View) v.getParent();
                    TextView historyId = (TextView) parent.findViewById(R.id.history_id);

                    int id = Integer.parseInt(historyId.getText().toString());

                    Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, "_id=" + id, null, null, null, null);

                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        isFav = c.getInt(c.getColumnIndex("IS_FAV")) == 1;
                        isFav = !isFav;

                        ImageView isFavourite = (ImageView) v.findViewById(R.id.is_favourite);
                        isFavourite.setImageResource(isFav ? R.drawable.fav_true : R.drawable.fav_false);

                        ContentValues updatedValues = new ContentValues();

                        updatedValues.put("IS_FAV", isFav);
                        String where = "_id=" + id;

                        db.update(DbProvider.HISTORY_TABLE_NAME, updatedValues, where, null);
                    }
                } else {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    TranslateApp translateApp = (TranslateApp) mainActivity.getApplicationContext();

                    TextView historyId = (TextView) v.findViewById(R.id.history_id);

                    translateApp.setCurrentHistoryId(Integer.parseInt(historyId.getText().toString()));

                    translateApp.updateHistory();
                    translateApp.setCurrentHistoryLoaded(false);

                    mainActivity.moveToTranslation();
                }
            }
        });
        listView.setAdapter(adapter);
    }

    private void setupHistory() {
        Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, null, null, null, null, "_id DESC", null);

        listData.clear();

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
        Cursor c = db.query(DbProvider.HISTORY_TABLE_NAME, null, "IS_FAV=1", null, null, null, "_id DESC", null);

        listData.clear();

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