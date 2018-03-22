package com.app.dueday.maya;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.widget.Toast;

public class UIUtil {

    public static final String TAG = "MAYA: ";
    public static final String GOTO_DATE = "GOTODATE";
    private static UIUtil mUIUtilIns;

    public static UIUtil getInstance() {
        if (mUIUtilIns == null)
            mUIUtilIns = new UIUtil();

        return mUIUtilIns;
    }

    public void showToast(Context context, String message, int gravity) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    public void showToast(Context context, String message) {
        showToast(context, message, Gravity.CENTER);
    }

    public void showDialog(Context context, String title, String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_NoActionBar);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setCancelable(true);
        dialog.show();
    }
}
