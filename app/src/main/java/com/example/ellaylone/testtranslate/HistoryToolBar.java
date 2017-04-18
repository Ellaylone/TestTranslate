package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by ellaylone on 18.04.17.
 */

public class HistoryToolBar extends RelativeLayout implements View.OnClickListener {

    RelativeLayout activityRoot;
    ImageView toolbarBack;
    ImageView toolbarDelete;

    OnClickListener onBackClickListener;
    OnClickListener onDeleteClickListener;

    public HistoryToolBar(Context context) {
        super(context);
        SetupLayout(context);
    }

    public HistoryToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetupLayout(context);
    }

    public HistoryToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
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
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.toolbar_back:
                if(onBackClickListener != null) {
                    onBackClickListener.onClick(v);
                }
            case R.id.toolbar_delete:
                if(onDeleteClickListener != null) {
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
}
