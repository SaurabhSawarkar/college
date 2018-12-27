package com.example.project.network.client;

import com.example.project.network.interceptor.OAuthInterceptor;
import com.example.project.network.preference.TokenSharedPreference;
import com.example.project.network.util.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * Retrofit client
 */

public class RetrofitClient {

    public RetrofitClient() {

    }

    public static class Builder {

        private boolean isDebug;
        private String baseUrl;
        private Gson gson;
        private Interceptor interceptor;
        private TokenSharedPreference tokenSharedPref;

        public Builder() {

        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder gson(Gson gson) {
            this.gson = gson;
            return this;
        }

        public Builder tokenSharedPreference(TokenSharedPreference preference) {
            this.tokenSharedPref = preference;
            return this;
        }

        public Builder debug(boolean debug) {
            isDebug = debug;
            return this;
        }

        public Builder interceptor(Interceptor interceptor) {
            this.interceptor = interceptor;
            return this;
        }

        public Retrofit buildSimple() {
            OkHttpClient okHttpClient = getSimpleHttpClient();
            if (gson == null) {
                //generate default gson instance
                gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();
            }

            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        public Retrofit buildOAuth() {
            OkHttpClient okHttpClient = getOAuthHttpClient();
            if (gson == null) {
                //generate default gson instance
                gson = new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create();
            }

            return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        private OkHttpClient getSimpleHttpClient() {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(Constants.OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);
            httpClient.readTimeout(Constants.OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);
            httpClient.followRedirects(false);
            if (interceptor != null) {
                httpClient.addInterceptor(interceptor);
            }
            if (isDebug) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }
            return httpClient.build();
        }

        private OkHttpClient getOAuthHttpClient() {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.connectTimeout(Constants.OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);
            httpClient.readTimeout(Constants.OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS);
            httpClient.followRedirects(false);
            if (interceptor != null) {
                httpClient.addInterceptor(interceptor);
            }
            if (isDebug) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                httpClient.addInterceptor(logging);
            }
            OAuthInterceptor oAuthInterceptor = new OAuthInterceptor();
            oAuthInterceptor.setTokenSharedPref(tokenSharedPref);
            httpClient.authenticator(oAuthInterceptor);
            return httpClient.build();
        }
    }
}
