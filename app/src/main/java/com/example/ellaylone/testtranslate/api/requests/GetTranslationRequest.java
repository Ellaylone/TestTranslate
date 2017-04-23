package com.example.ellaylone.testtranslate.api.requests;

import com.example.ellaylone.testtranslate.api.TranslateApi;
import com.example.ellaylone.testtranslate.api.providers.TranslateProvider;
import com.example.ellaylone.testtranslate.api.get.GetTranslation;

import retrofit2.Call;

/**
 * Created by ellaylone on 21.04.17.
 */

public class GetTranslationRequest implements IRequest<TranslateApi, GetTranslation> {

    private String translationText;
    private String sourceLang;
    private String targetLang;

    public void setTranslationText(String translationText) {
        this.translationText = translationText;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    @Override
    public Call<GetTranslation> getQuery(TranslateApi translateApi) {
        String lang = sourceLang + "-" + targetLang;
        String key = TranslateProvider.getKEY();
        return translateApi.getTranslation(key, translationText, lang);
    }
}
