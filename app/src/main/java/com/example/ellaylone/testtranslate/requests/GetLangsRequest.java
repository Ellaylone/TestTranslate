package com.example.ellaylone.testtranslate.requests;

import com.example.ellaylone.testtranslate.GetLangList;
import com.example.ellaylone.testtranslate.TranslateApi;
import com.example.ellaylone.testtranslate.TranslateProvider;
import com.example.ellaylone.testtranslate.requests.IRequest;

import retrofit2.Call;

/**
 * Created by ellaylone on 16.04.17.
 */

public class GetLangsRequest implements IRequest<TranslateApi, GetLangList> {
    @Override
    public Call<GetLangList> getQuery(TranslateApi translateApi) {
        String ui = "ru";
        String key = TranslateProvider.getKEY();
        return translateApi.getLangs(key, ui);
    }
}