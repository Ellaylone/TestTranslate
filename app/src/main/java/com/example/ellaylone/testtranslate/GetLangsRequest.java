package com.example.ellaylone.testtranslate;

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