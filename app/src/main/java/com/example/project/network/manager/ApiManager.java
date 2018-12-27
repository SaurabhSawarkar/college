package com.example.project.network.manager;

import android.content.Context;

import com.example.project.network.client.RetrofitClient;
import com.example.project.network.interceptor.AccessTokenInterceptor;
import com.example.project.network.interceptor.Base64EncodeRequestInterceptor;
import com.example.project.network.login.LoginEndPoint;
import com.example.project.network.preference.TokenSharedPreference;
import com.example.project.network.util.IResponsePublisher;
import com.example.project.network.util.RequestTypes;
import com.example.project.network.util.ResponsePublisher;
import retrofit2.Retrofit;

/**
 * Common interface for each network call.
 */

public class ApiManager implements IApiManager {

    private static ApiManager mIntance;
    private String mBaseUrl;
    private final ResponsePublisher mResponsePublisher;
    private final boolean isDebugEnabled = true;
    private Retrofit mOAuthRetrofitInstance;
    private TokenSharedPreference mTokenSharedPreference;

    private Context mContext;

    private ApiManager(Context context) {
        mContext = context;
        mResponsePublisher = new ResponsePublisher();
        mTokenSharedPreference = new TokenSharedPreference(mContext);
    }

    public static ApiManager getInstance() {
        if (mIntance == null) {
            throw new ExceptionInInitializerError("Please call init first");
        }
        return mIntance;
    }

    public static void init(Context applicationContext) {
        mIntance = new ApiManager(applicationContext);
    }

    @Override
    public void setBaseUrl(String baseUrl) {
        mBaseUrl = baseUrl;
        updateRetrofitInstance();
    }

    /**
     * Everytime base url is changed, update the retrofit instance
     */
    private void updateRetrofitInstance() {
        RetrofitClient.Builder retrofitBuilder = new RetrofitClient.Builder();
        retrofitBuilder
                .baseUrl(mBaseUrl)
                .tokenSharedPreference(getTokenSharedPreference())
                .debug(isDebugEnabled);

        AccessTokenInterceptor interceptor = new AccessTokenInterceptor();
        interceptor.setAccessTokenPref(new TokenSharedPreference(mContext));
        retrofitBuilder.interceptor(interceptor);
        mOAuthRetrofitInstance = retrofitBuilder.buildOAuth();
    }

    private TokenSharedPreference getTokenSharedPreference() {
        if (mTokenSharedPreference == null) {
            mTokenSharedPreference = new TokenSharedPreference(mContext);
        }
        return mTokenSharedPreference;
    }

    /**
     * Returns OAuth instance of retrofit client.
     *
     * @return - Retrofit client
     */
    private Retrofit getOAuthRetrofitInstance() {
        if (mOAuthRetrofitInstance == null) {
            throw new ExceptionInInitializerError("Base URL can't be null");
        }
        return mOAuthRetrofitInstance;
    }

    @Override
    public void registerResponseObserver(IResponsePublisher responsePublisher) {
        mResponsePublisher.registerResponsePublisher(responsePublisher);
    }

    @Override
    public void unregisterResponseObserver(IResponsePublisher responsePublisher) {
        mResponsePublisher.unregisterResponsePublisher(responsePublisher);
    }

    @Override
    public void unregisterAllResponseObserver() {
        mResponsePublisher.unregisterAllResponsePublisher();
    }

    @Override
    public void login(@RequestTypes.Interface int requestType, String username, String password, String oAuthCode, String redirectUrl) {
        RetrofitClient.Builder retrofitBuilder = new RetrofitClient.Builder();
        retrofitBuilder
                .baseUrl(mBaseUrl)
                .debug(true);
        Base64EncodeRequestInterceptor interceptor = new Base64EncodeRequestInterceptor();
        interceptor.setUsername(username);
        interceptor.setPassword(password);
        retrofitBuilder.interceptor(interceptor);

        LoginEndPoint endPoint = new LoginEndPoint(requestType, retrofitBuilder.buildSimple(), mResponsePublisher);
        endPoint.login(oAuthCode, redirectUrl);
    }
}
