package com.liu.climb.noteapp.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.liu.climb.noteapp.common.util.LogUtil;

/**
 * @author pd_liu on 2018/2/28.
 *         <p>
 *         所有Activity的基类
 *         </p>
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG_LOG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.w(TAG_LOG, "====================================================================================================");
        LogUtil.w(TAG_LOG, "||");



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.w(TAG_LOG, "||");
        LogUtil.w(TAG_LOG, "====================================================================================================");
    }
}
