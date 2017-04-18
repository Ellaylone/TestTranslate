package com.example.ellaylone.testtranslate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by ellaylone on 17.04.17.
 */

public class SimpleToolBar extends RelativeLayout implements View.OnClickListener {

    RelativeLayout activityRoot;
    ImageView toolbarBack;
    ImageView toolbarDelete;
    TextView toolbarTitle;

    OnClickListener onBackClickListener;
    OnClickListener onDeleteClickListener;

    public SimpleToolBar(Context context) {
        super(context);
        SetupLayout(context);
    }

    public SimpleToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        SetupLayout(context);
    }

    public SimpleToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        SetupLayout(context);
    }

    private void SetupLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        activityRoot = (RelativeLayout) inflater.inflate(R.layout.simple_toolbar_layout, this);

        toolbarBack = (ImageView) activityRoot.findViewById(R.id.toolbar_back);
        toolbarBack.setOnClickListener(this);

        toolbarDelete = (ImageView) activityRoot.findViewById(R.id.toolbar_delete);
        toolbarDelete.setOnClickListener(this);

        toolbarTitle = (TextView) activityRoot.findViewById(R.id.toolbar_title);
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

    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
    }

    public void hideToolbarTitle() {
        toolbarTitle.setVisibility(GONE);
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
