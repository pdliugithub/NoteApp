package com.liu.climb.noteapp.common.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.text.TextUtils;

/**
 * @author pd_liu on 2018/2/28.
 *         <p>
 *         String 工具类
 *         </p>
 *         <p>
 *         Usage:
 *         {@link #getStringById(Context, int)}
 *         </p>
 */

public class StringUtil {

    private static final String TAG_LOG = "StringUtil";

    private StringUtil() {
    }

    /**
     * 通过String resource id get string object.
     *
     * @param context    context
     * @param resourceID resource id
     * @return string
     */
    public static String getStringById(Context context, @StringRes int resourceID) {
        return context.getResources().getString(resourceID);
    }

    /**
     * 判断一个对象是否为空
     *
     * @param object object
     * @return true if object is empty; false if object is not empty.
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        } else if (object instanceof String) {
            String temp = (String) object;
            return TextUtils.isEmpty(temp);
        } else
            return false;
    }
}
