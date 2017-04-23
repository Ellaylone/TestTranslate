package com.example.ellaylone.testtranslate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.TranslationItem;

import java.util.ArrayList;

/**
 * Created by ellaylone on 18.04.17.
 */

public class HistoryFavItemAdapter extends BaseAdapter {
    private final ArrayList mData;
    private boolean mIsHistory;
    private View.OnClickListener mOnClickListener;

    public HistoryFavItemAdapter(ArrayList<TranslationItem> items, boolean isHistory, View.OnClickListener onClickListener) {
        mData = items;
        mOnClickListener = onClickListener;
        mIsHistory = isHistory;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public TranslationItem getItem(int position) {
        return (TranslationItem) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            if (mData.size() == 1 && getItem(0).getSourceText() == null) {
                result = LayoutInflater.from(parent.getContext()).inflate(mIsHistory ? R.layout.history_list_hist_empty : R.layout.history_list_fav_empty, parent, false);
            } else {
                result = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);

                TranslationItem item = getItem(position);

                TextView historyId = (TextView) result.findViewById(R.id.history_id);
                historyId.setText(item.getId().toString());

                TextView sourceText = (TextView) result.findViewById(R.id.source_text);
                sourceText.setText(item.getSourceText());

                TextView translatedText = (TextView) result.findViewById(R.id.translated_text);
                translatedText.setText(item.getTargetText());

                TextView translationLangs = (TextView) result.findViewById(R.id.translation_langs);
                translationLangs.setText(item.getSourceLang() + " - " + item.getTargetLang());

                ImageView isFavourite = (ImageView) result.findViewById(R.id.is_favourite);
                isFavourite.setImageResource(item.isFavourite() ? R.drawable.fav_true : R.drawable.fav_false);

                result.setOnClickListener(mOnClickListener);
                isFavourite.setOnClickListener(mOnClickListener);
            }
        } else {
            result = convertView;
        }
        return result;
    }
}