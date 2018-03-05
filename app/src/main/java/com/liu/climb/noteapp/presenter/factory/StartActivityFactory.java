package com.liu.climb.noteapp.presenter.factory;

import android.content.Context;
import android.content.Intent;

/**
 * @author pd_liu on 2018/2/28.
 *         <p>
 *         Activity跳转间的工厂类
 *         </p>
 */

public class StartActivityFactory {

    private static final String TAG_LOG = "StartActivityFactory";

    public static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

}
