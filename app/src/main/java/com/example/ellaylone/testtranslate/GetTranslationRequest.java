package com.example.ellaylone.testtranslate;

import com.example.ellaylone.testtranslate.requests.IRequest;

import retrofit2.Call;

/**
 * Created by ellaylone on 21.04.17.
 */

public class GetTranslationRequest implements IRequest<TranslateApi, GetTranslation> {

    @Override
    public Call<GetTranslation> getQuery(TranslateApi translateApi) {
        String lang = "en-ru";
        String key = TranslateProvider.getKEY();
//        if (trHolder.getTexts() != null) {
//            List<String> textsArr = new ArrayList<>();
//            for (String item : trHolder.getTexts()) {
//                String normalized = textNormalizer.normalize(item);
//                textsArr.add(normalized);
//            }
//            return translateApi.getTranslation(srv, key, textsArr, lang);
//        } else {
//            String normalized = textNormalizer.normalize(trHolder.getText());
            return translateApi.getTranslation(key, "asdasd", lang);
//        }
    }
}
