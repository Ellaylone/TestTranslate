package com.example.ellaylone.testtranslate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by ellaylone on 16.04.17.
 */

public class TranslationFragment extends Fragment {
    TextView sourceLang;
    TextView targetLang;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_translate, container, false);

        sourceLang = (TextView) view.findViewById(R.id.source_lang);
        targetLang = (TextView) view.findViewById(R.id.target_lang);

        sourceLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("translate", "source");
            }
        });

        targetLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("translate", "target");
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TranslateProvider.getENDPOINT())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TranslateApi translateApi = retrofit.create(TranslateApi.class);
        GetLangsRequest getLangsRequest = new GetLangsRequest();
        Call<GetLangList> langsCall = getLangsRequest.getQuery(translateApi);
        langsCall.enqueue(new Callback<GetLangList>() {
            @Override
            public void onResponse(Call<GetLangList> call, Response<GetLangList> response) {
                sourceLang.setText(response.body().getLangs().get("en"));
                targetLang.setText(response.body().getLangs().get("ru"));
            }

            @Override
            public void onFailure(Call<GetLangList> call, Throwable t) {
                Log.v("fail", "" + t);
            }
        });

        return view;
    }
}
