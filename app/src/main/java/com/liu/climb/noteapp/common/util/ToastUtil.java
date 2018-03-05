package com.liu.climb.noteapp.common.util;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.liu.climb.noteapp.R;

import java.lang.ref.SoftReference;

/**
 * @author pd_liu on 2018/3/1.
 *         <p>
 *         吐司工具类
 *         </p>
 */

public class ToastUtil {

    private static final String TAG_LOG = "ToastUtil";

    private static SoftReference<Context> mContextApp;

    private static SoftReference<Toast> mToast;

    private ToastUtil() {
    }

    public static void initToastApp(Context appContext){
        mContextApp = new SoftReference<Context>(appContext);
    }

    public static void show(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showApp(String msg){

        Context context = mContextApp.get();

        if(context != null){
            if(TextUtils.isEmpty(msg)){
                msg = StringUtil.getStringById(context, R.string.toast_empty_tip);
            }

            toast(context, msg);
        }
    }

    private static final void toast(Context context, String msg){
        if(mToast == null){
            mToast = new SoftReference<Toast>(Toast.makeText(context, msg, Toast.LENGTH_LONG));
        }
        Toast toast = mToast.get();
        if(toast != null){
            toast.setText(msg);
            toast.show();
        }
    }
}
