package com.liu.climb.noteapp.ui.helper;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.liu.climb.noteapp.R;

/**
 * @author pd_liu on 2018/3/5.
 *         <p>
 *         RecyclerView item click event supported.
 *         </p>
 *         <p>
 *         Usage:
 *         支持点击 {@link #setOnItemClickListener(OnItemClickListener)}
 *         支持长按 {@link #setOnItemLongClickListener(OnItemLongClickListener)}
 *         </p>
 *         <p>
 *         不兼容:
 *         如果您想要设置Item的点击事件、Item内部某一控件的点击事件，请不要选择我.
 *         </p>
 */

public class RecyclerItemClickSupport {

    private final RecyclerView mRecyclerView;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    /**
     * 单击事件
     */
    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    /**
     * 长按事件
     */
    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mOnItemLongClickListener != null) {
                RecyclerView.ViewHolder holder = mRecyclerView.getChildViewHolder(v);
                return mOnItemLongClickListener.onItemLongClicked(mRecyclerView, holder.getAdapterPosition(), v);
            }
            return false;
        }
    };

    private RecyclerView.OnChildAttachStateChangeListener mAttachListener
            = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (mOnItemClickListener != null) {
                view.setOnClickListener(mOnClickListener);
            }
            if (mOnItemLongClickListener != null) {
                view.setOnLongClickListener(mOnLongClickListener);
            }
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {
        }
    };

    private RecyclerItemClickSupport(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.setTag(R.id.item_click_support, this);
        mRecyclerView.addOnChildAttachStateChangeListener(mAttachListener);
    }

    public static RecyclerItemClickSupport addTo(RecyclerView view) {
        RecyclerItemClickSupport support = (RecyclerItemClickSupport) view.getTag(R.id.item_click_support);
        if (support == null) {
            support = new RecyclerItemClickSupport(view);
        }
        return support;
    }

    public static RecyclerItemClickSupport removeFrom(RecyclerView view) {
        RecyclerItemClickSupport support = (RecyclerItemClickSupport) view.getTag(R.id.item_click_support);
        if (support != null) {
            support.detach(view);
        }
        return support;
    }

    public RecyclerItemClickSupport setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
        return this;
    }

    public RecyclerItemClickSupport setOnItemLongClickListener(OnItemLongClickListener listener) {
        mOnItemLongClickListener = listener;
        return this;
    }

    private void detach(RecyclerView view) {
        view.removeOnChildAttachStateChangeListener(mAttachListener);
        view.setTag(R.id.item_click_support, null);
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClicked(RecyclerView recyclerView, int position, View v);
    }
}
