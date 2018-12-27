package com.example.project.util;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import com.example.project.R;


/**
 * Holds various dialogs needed throughout the application.
 */

public class DialogUtils {

    private static AlertDialog mTokenExpiryDialog;
    private static String mPositiveBtnErrorMessage = "";

    public static void showPositiveNegativeBtnAlertDialog(final Context context, String title, String message,
                                                          String positiveBtnText, String negativeBtnText, final DialogClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(message);

        builder.setPositiveButton(positiveBtnText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onPositiveButtonClick();
                        }
                    }
                });

        builder.setNegativeButton(negativeBtnText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onNegativeButtonClicked();
                        }
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
        });

        dialog.show();
    }

    public synchronized static void showPositiveBtnAlertDialog(final Context context, String title, String message,
                                                               String positiveBtnText, final boolean isCancelable, final DialogClickListener listener) {
        if (message != null && !mPositiveBtnErrorMessage.equalsIgnoreCase(message)) {
            mPositiveBtnErrorMessage = message;
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            builder.setMessage(message);

            builder.setPositiveButton(positiveBtnText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // positive button logic
                            dialog.dismiss();
                            mPositiveBtnErrorMessage = "";
                            if (listener != null) {
                                listener.onPositiveButtonClick();
                            }
                        }
                    });
            final AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                }
            });

            dialog.show();
        }
    }

    public static void showTokenExpiryAlertDialog(final Context context, String title, String message,
                                                  String positiveBtnText, final DialogClickListener listener) {

        if (mTokenExpiryDialog == null || !mTokenExpiryDialog.isShowing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
            if (!TextUtils.isEmpty(title)) {
                builder.setTitle(title);
            }
            builder.setMessage(message);

            builder.setPositiveButton(positiveBtnText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // positive button logic
                            dialog.dismiss();
                            if (listener != null) {
                                listener.onPositiveButtonClick();
                            }
                        }
                    });
            mTokenExpiryDialog = builder.create();
            mTokenExpiryDialog.setCancelable(false);
            mTokenExpiryDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface arg0) {
                    mTokenExpiryDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                }
            });

            mTokenExpiryDialog.show();
        }
    }

    public static void showLogoutAlertDialog(final Context context, String title, String message,
                                             String positiveBtnText, String negativeBtnText, final DialogClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }
        builder.setMessage(message);

        builder.setPositiveButton(positiveBtnText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // positive button logic
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onPositiveButtonClick();
                        }
                    }
                });

        builder.setNegativeButton(negativeBtnText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // negative button logic
                        dialog.dismiss();
                        if (listener != null) {
                            listener.onNegativeButtonClicked();
                        }
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            }
        });

        dialog.show();
    }

    public static Dialog createDatePickerDialog(Context context, String title, String message, String okBtn, String cancelBtn) {
        Dialog dialog = new Dialog(context, R.style.AlertDialogTheme);
        dialog.setContentView(R.layout.layout_date_picker);
        dialog.setCancelable(false);
        TextView dialogHeader = (TextView) dialog.findViewById(R.id.tv_header);
        Button okButton = (Button) dialog.findViewById(R.id.btn_ok);
        Button cancelButton = (Button) dialog.findViewById(R.id.btn_cancel);
        dialogHeader.setText(title);
        okButton.setText(okBtn);
        cancelButton.setText(cancelBtn);
        return dialog;
    }

    public interface DialogClickListener {
        void onPositiveButtonClick();

        void onNegativeButtonClicked();
    }
}
