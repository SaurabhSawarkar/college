package com.example.project.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.example.project.R;
import com.example.project.util.DialogUtils;

/**
 * Base activity for all the activities of IoT CC app.
 * If Toolbar is needed then use {@link ToolbarBaseActivity}
 */

public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity {

    protected P mPresenter;
    protected Context mContext;
    private DialogUtils.DialogClickListener mDialogClickListener = new DialogUtils.DialogClickListener() {
        @Override
        public void onPositiveButtonClick() {

        }

        @Override
        public void onNegativeButtonClicked() {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mPresenter = getPresenter();
    }

    /**
     * Returns the name of child class who extended from BaseActivity
     *
     * @return - name of the child class extended from BaseActivity
     */
    private String getScreenName() {
        return String.format(getString(R.string.on_create_called_formatter),
                this.getClass().getSimpleName());
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    protected void showPositiveBtnDialog(String title, String message, DialogUtils.DialogClickListener listener) {
        if (!isFinishing()) {
            DialogUtils.showPositiveBtnAlertDialog(mContext, title, message, getString(R.string.ok_text), false, listener);
        }
    }

    protected void showPositiveNegativeBtnErrorMessage(String title, String errorMessage, String okText, DialogUtils.DialogClickListener listener) {
        DialogUtils.showPositiveNegativeBtnAlertDialog(mContext, title, errorMessage,
                okText, getString(R.string.cancel_text), listener);
    }

    protected void showPositiveBtnDialog(String title, String message, String okText,
                                         DialogUtils.DialogClickListener listener) {
        if (!isFinishing()) {
            DialogUtils.showPositiveBtnAlertDialog(mContext, title, message, okText, false, listener);
        }
    }

    protected void showRefreshExpiryDialog() {
        if (!isFinishing()) {
            DialogUtils.showTokenExpiryAlertDialog(mContext, null,
                    getString(R.string.refresh_token_expiry_msg), getString(R.string.ok_text), mDialogClickListener);
        }
    }

    protected abstract P getPresenter();

    @Override
    protected void onDestroy() {
        mContext = null;
        super.onDestroy();
    }
}
