package com.example.ellaylone.testtranslate;

/**
 * Created by ellaylone on 17.04.17.
 */

public class TranslateProvider {
    private static final String ENDPOINT = "https://translate.yandex.net";

    private static final String KEY = "trnsl.1.1.20170416T181730Z.097a14cc7fa2cfaa.d7e457c8c30a1a6ed422f6902c14157652de56b6";

    public static String getENDPOINT() {
        return ENDPOINT;
    }

    public static String getKEY() {
        return KEY;
    }
}
