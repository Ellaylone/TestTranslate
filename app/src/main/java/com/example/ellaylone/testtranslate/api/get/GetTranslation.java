package com.example.ellaylone.testtranslate.api.get;

import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ellaylone on 21.04.17.
 */

public class GetTranslation {
    private String lang;
    private List<String> text;

    public GetTranslation(List<String> texts, String lang) {
        if (texts != null) {
            this.text = texts;
            this.lang = lang;
        }
    }

    public GetTranslation(String text, String lang) {
        if (text != null) {
            this.text = Arrays.asList(text);
            this.lang = lang;
        }
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Nullable
    public String getFirstText() {
        List<String> items = getText();
        return items != null && items.size() > 0 ? items.get(0) : null;
    }
}
