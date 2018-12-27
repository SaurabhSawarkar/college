package com.example.project.util;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import com.example.project.R;

/**
 * General util class
 */

public class CommonUtil {

    private static final String TWO_DIGIT_FORMATTER = "%02d";
    private static final String GT_STRING = "GT";
    private static final String GT_EQ_STRING = "GT_EQ";
    private static final String LT_STRING = "LT";
    private static final String LT_EQ_STRING = "LT_EQ";

    public static String getTwoDigitStringOfNumber(int number) {
        String string = String.format(TWO_DIGIT_FORMATTER, number);
        return string;
    }

    public static void hideKeyboard(Context context, View parentLayout) {
        if (parentLayout != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(parentLayout.getWindowToken(), 0);
        }
    }

    public static String capitalize(String string) {
        String capitalizeString = "";
        if (!TextUtils.isEmpty(string)) {
            capitalizeString = string.substring(0, 1).toUpperCase() + string.substring(1);
        }
        return capitalizeString;
    }

    public static void logout(Context context) {

    }

    @SuppressWarnings("deprecation")
    public static void removeOnGlobalLayoutListener(ViewTreeObserver viewTreeObserver,
                                                    ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (viewTreeObserver != null && listener != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                viewTreeObserver.removeGlobalOnLayoutListener(listener);
            } else {
                viewTreeObserver.removeOnGlobalLayoutListener(listener);
            }
        }
    }
}
