package com.example.project.registration.view;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.example.project.R;
import com.example.project.base.BaseActivity;
import com.example.project.registration.presenter.IRegistrationPresenter;
import com.example.project.registration.presenter.RegistartionPresenterImpl;
import com.example.project.util.DialogUtils;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends BaseActivity<IRegistrationPresenter> {

    Button okBtn;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmailId;
    private EditText mPhoneNumber;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private Button mDob;
    private EditText mEducation;
    private EditText mCity;
    private EditText mDistrict;
    private EditText mTaluka;
    private EditText mState;
    private EditText mPincode;
    private EditText mAddress;
    private TextView mDateWarningText;
    private Button mRegister;
    private Dialog mErrorDialog;
    private String mDateOfBirthString;
    private final TextWatcher textWatcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            RegistrationActivity.this.manageRegisterButtonStatus();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
        }
    };
    private RelativeLayout mProgressBar;
    private LinearLayout mWarningsLayout;
    private DatePicker.OnDateChangedListener dateSetListener = new DatePicker.OnDateChangedListener() {
        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            RegistrationActivity.this.mDateWarningText.setVisibility(8);
            RegistrationActivity.this.okBtn.setEnabled(true);
            Calendar c = Calendar.getInstance();
            if (year >= c.get(1) && monthOfYear >= c.get(2) && dayOfMonth > c.get(7) + 1) {
                RegistrationActivity.this.mDateWarningText.setVisibility(0);
                RegistrationActivity.this.okBtn.setEnabled(false);
            } else if (year >= c.get(1) && monthOfYear > c.get(2)) {
                RegistrationActivity.this.mDateWarningText.setVisibility(0);
                RegistrationActivity.this.okBtn.setEnabled(false);
            } else if (year > c.get(1)) {
                RegistrationActivity.this.mDateWarningText.setVisibility(0);
                RegistrationActivity.this.okBtn.setEnabled(false);
            }

        }
    };
    private final View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_register:
                    RegistrationActivity.this.mWarningsLayout.removeAllViews();
                    if (RegistrationActivity.this.mPassword.getText().toString().equals(RegistrationActivity.this.mConfirmPassword.getText().toString()) && RegistrationActivity.this.isEmailValid(RegistrationActivity.this.mEmailId.getText().toString())) {
                        RegistrationActivity.this.mProgressBar.setVisibility(View.VISIBLE);
                    } else {
                        TextView configuration;
                        if (!RegistrationActivity.this.mPassword.getText().toString().equals(RegistrationActivity.this.mConfirmPassword.getText().toString())) {
                            configuration = new TextView(RegistrationActivity.this);
                            configuration.setTextSize(12);
                            configuration.setPadding(5, 5, 5, 10);
                            configuration.setTextColor(Color.RED);
                            configuration.setText("Passwords do not match");
                            RegistrationActivity.this.mWarningsLayout.addView(configuration);
                        }

                        if (!RegistrationActivity.this.isEmailValid(RegistrationActivity.this.mEmailId.getText().toString())) {
                            configuration = new TextView(RegistrationActivity.this);
                            configuration.setTextSize(12);
                            configuration.setPadding(5, 5, 5, 10);
                            configuration.setTextColor(Color.RED);
                            configuration.setText("Invalid Email Id");
                            RegistrationActivity.this.mWarningsLayout.addView(configuration);
                        }
                    }
                    break;
                case R.id.dp_dob:
                    RegistrationActivity.this.launchDatePickerDialog();
            }

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
    }

    @Override
    protected IRegistrationPresenter getPresenter() {
        return new RegistartionPresenterImpl();
    }

    private void initViews() {
        this.mFirstName = (EditText) this.findViewById(R.id.et_first_name);
        this.mLastName = (EditText) this.findViewById(R.id.et_last_name);
        this.mEmailId = (EditText) this.findViewById(R.id.et_email);
        this.mPhoneNumber = (EditText) this.findViewById(R.id.et_phone_number);
        this.mPassword = (EditText) this.findViewById(R.id.et_password);
        this.mConfirmPassword = (EditText) this.findViewById(R.id.et_confirm_password);
        this.mDob = (Button) this.findViewById(R.id.dp_dob);
        this.mEducation = (EditText) this.findViewById(R.id.et_education);
        this.mCity = (EditText) this.findViewById(R.id.et_city);
        this.mDistrict = (EditText) this.findViewById(R.id.et_district);
        this.mTaluka = (EditText) this.findViewById(R.id.et_taluka);
        this.mState = (EditText) this.findViewById(R.id.et_state);
        this.mPincode = (EditText) this.findViewById(R.id.et_pincode);
        this.mAddress = (EditText) this.findViewById(R.id.et_address);
        this.mFirstName.addTextChangedListener(this.textWatcher);
        this.mLastName.addTextChangedListener(this.textWatcher);
        this.mEmailId.addTextChangedListener(this.textWatcher);
        this.mPhoneNumber.addTextChangedListener(this.textWatcher);
        this.mPassword.addTextChangedListener(this.textWatcher);
        this.mConfirmPassword.addTextChangedListener(this.textWatcher);
        this.mEducation.addTextChangedListener(this.textWatcher);
        this.mCity.addTextChangedListener(this.textWatcher);
        this.mDistrict.addTextChangedListener(this.textWatcher);
        this.mTaluka.addTextChangedListener(this.textWatcher);
        this.mState.addTextChangedListener(this.textWatcher);
        this.mPincode.addTextChangedListener(this.textWatcher);
        this.mAddress.addTextChangedListener(this.textWatcher);
        this.mDob.addTextChangedListener(this.textWatcher);
        this.mWarningsLayout = (LinearLayout) this.findViewById(R.id.layout_warnings);
        this.mProgressBar = (RelativeLayout) this.findViewById(R.id.layout_progress_bar);
        this.mProgressBar.setOnClickListener(this.clickListener);
        this.mRegister = (Button) this.findViewById(R.id.btn_register);
        this.mRegister.setOnClickListener(this.clickListener);
        this.mDob.setOnClickListener(this.clickListener);
        this.mRegister.setEnabled(false);
    }

    private void manageRegisterButtonStatus() {
        if (this.mFirstName.getText().length() != 0 && this.mLastName.getText().length() != 0 && this.mEmailId.getText().length() != 0 && this.mPhoneNumber.getText().length() != 0 && this.mPassword.getText().length() != 0 && this.mConfirmPassword.getText().length() != 0 && this.mEducation.getText().length() != 0 && this.mCity.getText().length() != 0 && this.mDistrict.getText().length() != 0 && this.mTaluka.getText().length() != 0 && this.mState.getText().length() != 0 && this.mPincode.getText().length() != 0 && this.mAddress.getText().length() != 0 && this.mDateOfBirthString != null && this.mDateOfBirthString.length() > 0) {
            this.mRegister.setEnabled(true);
        } else {
            this.mRegister.setEnabled(false);
        }

    }

    private void launchDatePickerDialog() {
        final Dialog dialog = DialogUtils.createDatePickerDialog(this, "Enter Date of birth", "", "Set", "Cancel");
        final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.date_picker);
        Button cancelBtn = (Button) dialog.findViewById(R.id.btn_cancel);
        this.mDateWarningText = (TextView) dialog.findViewById(R.id.tv_warning);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), this.dateSetListener);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        this.okBtn = (Button) dialog.findViewById(R.id.btn_ok);
        this.okBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RegistrationActivity.this.mDateOfBirthString = datePicker.getDayOfMonth() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getYear();
                RegistrationActivity.this.mDob.setText(RegistrationActivity.this.mDateOfBirthString);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
