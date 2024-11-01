package com.SE1730.Group3.JobLink.src.presentation.ui.fontsmaterialuiux;

import android.widget.Button;

public class Raleway_Medium_Button extends Button {
    public Raleway_Medium_Button(android.content.Context context) {
        super(context);
        init();
    }

    public Raleway_Medium_Button(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Raleway_Medium_Button(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            android.graphics.Typeface tf = android.graphics.Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Medium.ttf");
            setTypeface(tf);
        }
    }
}
