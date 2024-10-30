package com.SE1730.Group3.JobLink.src.presentation.ui.fontsmaterialuiux;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputLayout;

public class Raleway_Regular_TextInputLayout extends TextInputLayout {
    public Raleway_Regular_TextInputLayout(Context context) {
        super(context);
        init();
    }

    public Raleway_Regular_TextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Raleway_Regular_TextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Kiểm tra nếu EditText bên trong không null
        if (getEditText() != null) {
            getEditText().setLineSpacing(0, 0.9f); // Thiết lập line spacing cho EditText
            if (!isInEditMode()) {
                // Load font từ assets
                Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Raleway-Regular.ttf");
                // Thiết lập font cho EditText bên trong TextInputLayout
                getEditText().setTypeface(tf);
            }
        }
    }
}
