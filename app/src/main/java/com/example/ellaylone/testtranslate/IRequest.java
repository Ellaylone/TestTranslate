package com.example.ellaylone.testtranslate;

import retrofit2.Call;

/**
 * Created by ellaylone on 16.04.17.
 */

public interface IRequest<T, K> {
    Call<K> getQuery(T retrofitService);
}
