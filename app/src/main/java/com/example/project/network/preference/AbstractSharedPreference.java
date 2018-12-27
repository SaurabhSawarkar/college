package com.example.project.network.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import com.example.project.network.util.AES;

/**
 * Base class for all the shared preferences.
 */

public abstract class AbstractSharedPreference {

    private static final String TAG = "AbstractSharedPreference";
    private SharedPreferences sharedPreferences;

    AbstractSharedPreference(Context context, String prefName) {
        sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    protected boolean put(String key, Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }
        return editor.commit();
    }

    protected String get(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    protected Float get(String key, Float defaultValue) {
        return sharedPreferences.getFloat(key, defaultValue);
    }

    protected Boolean get(String key, boolean defaultValue) {
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    protected Integer get(String key, Integer defaultValue) {
        return sharedPreferences.getInt(key, defaultValue);
    }

    protected boolean remove(String key) {
        if (key == null || !sharedPreferences.contains(key)) {
            return false;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        return editor.commit();
    }

    public boolean clear() {
        return sharedPreferences.edit().clear().commit();
    }

    @SuppressLint("LongLogTag")
    protected String getEncryptedText(String token) {
        try {
            if (!TextUtils.isEmpty(token)) {
                token = AES.encrypt(token);
            }
        } catch (Exception e) {
            Log.d(TAG, "Problem in encryption: " + e.toString());
        }
        return token;
    }

    @SuppressLint("LongLogTag")
    protected String getDecryptedText(String token) {
        try {
            if (!TextUtils.isEmpty(token)) {
                token = AES.decrypt(token);
            }
        } catch (Exception e) {
            Log.d(TAG, "Problem in decryption: " + e.toString());
        }
        return token;
    }
}
