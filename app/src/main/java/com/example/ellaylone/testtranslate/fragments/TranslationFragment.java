package com.example.ellaylone.testtranslate.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ellaylone.testtranslate.DbProvider;
import com.example.ellaylone.testtranslate.GetLangList;
import com.example.ellaylone.testtranslate.MainActivity;
import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.SelectLangActivity;
import com.example.ellaylone.testtranslate.TextArea;
import com.example.ellaylone.testtranslate.TranslateApi;
import com.example.ellaylone.testtranslate.TranslateProvider;
import com.example.ellaylone.testtranslate.requests.GetLangsRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ellaylone on 16.04.17.
 */

public class TranslationFragment extends Fragment {
    private static final String BUNDLE_LANGS_NAME = "LANGS";
    private static final String BUNDLE_ACTIVE_LANGS_NAME = "ACTIVE_LANGS";

    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_LANGS = "LANGS";
    public static final String EXTRA_ACTIVE_LANG = "ACTIVE_LANG";
    public static final String EXTRA_ACTIVE_LANG_TYPE = "ACTIVE_LANG_TYPE";

    private ImageView switchLangs;
    private TextView sourceLang;
    private TextView targetLang;
    private String activeSourceLang;
    private String activeTargetLang;

    private SQLiteDatabase db;
    private Map<String, String> langs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        storeLangs(outState);
        storeActiveLangs(outState);
    }

    private void storeLangs(Bundle outState) {
        ArrayList<Map> list = new ArrayList<>();
        list.add(langs);

        outState.putSerializable(BUNDLE_LANGS_NAME, list);
    }

    private void restoreLangs(Bundle inState) {
        ArrayList<Map> list = (ArrayList<Map>) inState.getSerializable(BUNDLE_LANGS_NAME);
        langs = list.get(0);
    }

    private void storeActiveLangs(Bundle outState) {
        String[] activeLangsList = {
                activeSourceLang,
                activeTargetLang
        };

        outState.putStringArray(BUNDLE_ACTIVE_LANGS_NAME, activeLangsList);
    }

    private void restoreActiveLangs(Bundle inState) {
        String[] activeLangsList = inState.getStringArray(BUNDLE_ACTIVE_LANGS_NAME);
        activeSourceLang = activeLangsList[0];
        activeTargetLang = activeLangsList[1];
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_translate, container, false);

        db = ((MainActivity) getActivity()).getDb();

        sourceLang = (TextView) view.findViewById(R.id.source_lang);
        targetLang = (TextView) view.findViewById(R.id.target_lang);
        switchLangs = (ImageView) view.findViewById(R.id.switch_langs);

        if (savedInstanceState != null) {
            restoreLangs(savedInstanceState);
            restoreActiveLangs(savedInstanceState);

            setupTextViews();
            setupSwitch();
        } else {
            updateActiveLangs();
            updateLangs();
        }


        TextArea textArea = (TextArea) view.findViewById(R.id.source_text_area);
        textArea.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                final TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
                if (!hasFocus) {
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    tabLayout.setVisibility(View.VISIBLE);
                                }
                            }, 200);
                } else {
                    tabLayout.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    private void setupSwitch() {
        switchLangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = activeSourceLang;
                activeSourceLang = activeTargetLang;
                activeTargetLang = temp;
                saveActiveLangs(activeSourceLang, activeTargetLang);
                setupTextViews();
            }
        });
    }

    private void saveActiveLangs(String sourceCode, String targetCode) {
        ContentValues newValues = new ContentValues();

        newValues.put("LANG_CODE", sourceCode);
        newValues.put("LANG_TYPE", 1);

        db.update(DbProvider.ACTIVE_LANGS_TABLE_NAME, newValues, String.format("%s = ?", "LANG_TYPE"),
                new String[]{"1"});
        newValues.clear();

        newValues.put("LANG_CODE", targetCode);
        newValues.put("LANG_TYPE", 2);

        db.update(DbProvider.ACTIVE_LANGS_TABLE_NAME, newValues, String.format("%s = ?", "LANG_TYPE"),
                new String[]{"2"});
        newValues.clear();
    }

    private void setupTextViews() {
        sourceLang.setText(langs.get(activeSourceLang));
        targetLang.setText(langs.get(activeTargetLang));

        final ArrayList<Map> list = new ArrayList<>();
        list.add(langs);

        sourceLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLangActivity.class);
                intent.putExtra(EXTRA_TITLE, getString(R.string.lang_select_source));
                intent.putExtra(EXTRA_LANGS, list);
                intent.putExtra(EXTRA_ACTIVE_LANG, activeSourceLang);
                intent.putExtra(EXTRA_ACTIVE_LANG_TYPE, 1);

                startActivity(intent);
            }
        });

        targetLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLangActivity.class);
                intent.putExtra(EXTRA_TITLE, getString(R.string.lang_select_target));
                intent.putExtra(EXTRA_LANGS, list);
                intent.putExtra(EXTRA_ACTIVE_LANG, activeTargetLang);
                intent.putExtra(EXTRA_ACTIVE_LANG_TYPE, 2);
                startActivity(intent);
            }
        });
    }

    private void updateActiveLangs() {
        Cursor c = db.query(DbProvider.ACTIVE_LANGS_TABLE_NAME, null, null, null, null, null, null);
        if (c.getCount() != 0) {
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                switch (c.getInt(c.getColumnIndex("LANG_TYPE"))) {
                    case 1:
                        activeSourceLang = c.getString(c.getColumnIndex("LANG_CODE"));
                        break;
                    case 2:
                        activeTargetLang = c.getString(c.getColumnIndex("LANG_CODE"));
                        break;
                    default:
                        break;
                }
                c.moveToNext();
            }
            c.close();
        } else {
            updateActiveLangsList();
        }
    }

    private void updateLangs() {
        Cursor c = db.query(DbProvider.LANGS_TABLE_NAME, null, null, null, null, null, null);
        if (c.getCount() != 0) {
            langs = new HashMap<String, String>(93);
            c.moveToFirst();
            for (int i = 0; i < c.getCount(); i++) {
                langs.put(c.getString(c.getColumnIndex("LANG_CODE")), c.getString(c.getColumnIndex("LANG_NAME")));
                c.moveToNext();
            }
            setupTextViews();
            setupSwitch();
        } else {
            updateLangsList();
        }
    }

    private void updateActiveLangsList() {
        ContentValues newValues = new ContentValues();

        activeSourceLang = "en";

        newValues.put("LANG_CODE", activeSourceLang);
        newValues.put("LANG_TYPE", 1);

        db.insert(DbProvider.ACTIVE_LANGS_TABLE_NAME, null, newValues);
        newValues.clear();

        activeTargetLang = "ru";

        newValues.put("LANG_CODE", activeTargetLang);
        newValues.put("LANG_TYPE", 2);

        db.insert(DbProvider.ACTIVE_LANGS_TABLE_NAME, null, newValues);
        newValues.clear();
    }

    private void updateLangsList() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TranslateProvider.getENDPOINT())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TranslateApi translateApi = retrofit.create(TranslateApi.class);
        GetLangsRequest getLangsRequest = new GetLangsRequest();
        Call<GetLangList> langsCall = getLangsRequest.getQuery(translateApi);
        langsCall.enqueue(new Callback<GetLangList>() {
            @Override
            public void onResponse(Call<GetLangList> call, final Response<GetLangList> response) {
                langs = response.body().getLangs();
                if (langs != null) {
                    writeLangs();
                }
            }

            @Override
            public void onFailure(Call<GetLangList> call, Throwable t) {
                Log.e("fail", "" + t);
            }
        });
    }

    private void writeLangs() {
        Iterator it = langs.entrySet().iterator();
        ContentValues newValues = new ContentValues();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            newValues.put("LANG_CODE", pair.getKey().toString());
            newValues.put("LANG_NAME", pair.getValue().toString());

            db.insert(DbProvider.LANGS_TABLE_NAME, null, newValues);

            newValues.clear();
        }

        setupTextViews();
        setupSwitch();
    }
}