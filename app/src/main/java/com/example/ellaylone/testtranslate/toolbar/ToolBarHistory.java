package com.example.ellaylone.testtranslate.toolbar;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.ellaylone.testtranslate.R;
import com.example.ellaylone.testtranslate.viewPager.SwipeViewPager;

/**
 * Created by ellaylone on 18.04.17.
 */

public class ToolBarHistory extends RelativeLayout implements View.OnClickListener {

    RelativeLayout activityRoot;
    ImageView toolbarBack;
    ImageView toolbarDelete;

    TabLayout tabs;

    private OnClickListener onBackClickListener;
    private OnClickListener onDeleteClickListener;

    public ToolBarHistory(Context context) {
        super(context);
        SetupLayout(context);
    }

    public ToolBarHistory(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetupLayout(context);
    }

    public ToolBarHistory(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetupLayout(context);
    }

    private void SetupLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        activityRoot = (RelativeLayout) inflater.inflate(R.layout.toolbar_history_layout, this);

        toolbarBack = (ImageView) activityRoot.findViewById(R.id.toolbar_back);
        toolbarBack.setOnClickListener(this);

        toolbarDelete = (ImageView) activityRoot.findViewById(R.id.toolbar_delete);
        toolbarDelete.setOnClickListener(this);

        tabs = (TabLayout) findViewById(R.id.toolbar_tabs);
    }

    public void setupTabsWithPager(SwipeViewPager swipeViewPager) {
        tabs.setupWithViewPager(swipeViewPager);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_back:
                if (onBackClickListener != null) {
                    onBackClickListener.onClick(v);
                }
            case R.id.toolbar_delete:
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onClick(v);
                }
        }
    }

    public void hideToolbarBackIcon() {
        toolbarBack.setVisibility(GONE);
    }

    public void hideToolbarDeleteIcon() {
        toolbarDelete.setVisibility(GONE);
    }

    public void setOnBackClickListener(OnClickListener onClickListener) {
        this.onBackClickListener = onClickListener;
    }

    public void setOnDeleteClickListener(OnClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public TabLayout getTabs() {
        return tabs;
    }
}
