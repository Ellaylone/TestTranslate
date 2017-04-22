package com.example.ellaylone.testtranslate;

/**
 * Created by ellaylone on 22.04.17.
 */

public class TranslationItem {
    private String sourceText, targetText, sourceLang, targetLang;
    private Integer id;
    private boolean isFavourite;

    public TranslationItem(String sourceText, String targetText, String sourceLang, String targetLang, Integer id, int isFavourite) {
        this.sourceText = sourceText;
        this.targetText = targetText;
        this.sourceLang = sourceLang;
        this.targetLang = targetLang;
        this.id = id;
        this.isFavourite = isFavourite == 1 ? true : false;
    }

    public String getSourceText() {
        return sourceText;
    }

    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    public String getTargetText() {
        return targetText;
    }

    public void setTargetText(String targetText) {
        this.targetText = targetText;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public String getTargetLang() {
        return targetLang;
    }

    public void setTargetLang(String targetLang) {
        this.targetLang = targetLang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
