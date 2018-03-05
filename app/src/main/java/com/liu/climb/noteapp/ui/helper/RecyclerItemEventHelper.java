package com.liu.climb.noteapp.ui.helper;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author pd_liu on 2018/3/5.
 *         <p>
 *         RecyclerView item event helper.
 *         </p>
 *         @deprecated 已废弃请查看 {@link RecyclerItemClickSupport}
 */

public class RecyclerItemEventHelper extends RecyclerView.SimpleOnItemTouchListener {

    private static final String TAG_LOG = "RecyclerItemEventHelper";

    private RecyclerView mRecyclerView;

    /**
     * 点击事件
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * 长按事件
     */
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 手势检测
     */
    private GestureDetectorCompat mGestureDetector;


    public RecyclerItemEventHelper(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), mSimpleOnGestureListener);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    /**
     * 手势检测处理点击、长按事件
     */
    private GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mOnItemClickListener != null && mGestureDetector.onTouchEvent(e)) {
                mOnItemClickListener.onItemClick(childView, mRecyclerView.getChildAdapterPosition(childView));
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            View childView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mOnItemLongClickListener != null) {
                mOnItemLongClickListener.onItemLongClick(childView,
                        mRecyclerView.getChildAdapterPosition(childView));
            }
        }
    };
}
