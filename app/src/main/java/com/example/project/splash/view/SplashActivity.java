package com.example.project.splash.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import com.example.project.R;
import com.example.project.base.BaseActivity;
import com.example.project.login.view.LoginActivity;
import com.example.project.splash.presenter.ISplashPresenter;
import com.example.project.splash.presenter.SplashPresenterImpl;

public class SplashActivity extends BaseActivity<ISplashPresenter> {

    private final int SPLASH_TIME = 2000;
    private Handler splashHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
        splashScreenHandler();
    }

    protected void init() {
        splashHandler = new Handler();
    }

    private void splashScreenHandler() {
        splashHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Class targetActivity = LoginActivity.class;
                Intent loginIntent = new Intent(SplashActivity.this, targetActivity);
                startActivity(loginIntent);
                finish();
                splashHandler = null;
            }
        }, SPLASH_TIME);
    }

    @NonNull
    @Override
    protected ISplashPresenter getPresenter() {
        return new SplashPresenterImpl();
    }
}
