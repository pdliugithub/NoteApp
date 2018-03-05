package com.liu.climb.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.liu.climb.noteapp.common.util.LogUtil;
import com.liu.climb.noteapp.common.util.SharedPreferenceUtil;
import com.liu.climb.noteapp.common.util.StringUtil;
import com.liu.climb.noteapp.common.util.ToastUtil;
import com.liu.climb.noteapp.data.source.NoteSource;
import com.liu.climb.noteapp.presenter.factory.StartActivityFactory;
import com.liu.climb.noteapp.ui.activity.EditNoteActivity;
import com.liu.climb.noteapp.ui.adapter.NoteAdapter;
import com.liu.climb.noteapp.ui.base.CommonActivity;
import com.liu.climb.noteapp.ui.helper.RecyclerItemClickSupport;
import com.liu.climb.noteapp.ui.helper.RecyclerItemEventHelper;

import java.util.ArrayList;

/**
 * @author pd_liu 2018年2月24日
 *         <p>
 *         集成一款日常记录事件的流程以及完成的程度功能的App
 *         </p>
 */
public class MainActivity extends CommonActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrayList<NoteSource> mNoteSources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //before super.onCreate()
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.icon_home);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                intent.putExtra(EditNoteActivity.ENTER_TYPE, EditNoteActivity.ENTER_TYPE_CREATED);
                StartActivityFactory.startActivity(MainActivity.this, intent);
            }
        });
        mDrawerLayout = findViewById(R.id.drawer_layout);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

//        RecyclerItemClickSupport support = RecyclerItemClickSupport.addTo(mRecyclerView);
//        support.setOnItemClickListener(new RecyclerItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                LogUtil.e(TAG_LOG, "position:"+mNoteSources.get(position).getText());
//                ToastUtil.show(MainActivity.this, "position"+mNoteSources.get(position).getText());
//            }
//        });



        mSwipeRefreshLayout = findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setEnabled(true);

        mSwipeRefreshLayout.setOnRefreshListener(mOnRefresh);

        mSwipeRefreshLayout.setRefreshing(true);
        mOnRefresh.onRefresh();
    }

    /**
     * 刷新数据
     */
    SwipeRefreshLayout.OnRefreshListener mOnRefresh = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            mNoteSources = SharedPreferenceUtil.restore(MainActivity.this);
            if (!StringUtil.isEmpty(mNoteSources)) {
                NoteAdapter noteAdapter = new NoteAdapter(mNoteSources, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = ((int)v.getTag());
                        Intent intent = new Intent(MainActivity.this, EditNoteActivity.class);
                        intent.putExtra(EditNoteActivity.ENTER_TYPE, EditNoteActivity.ENTER_TYPE_EDITOR);
                        intent.putExtra(EditNoteActivity.NOTE_CREATE_DATE, mNoteSources.get(position).getCreateDate());
                        StartActivityFactory.startActivity(MainActivity.this, intent);
                    }
                });
                mRecyclerView.setAdapter(noteAdapter);
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
    };


    @Override
    protected void receiverBroadcastMsg(Intent intent) {
        super.receiverBroadcastMsg(intent);

        mSwipeRefreshLayout.setRefreshing(true);
        mOnRefresh.onRefresh();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.title) {
            ToastUtil.show(this, "点击了");
            return true;
        }

        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(Gravity.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
