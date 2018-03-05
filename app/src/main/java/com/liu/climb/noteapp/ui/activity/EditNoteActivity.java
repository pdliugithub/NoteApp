package com.liu.climb.noteapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.MenuItem;

import com.liu.climb.noteapp.MainActivity;
import com.liu.climb.noteapp.R;
import com.liu.climb.noteapp.common.util.LogUtil;
import com.liu.climb.noteapp.common.util.SharedPreferenceUtil;
import com.liu.climb.noteapp.common.util.StringUtil;
import com.liu.climb.noteapp.data.source.NoteSource;
import com.liu.climb.noteapp.ui.base.CommonActivity;

public class EditNoteActivity extends CommonActivity {

    public static final String ENTER_TYPE = "enter_type";
    public static final String NOTE_CREATE_DATE = "date";

    public static final String ENTER_TYPE_CREATED = "created";

    public static final String ENTER_TYPE_EDITOR = "editor";

    private String mCreateDate;

    private NoteSource mNoteSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_note);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String type = intent.getStringExtra(ENTER_TYPE);

        switch (type) {
            case ENTER_TYPE_CREATED:
                mNoteSource = new NoteSource();
                mCreateDate = String.valueOf(System.currentTimeMillis());

                mNoteSource.setCreateDate(mCreateDate);
                break;

            case ENTER_TYPE_EDITOR:
                mCreateDate = intent.getStringExtra(NOTE_CREATE_DATE);
                TextInputEditText input = findViewById(R.id.note_content);

                SharedPreferenceUtil.printData(this);
                mNoteSource = SharedPreferenceUtil.findNoteByCrateDate(EditNoteActivity.this, mCreateDate);
                LogUtil.e(TAG_LOG, "查找的数据：" + mNoteSource.toString());
                if (mNoteSource != null) {
                    input.setText(mNoteSource.getText());
                }

                break;
        }
        LogUtil.e(TAG_LOG, "intent == null" + (intent == null));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            saveNote();
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        saveNote();
        super.onBackPressed();
    }

    /**
     * 保存笔记内容
     */
    private void saveNote() {
        /*
        进行保存笔记,成功后，退出
        */
        TextInputEditText input = findViewById(R.id.note_content);

        String content = input.getText().toString().trim();
        if (!StringUtil.isEmpty(content)) {
            setTitle("保存中");
            mNoteSource.setCreateDate(mCreateDate);
            mNoteSource.setText(content);
            if (SharedPreferenceUtil.save(this, mNoteSource)) {
                /*
                保存成功后进行退出
                 */
                setTitle("保存成功");
            } else {
                setTitle("请重试");
            }
        }

        //通知首页刷新数据
        sendBroadcastMsg(MainActivity.class);
    }
}
