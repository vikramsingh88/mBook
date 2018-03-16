package vik.com.mbooks.utils;

import android.app.ProgressDialog;
import android.content.Context;

import vik.com.mbooks.R;

/**
 * Created by M1032130 on 2/22/2018.
 */

public class CustomProgressDialog {

    public static ProgressDialog getProgressDialog(Context context, String text) {
        ProgressDialog progress=new ProgressDialog(context, R.style.CustomProgressDialogStyle);
        progress.setMessage(text);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        return progress;
    }
}
