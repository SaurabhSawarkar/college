package com.example.project.network.interceptor;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class Base64EncodeRequestInterceptor implements Interceptor {

    private String mUsername;
    private String mPassword;

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (mUsername != null && mPassword != null) {
            request = request.newBuilder()
                    .addHeader("Authorization", Credentials.basic(mUsername, mPassword))
                    .build();
        }

        return chain.proceed(request);
    }

}
