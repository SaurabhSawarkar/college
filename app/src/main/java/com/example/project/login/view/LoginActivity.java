package com.example.project.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import com.example.project.R;
import com.example.project.base.BaseActivity;
import com.example.project.dashboard.view.DashboardActivity;
import com.example.project.login.presenter.ILoginPresenter;
import com.example.project.login.presenter.LoginPresenterImpl;
import com.example.project.registration.view.RegistrationActivity;

public class LoginActivity extends BaseActivity<ILoginPresenter> {

    private Button mLoginBtn;
    private Button mRegisterBtn;
    private View.OnClickListener mViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_login:
                    Intent dashboardIntent = new Intent(mContext, DashboardActivity.class);
                    startActivity(dashboardIntent);
                    finish();
                    break;
                case R.id.btn_register:
                    Intent registration = new Intent(mContext, RegistrationActivity.class);
                    startActivity(registration);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }

    private void initViews() {
        mLoginBtn = findViewById(R.id.btn_login);
        mRegisterBtn = findViewById(R.id.btn_register);

        mLoginBtn.setOnClickListener(mViewClickListener);
        mRegisterBtn.setOnClickListener(mViewClickListener);
    }

    @Override
    protected ILoginPresenter getPresenter() {
        return new LoginPresenterImpl();
    }
}
