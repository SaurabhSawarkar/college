package com.example.project.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.example.project.R;
import com.example.project.util.CommonUtil;
import com.example.project.util.DialogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Base fragment for all the fragments.
 */

public abstract class BaseFragment<P extends IBasePresenter> extends Fragment {

    protected Context mContext;
    protected P mPresenter;
    protected boolean isFragmentSelected;
    protected List<Throwable> mExceptionList;
    private DialogUtils.DialogClickListener mDialogClickListener = new DialogUtils.DialogClickListener() {
        @Override
        public void onPositiveButtonClick() {
            CommonUtil.logout(mContext);
        }

        @Override
        public void onNegativeButtonClicked() {

        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mPresenter = getPresenter();
        mExceptionList = new ArrayList<>();
    }

    private String getScreenName() {
        return String.format(getString(R.string.on_create_called_formatter),
                this.getClass().getSimpleName());
    }

    private String getActivityName() {
        Activity activity = getActivity();
        String activityName = "";
        if (activity != null) {
            activityName = getActivity().getClass().getSimpleName();
        }
        return activityName;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    /**
     * Method gets called whenever fragment is visited.
     */
    public abstract void onFragmentSelected();

    /**
     * Gets called whenever fragment is deselected.
     */
    public abstract void onFragmentUnSelected();

    protected abstract P getPresenter();

    protected void showPositiveNegativeBtnErrorMessage(String errorMessage, DialogUtils.DialogClickListener listener) {
        DialogUtils.showPositiveNegativeBtnAlertDialog(mContext, getString(R.string.error_text), errorMessage,
                getString(R.string.ok_text), getString(R.string.cancel_text), listener);
    }

    protected synchronized void showPositiveBtnDialog(String title, String message, DialogUtils.DialogClickListener listener) {
        if (isAdded()) {
            DialogUtils.showPositiveBtnAlertDialog(mContext, title, message, getString(R.string.ok_text), false, listener);
        }
    }

    protected void showRefreshExpiryDialog() {
        if (isAdded()) {
            DialogUtils.showTokenExpiryAlertDialog(mContext, null,
                    getString(R.string.refresh_token_expiry_msg), getString(R.string.ok_text), mDialogClickListener);
        }
    }

}
