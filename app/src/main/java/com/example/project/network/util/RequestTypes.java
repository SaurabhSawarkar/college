package com.example.project.network.util;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Defines requests of each API calls.
 */

public class RequestTypes {

    public static final int NONE = 0;
    public static final int LOGIN_REQUEST = NONE + 1;
    public static final int DASHBOARD_REQUEST = LOGIN_REQUEST + 1; //2

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NONE,
            LOGIN_REQUEST,
            DASHBOARD_REQUEST}
    )

    public @interface Interface {
    }
}
