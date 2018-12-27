package com.example.project.network.interceptor;

import java.io.IOException;

import com.example.project.network.preference.TokenSharedPreference;
import com.example.project.network.util.Constants;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Interceptor to set/updated access token
 */

public class AccessTokenInterceptor implements Interceptor {

    private TokenSharedPreference mTokenPref;

    public void     setAccessTokenPref(TokenSharedPreference tokenPref) {
        mTokenPref = tokenPref;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request requestChain = chain.request();
        if (mTokenPref != null) {
            String accessToken = mTokenPref.getAccessToken();
            if (accessToken != null) {
                requestChain = requestChain.newBuilder()
                        .addHeader(Constants.AUTHORIZATION, "Bearer " + accessToken).build();
            }
        }
        return chain.proceed(requestChain);
    }
}