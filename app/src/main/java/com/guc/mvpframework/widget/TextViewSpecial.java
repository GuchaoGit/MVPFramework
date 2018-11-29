package com.guc.mvpframework.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by guc on 2018/11/29.
 * 描述：特殊字体的TextView
 */
public class TextViewSpecial extends AppCompatTextView {
    public TextViewSpecial(Context context) {
        super(context);
    }

    public TextViewSpecial(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewSpecial(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setTypeface(Typeface tf, int style) {
        AssetManager mgr = getContext().getAssets();//得到AssetManager
        tf = Typeface.createFromAsset(mgr, "fonts/rm_albion.ttf");//根据路径得到Typeface
        super.setTypeface(tf, style);
    }
}
