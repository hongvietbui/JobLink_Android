package com.SE1730.Group3.JobLink.src.presentation.ui.fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class Raleway_Medium_TextView extends AppCompatTextView {

    public Raleway_Medium_TextView(Context context) {
        super(context);
        init();
    }

    public Raleway_Medium_TextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Raleway_Medium_TextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLineSpacing(0, 0.9f);

        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Medium.ttf");
            setTypeface(tf);
        }
    }
}
