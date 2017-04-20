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
import android.widget.TextView;

import com.example.ellaylone.testtranslate.GetLangList;
import com.example.ellaylone.testtranslate.MainActivity;
import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.SelectLangActivity;
import com.example.ellaylone.testtranslate.TextArea;
import com.example.ellaylone.testtranslate.TranslateApi;
import com.example.ellaylone.testtranslate.TranslateProvider;
import com.example.ellaylone.testtranslate.requests.GetLangsRequest;

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
    public static final String EXTRA_TITLE = "TITLE";

    private TextView sourceLang;
    private TextView targetLang;

    private SQLiteDatabase db;
    private Map<String, String> langs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_translate, container, false);

        db = ((MainActivity) getActivity()).getDb();

        sourceLang = (TextView) view.findViewById(R.id.source_lang);
        targetLang = (TextView) view.findViewById(R.id.target_lang);

        updateLangs();

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

    private void setupTextViews() {
        sourceLang.setText(langs.get("en"));
        targetLang.setText(langs.get("ru"));

        sourceLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLangActivity.class);
                intent.putExtra(EXTRA_TITLE, getString(R.string.lang_select_source));
                startActivity(intent);
            }
        });

        targetLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLangActivity.class);
                intent.putExtra(EXTRA_TITLE, getString(R.string.lang_select_target));
                startActivity(intent);
            }
        });
    }

    private void updateLangs() {
        Cursor c = db.query("LANGS", null, null, null, null, null, null);
        if (c.getCount() != 0) {
            langs = new HashMap<String, String>(93);
            c.moveToFirst();
            for (int i = 0; i < (c.getCount() - 1); i++) {
                langs.put(c.getString(c.getColumnIndex(c.getColumnName(1))), c.getString(c.getColumnIndex(c.getColumnName(2))));
                Log.v(c.getString(c.getColumnIndex(c.getColumnName(1))), c.getString(c.getColumnIndex(c.getColumnName(2))));
                c.moveToNext();
            }
            setupTextViews();
        } else {
            updateLangsList();
        }
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
                    setupTextViews();
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

            db.insert("LANGS", null, newValues);

            newValues.clear();
        }
    }
}