package com.example.ellaylone.testtranslate.api.requests;

import com.example.ellaylone.testtranslate.api.TranslateApi;
import com.example.ellaylone.testtranslate.api.providers.TranslateProvider;
import com.example.ellaylone.testtranslate.api.get.GetLangList;

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