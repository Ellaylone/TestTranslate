package com.example.ellaylone.testtranslate;

import retrofit2.Call;
import retrofit2.http.GET;
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
}
