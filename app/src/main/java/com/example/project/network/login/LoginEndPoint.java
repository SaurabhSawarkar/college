package com.example.project.network.login;

import com.example.project.network.refreshToken.model.TokenResponse;
import com.example.project.network.util.AbstractCallback;
import com.example.project.network.util.IResponsePublisher;
import com.example.project.network.util.RequestTypes;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Handles login API call
 */

public class LoginEndPoint implements ILoginEndPoint {

    private ILoginAPI mLoginApi;
    private IResponsePublisher mResponsePublisher;
    @RequestTypes.Interface
    int mRequestType;

    public LoginEndPoint(@RequestTypes.Interface int requestType, Retrofit retrofit, IResponsePublisher publisher) {
        mRequestType = requestType;
        mResponsePublisher = publisher;
        mLoginApi = retrofit.create(ILoginAPI.class);
    }

    @Override
    public void login(String endpointUrl, String redirectUrl) {
        Call<TokenResponse> call = mLoginApi.loginAPI(endpointUrl, redirectUrl);
        call.enqueue(new AbstractCallback<TokenResponse>(mRequestType, mResponsePublisher));
    }
}
