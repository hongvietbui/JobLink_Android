package com.SE1730.Group3.JobLink.src.domain.utilities;

import android.content.res.ColorStateList;
import android.text.InputType;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.SE1730.Group3.JobLink.R;
import com.google.android.material.textfield.TextInputLayout;

import org.threeten.bp.format.DateTimeFormatter;
import org.threeten.bp.format.DateTimeParseException;

import java.util.regex.Pattern;

public class Validator {
    public static boolean isNullOrEmpty(EditText editText){
        if(TextUtils.isEmpty(editText.getText().toString())){
            editText.setError("This field is required");
            return true;
        }
        return false;
    }

    public static boolean isNullOrEmpty(TextInputLayout textInputLayout){
        if(TextUtils.isEmpty(textInputLayout.getEditText().getText().toString())){
            textInputLayout.setHelperText("This field is required");
            textInputLayout.setHelperTextColor(ColorStateList.valueOf(0xFFFF0000));
            return true;
        }
        return false;
    }

    public static boolean isValidRepassword(EditText password, EditText repassword){
        if (isNullOrEmpty(repassword)) return false;
        if (!password.getText().toString().equals(repassword.getText().toString())) {
            repassword.setError("Password does not match");
            return false;
        }
        return true;
    }

    public static boolean isValidDate(EditText editText, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        try {
            formatter.parse(editText.getText().toString());
            return true;
        } catch (DateTimeParseException e) {
            editText.setError("Invalid date format");
            return false;
        }
    }

    // Password must have at least 8 characters, including at least one uppercase letter, one number, and one special character
    public static boolean isValidPassword(EditText editText) {
        if (isNullOrEmpty(editText))
            return false;
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=]).{8,}$";
        if(!Pattern.matches(passwordPattern, editText.getText().toString()))
        {
            editText.setError("Password must have at least 8 characters, including at least one uppercase letter, one number, and one special character");
            return false;
        }
        return true;
    }

    public static boolean isNumeric(EditText editText) {
        if (isNullOrEmpty(editText)) return false;
        try {
            Double.parseDouble(editText.getText().toString());
            return true;
        } catch (NumberFormatException e) {
            editText.setError("This field must be a number");
            return false;
        }
    }

    public static boolean isValidEmail(EditText editText) {
        if (isNullOrEmpty(editText)) return false;
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (!Pattern.matches(emailPattern, editText.getText().toString())) {
            editText.setError("Invalid email format");
            return false;
        }
        return true;
    }

    //Phone number must have 10 digits and start with 0
    public static boolean isValidPhoneNumber(EditText editText) {
        if (isNullOrEmpty(editText)) return false;
        String phonePattern = "^0\\d{9}$";
        if (!Pattern.matches(phonePattern, editText.getText().toString())) {
            editText.setError("Invalid phone number");
            return false;
        }
        return true;
    }

    public static boolean areFieldsNullOrEmpty(EditText... editTexts){
        boolean result = false;

        for (EditText editText : editTexts) {
            if(isNullOrEmpty(editText)) result = true;
        }
        return result;
    }

    public static boolean areFieldsNullOrEmpty(TextInputLayout... textInputLayouts){
        boolean result = false;

        for (TextInputLayout textInputLayout : textInputLayouts) {
            if(isNullOrEmpty(textInputLayout)) result = true;
        }
        return result;
    }

    public static void togglePasswordVisibility(EditText editText, ImageView imageView) {
        if ((editText.getInputType() & InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            imageView.setImageResource(R.drawable.ic_eye_slash);
        } else {
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            imageView.setImageResource(R.drawable.ic_eye);
        }
        editText.setSelection(editText.getText().length());
    }
}
