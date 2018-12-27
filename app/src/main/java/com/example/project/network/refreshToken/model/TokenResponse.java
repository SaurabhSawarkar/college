package com.example.project.network.refreshToken.model;

import com.example.project.network.util.BaseResponse;
import com.google.gson.annotations.SerializedName;

/**
 * Created by IoT-Engg team.
 */

public class TokenResponse extends BaseResponse {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("refresh_token")
    private String refreshToken;
    @SerializedName("expires_in")
    private String expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getExpiresIn() {
        return expiresIn;
    }
}
