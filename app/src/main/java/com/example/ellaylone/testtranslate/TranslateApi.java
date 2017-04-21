package com.example.ellaylone.testtranslate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ellaylone on 16.04.17.
 */

public interface TranslateApi {
    @GET("/api/v1.5/tr.json/getLangs")
    Call<GetLangList> getLangs(
            @Query("key") String key,
            @Query("ui") String ui
    );

    @FormUrlEncoded
    @POST("/api/v1.5/tr.json/translate")
    Call<GetTranslation> getTranslation(
            @Query("key") String key,
            @Field("text") String text,
            @Field("lang") String lang
    );
}
