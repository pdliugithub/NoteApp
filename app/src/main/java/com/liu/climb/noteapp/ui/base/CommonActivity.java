package com.liu.climb.noteapp.ui.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import com.liu.climb.noteapp.MainActivity;
import com.liu.climb.noteapp.R;
import com.liu.climb.noteapp.common.manager.SendMessageManager;
import com.liu.climb.noteapp.common.util.LogUtil;

/**
 * @author pd_liu on 2018/2/28.
 *         <p>
 *         Common activty used by extends...
 *         </p>
 */

public abstract class CommonActivity extends BaseActivity {

    /**
     * 本地广播进行通信
     */
    private SendMessageManager mSendMessageManager;

    /**
     * 广播接收者接受广播信息
     */
    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtil.e(TAG_LOG, "receiver" + intent.getAction());
            //接收广播消息
            receiverBroadcastMsg(intent);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);

            actionBar.setDisplayShowTitleEnabled(true);
        }

        /*
        注册通信机制
         */
        mSendMessageManager = new SendMessageManager(this);
        mSendMessageManager.registerBroadcastMsg(mBroadcastReceiver, this.getClass());

    }

    /**
     * 发送广播消息
     *
     * @param cls
     */
    protected void sendBroadcastMsg(Class cls) {
        mSendMessageManager.sendBroadcastMsg(cls);
    }

    /**
     * 此方法接受广播消息
     *
     * @param intent
     */
    protected void receiverBroadcastMsg(Intent intent) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //finish activity
        if (id == android.R.id.home) {
            finish();
            LogUtil.w(TAG_LOG, "退出当前页面");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //回收通信机制资源
        mSendMessageManager.unRegisterBroadcastMsg();
        mSendMessageManager = null;
    }
}
