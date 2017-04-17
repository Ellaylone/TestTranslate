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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
                TextView sourceLang = (TextView) getActivity().findViewById(R.id.source_lang);
                sourceLang.setText(response.body().getLangs().get("en"));
                TextView targetLang = (TextView) getActivity().findViewById(R.id.target_lang);
                targetLang.setText(response.body().getLangs().get("ru"));
            }

            @Override
            public void onFailure(Call<GetLangList> call, Throwable t) {
                Log.v("fail", "" + t);
            }
        });

        return inflater.inflate(R.layout.fragment_category_translate, container, false);
    }
}
