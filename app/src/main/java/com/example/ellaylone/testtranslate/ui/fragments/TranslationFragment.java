package com.example.ellaylone.testtranslate.ui.fragments;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ellaylone.testtranslate.db.DbProvider;
import com.example.ellaylone.testtranslate.api.get.GetLangList;
import com.example.ellaylone.testtranslate.api.get.GetTranslation;
import com.example.ellaylone.testtranslate.api.requests.GetTranslationRequest;
import com.example.ellaylone.testtranslate.ui.activity.MainActivity;
import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.ui.activity.SelectLangActivity;
import com.example.ellaylone.testtranslate.ui.activity.ShowTranslationActivity;
import com.example.ellaylone.testtranslate.ui.view.TextArea;
import com.example.ellaylone.testtranslate.api.TranslateApi;
import com.example.ellaylone.testtranslate.api.providers.TranslateProvider;
import com.example.ellaylone.testtranslate.TranslationItem;
import com.example.ellaylone.testtranslate.api.requests.GetLangsRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
    public static final String EXTRA_LANGS = "LANGS";
    public static final String EXTRA_ACTIVE_LANG = "ACTIVE_LANG";
    public static final String EXTRA_ACTIVE_LANG_TYPE = "ACTIVE_LANG_TYPE";
    public static final String EXTRA_TRANSLATION_TO_SHOW = "TRANSLATION_TO_SHOW";
    private static final String BUNDLE_LANGS_NAME = "LANGS";
    private static final String BUNDLE_ACTIVE_LANGS_NAME = "ACTIVE_LANGS";
    private static final String BUNDLE_SOURCE_TEXT = "SOURCE_TEXT";
    private static final String BUNDLE_TRANSLATED_TEXT = "TRANSLATED_TEXT";
    private Retrofit retrofit;
    private TranslateApi translateApi;

    private ImageView switchLangs;
    private TextView sourceLang;
    private TextView targetLang;
    private TextView translation;
    private TextArea sourceText;
    private ImageView showTranslation;
    private ImageView addFav;
    private String activeSourceLang;
    private String activeTargetLang;
    private List<String> translatedText;

    private TranslationItem currentHistory;

    private SQLiteDatabase db;
    private Map<String, String> langs;

    private Handler handler = new Handler();
    private Runnable runnable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRetrofit();
        setRetainInstance(true);
    }

    private void setupRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(TranslateProvider.getENDPOINT())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        translateApi = retrofit.create(TranslateApi.class);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        storeLangs(outState);
        storeActiveLangs(outState);
        storeSourceText(outState);
        storeTranslatedText(outState);
        writeHistory();
    }

    private void storeSourceText(Bundle outState) {
        outState.putString(BUNDLE_SOURCE_TEXT, sourceText.getText().toString());
    }

    private void restoreSourceText(Bundle inState) {
        sourceText.setText(inState.getString(BUNDLE_SOURCE_TEXT));
    }

    private void storeTranslatedText(Bundle outState) {
        ArrayList<List> list = new ArrayList<>();
        list.add(translatedText);

        outState.putSerializable(BUNDLE_TRANSLATED_TEXT, list);
    }

    private void restoreTranslatedText(Bundle inState) {
        ArrayList<List> list = (ArrayList<List>) inState.getSerializable(BUNDLE_TRANSLATED_TEXT);
        translatedText = list.get(0);
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

        getHistory(0);

        sourceLang = (TextView) view.findViewById(R.id.source_lang);
        targetLang = (TextView) view.findViewById(R.id.target_lang);
        switchLangs = (ImageView) view.findViewById(R.id.switch_langs);
        translation = (TextView) view.findViewById(R.id.translation_result_text);
        showTranslation = (ImageView) view.findViewById(R.id.show_translation);
        addFav = (ImageView) view.findViewById(R.id.add_fav);
        sourceText = (TextArea) view.findViewById(R.id.source_text_area);

        setupTranslationResults();

        if (savedInstanceState != null) {
            restoreLangs(savedInstanceState);
            restoreActiveLangs(savedInstanceState);
            restoreSourceText(savedInstanceState);
            restoreTranslatedText(savedInstanceState);

            setupTextViews();
            setupSwitch();
            displayTranslation();
        } else {
            if (currentHistory != null) {
                activeSourceLang = currentHistory.getSourceLang();
                activeTargetLang = currentHistory.getTargetLang();
                sourceText.setText(currentHistory.getSourceText());
                List<String> list = new ArrayList<String>();
                list.add(currentHistory.getTargetText());
                translatedText = list;
                displayTranslation();
            } else {
                updateActiveLangs();
            }
            updateLangs();
        }

        sourceText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        sourceText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String newText = s.toString();
                handler.removeCallbacks(runnable);

                runnable = new Runnable() {
                    @Override
                    public void run() {
                        getTranslation(newText);
                    }
                };

                handler.postDelayed(runnable, 1000);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return view;
    }

    private void setupTranslationResults() {
        addFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        showTranslation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeHistory();

                Intent intent = new Intent(getActivity(), ShowTranslationActivity.class);
                intent.putExtra(EXTRA_TRANSLATION_TO_SHOW, translatedText.get(0));

                startActivity(intent);
            }
        });

        displayTranslation();
    }

    private void getTranslation(String text) {
        if (text.equals("")) {
            translatedText = null;
            displayTranslation();
        } else {
            GetTranslationRequest getTranslationRequest = new GetTranslationRequest();

            getTranslationRequest.setTranslationText(text);
            getTranslationRequest.setSourceLang(activeSourceLang);
            getTranslationRequest.setTargetLang(activeTargetLang);

            Call<GetTranslation> translationCall = getTranslationRequest.getQuery(translateApi);

            translationCall.enqueue(new Callback<GetTranslation>() {
                @Override
                public void onResponse(Call<GetTranslation> call, Response<GetTranslation> response) {
                    translatedText = response.body().getText();
                    displayTranslation();
                }

                @Override
                public void onFailure(Call<GetTranslation> call, Throwable t) {
                    Log.e("fail", "" + t);
                }
            });
        }
    }

    private void displayTranslation() {
        String resultText = "";

        if (translatedText != null) {
            resultText = translatedText.get(0);
        }

        showTranslation.setVisibility(translatedText == null ? View.GONE : View.VISIBLE);
        addFav.setVisibility(translatedText == null ? View.GONE : View.VISIBLE);

        translation.setText(resultText);
    }

    private void setupSwitch() {
        switchLangs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (translatedText != null) {
                    writeHistory();
                    sourceText.setText(translatedText.get(0));
                    translatedText = null;
                }

                String temp = activeSourceLang;
                activeSourceLang = activeTargetLang;
                activeTargetLang = temp;
                saveActiveLangs(activeSourceLang, activeTargetLang);
                setupTextViews();
                displayTranslation();
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

    private void writeHistory() {
        if (!sourceText.getText().toString().equals("") && translatedText != null) {
            if (currentHistory == null ||
                    !currentHistory.getSourceText().equals(sourceText.getText().toString()) ||
                    !currentHistory.getSourceLang().equals(activeSourceLang) ||
                    !currentHistory.getTargetLang().equals(activeTargetLang)) {
                currentHistory = new TranslationItem(
                        sourceText.getText().toString(),
                        translatedText.get(0),
                        activeSourceLang,
                        activeTargetLang,
                        0,
                        0
                );

                ContentValues newValues = new ContentValues();

                newValues.put("SOURCE_TEXT", currentHistory.getSourceText());
                newValues.put("TRANSLATED_TEXT", currentHistory.getTargetText());
                newValues.put("LANG_CODE_SOURCE", currentHistory.getSourceLang());
                newValues.put("LANG_CODE_TRANSLATION", currentHistory.getTargetLang());

                db.insert(DbProvider.HISTORY_TABLE_NAME, null, newValues);

                newValues.clear();
            }
        }
    }

    private void getHistory(int id) {
        Cursor c;
        if (id == 0) {
            c = db.query(DbProvider.HISTORY_TABLE_NAME, null, null, null, null, null, "_id DESC", "1");
        } else {
            c = db.query(DbProvider.HISTORY_TABLE_NAME, new String[]{"_id"},
                    "_id LIKE ? ",
                    new String[]{"%" + id + "%"},
                    null, null, null, null);
        }

        if (c.getCount() > 0) {
            c.moveToFirst();
            currentHistory = new TranslationItem(
                    c.getString(c.getColumnIndex("SOURCE_TEXT")),
                    c.getString(c.getColumnIndex("TRANSLATED_TEXT")),
                    c.getString(c.getColumnIndex("LANG_CODE_SOURCE")),
                    c.getString(c.getColumnIndex("LANG_CODE_TRANSLATION")),
                    c.getInt(c.getColumnIndex("_id")),
                    c.getInt(c.getColumnIndex("IS_FAV"))
            );
            c.close();
        }
    }
}