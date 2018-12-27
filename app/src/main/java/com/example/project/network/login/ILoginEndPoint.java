package com.example.project.network.login;
import com.example.project.network.refreshToken.model.TokenResponse;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Login end point API interface
 */

public interface ILoginEndPoint {
    interface ILoginAPI {
        //TODO: Put login url here
        @POST("post_url comes here")
        Call<TokenResponse> loginAPI(@Query("code") String authorizationCode, @Query("redirect_uri") String redirectUrl);

    }

    void login(String authorizationCode, String redirectUrl);
}
