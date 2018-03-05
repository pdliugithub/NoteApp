package com.liu.climb.noteapp.common.util;

import android.util.Log;

import com.liu.climb.noteapp.common.config.EnvironmentConfig;

/**
 * @author pd_liu on 2018/2/27.
 *         <p>
 *         日志工具类
 *         </p>
 */

public class LogUtil {

    private static final String TAG_LOG = "LogUtil";

    private LogUtil() {
    }

    public static void e(String tag, String message) {
        if (EnvironmentConfig.sDebug) {
            Log.e(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (EnvironmentConfig.sDebug) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (EnvironmentConfig.sDebug) {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (EnvironmentConfig.sDebug) {
            Log.w(tag, message);
        }
    }
}
