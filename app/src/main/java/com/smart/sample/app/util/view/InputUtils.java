package com.smart.sample.app.util.view;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

public class InputUtils {


    public static boolean emailInvalid(String email) {
        if (email == null)
            return true;
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches();
    }

    public static boolean phoneInvalid(String phone) {
        if (phone == null)
            return true;
        return !Patterns.PHONE.matcher(phone.trim()).matches();
    }


    public static boolean isEmpty(String s) {
        if (s == null)
            return true;
        if (TextUtils.isEmpty(s.trim()))
            return true;
        return false;

    }

    public static String getTrimString(String s) {
        if (s == null)
            return "";
        return s.trim();


    }


    public static void showError(TextInputLayout layout, @Nullable String message) {
        if (message != null) {
            layout.setError(message);
        } else layout.setErrorEnabled(false);
    }
}
