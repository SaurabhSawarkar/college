package com.example.project.dashboard.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.project.R;
import com.example.project.base.ToolbarBaseActivity;
import com.example.project.dashboard.presenter.DashboardPresenterImpl;
import com.example.project.dashboard.presenter.IDashboradPresenter;

public class DashboardActivity extends ToolbarBaseActivity<IDashboradPresenter> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setupToolbar("Dashboard", true);
    }

    @Override
    protected boolean isNavigationDrawerRequired() {
        return true;
    }

    @Override
    protected IDashboradPresenter getPresenter() {
        return new DashboardPresenterImpl();
    }
}
