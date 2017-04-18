package com.example.ellaylone.testtranslate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

    TextView sourceLang;
    TextView targetLang;

    private Map <String, String> langs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_translate, container, false);

        sourceLang = (TextView) view.findViewById(R.id.source_lang);
        targetLang = (TextView) view.findViewById(R.id.target_lang);

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
                sourceLang.setText(langs.get("en"));
                targetLang.setText(langs.get("ru"));

                sourceLang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Object[] langsA = langs.values().toArray();
                        Intent intent = new Intent(getActivity(), SelectLangActivity.class);
                        intent.putExtra(EXTRA_TITLE, "Язык текста");
                        startActivity(intent);
                    }
                });

                targetLang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SelectLangActivity.class);
                        intent.putExtra(EXTRA_TITLE, "Язык перевода");
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<GetLangList> call, Throwable t) {
                Log.v("fail", "" + t);
            }
        });

        return view;
    }
}