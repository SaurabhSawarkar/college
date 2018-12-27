package com.example.project.network.refreshToken;

import com.example.project.network.refreshToken.model.TokenResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;

/**
 * Handles refresh token API call
 */

public class RefreshToken implements IRefreshToken {

    private IRefreshTokenAPI mRefreshTokenAPI;

    public RefreshToken(Retrofit retrofit) {
        mRefreshTokenAPI = retrofit.create(IRefreshTokenAPI.class);
    }

    @Override
    public Response<TokenResponse> refreshToken(String refreshToken, String redirectURL) {
        Call<TokenResponse> call = mRefreshTokenAPI.refreshTokenAPI(refreshToken, redirectURL);
        Response<TokenResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
