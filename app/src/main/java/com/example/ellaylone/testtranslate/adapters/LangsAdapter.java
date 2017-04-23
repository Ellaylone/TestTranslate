package com.example.ellaylone.testtranslate.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ellaylone.testtranslate.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by ellaylone on 18.04.17.
 */

public class LangsAdapter extends BaseAdapter {
    private final ArrayList mData;
    View.OnClickListener onLangClickListener;
    private String activeLanguage;

    public LangsAdapter(Map<String, String> map, String activeLanguage, View.OnClickListener onLangClickListener) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
        this.activeLanguage = activeLanguage;
        this.onLangClickListener = onLangClickListener;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.lang_list_item, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = getItem(position);

        TextView itemTitle = (TextView) result.findViewById(R.id.item_title);
        itemTitle.setText(item.getValue());
        itemTitle.setHint(item.getKey());
        itemTitle.setOnClickListener(onLangClickListener);

        result.findViewById(R.id.item_selected).setVisibility(item.getKey().trim().equalsIgnoreCase(activeLanguage) ? ImageView.VISIBLE : ImageView.GONE);

        return result;
    }
}