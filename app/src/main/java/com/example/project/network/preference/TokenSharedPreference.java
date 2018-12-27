package com.example.project.network.preference;

import android.content.Context;

/**
 * Manages token saving and retrieving.
 */

public class TokenSharedPreference extends AbstractSharedPreference {

    private static final String PREF_NAME = "token_shared_preference";
    private static final String ACCESS_TOKEN = "access_token";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String EXPIRE_IN = "expire_in";
    private static final String TOKEN_TYPE = "token_type";

    public TokenSharedPreference(Context context) {
        super(context, PREF_NAME);
    }

    public void setExpireInToken(String expireIn) {
        put(EXPIRE_IN, getEncryptedText(expireIn));
    }

    public String getAccessToken() {
        String accessToken = getDecryptedText(get(ACCESS_TOKEN, ""));
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        put(ACCESS_TOKEN, getEncryptedText(accessToken));
    }

    public String getRefreshToken() {
        String refreshToken = getDecryptedText(get(REFRESH_TOKEN, ""));
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        put(REFRESH_TOKEN, getEncryptedText(refreshToken));
    }

    public String getExpireIn() {
        String expireIn = getDecryptedText(get(EXPIRE_IN, ""));
        return expireIn;
    }

    public String getTokenType() {
        String tokenType = getDecryptedText(get(TOKEN_TYPE, ""));
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        put(TOKEN_TYPE, getEncryptedText(tokenType));
    }
}
