package com.example.project.network.interceptor;


import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.example.project.network.client.RetrofitClient;
import com.example.project.network.preference.TokenSharedPreference;
import com.example.project.network.refreshToken.RefreshToken;
import com.example.project.network.refreshToken.model.TokenResponse;
import com.example.project.network.util.Constants;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

import java.io.IOException;

/**
 * Used to refresh the access token.
 */

public class OAuthInterceptor implements Authenticator {

    private TokenSharedPreference mTokenSharedPref;

    public void setTokenSharedPref(TokenSharedPreference pref) {
        mTokenSharedPref = pref;
    }

    @Nullable
    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        RetrofitClient.Builder retrofitBuilder = new RetrofitClient.Builder();
        retrofitBuilder
                .baseUrl(Constants.SSO_BASE_URL)
                .debug(true);
        Base64EncodeRequestInterceptor BasicInterceptor = new Base64EncodeRequestInterceptor();
        BasicInterceptor.setUsername(Constants.SMARTWS_CLIENT_ID);
        BasicInterceptor.setPassword(Constants.SMARTWS_SECRET);
        retrofitBuilder.interceptor(BasicInterceptor);

        String redirectUrl = Constants.SSO_BASE_URL + "/oauth-server/access_response";
        final RefreshToken refreshToken = new RefreshToken(retrofitBuilder.buildSimple());
        retrofit2.Response<TokenResponse> tokenResponse = refreshToken.
                refreshToken(mTokenSharedPref.getRefreshToken(), redirectUrl);

        if (tokenResponse != null && tokenResponse.body() != null) {
            TokenResponse newToken = tokenResponse.body();
            updateTokens(newToken);
            return addAuthorizationHeader(response.request(), mTokenSharedPref.getAccessToken());
        }
        return null;
    }

    private void updateTokens(TokenResponse tokenResponse) {
        if (tokenResponse != null && mTokenSharedPref != null) {
            mTokenSharedPref.setAccessToken(tokenResponse.getAccessToken());
            mTokenSharedPref.setTokenType(tokenResponse.getTokenType());
            mTokenSharedPref.setExpireInToken(tokenResponse.getExpiresIn());
            String refreshToken = mTokenSharedPref.getRefreshToken();
            if (!TextUtils.isEmpty(refreshToken)) {
                mTokenSharedPref.setRefreshToken(refreshToken);
            }
        }
    }

    private Request addAuthorizationHeader(Request original, String accessToken) {
        return original.newBuilder()
                .header(Constants.AUTHORIZATION,
                        "Bearer " + accessToken)
                .build();
    }
}
