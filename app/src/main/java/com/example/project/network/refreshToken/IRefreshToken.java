package com.example.project.network.refreshToken;

import com.example.project.network.refreshToken.model.TokenResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Refresh token endpoint.
 */

public interface IRefreshToken {

    Response<TokenResponse> refreshToken(String refreshToken, String redirectURL);

    interface IRefreshTokenAPI {
        @POST("/oauth-server/oauth/token?grant_type=refresh_token")
        Call<TokenResponse> refreshTokenAPI(@Query("refresh_token") String refreshToken, @Query("redirect_uri") String redirectURL);
    }
}
