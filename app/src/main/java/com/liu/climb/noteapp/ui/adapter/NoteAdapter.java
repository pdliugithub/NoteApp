package com.liu.climb.noteapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liu.climb.noteapp.R;
import com.liu.climb.noteapp.data.source.NoteSource;

import java.util.ArrayList;

/**
 * @author pd_liu on 2018/3/1.
 *         <p>
 *         笔记列表适配器
 *         </p>
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.Holder> {

    private ArrayList<NoteSource> notes;

    private View.OnClickListener mOnClick;

    public NoteAdapter(ArrayList<NoteSource> notes) {
        this.notes = notes;
    }

    public NoteAdapter(ArrayList<NoteSource> notes, View.OnClickListener onClickListener) {
        this.notes = notes;
        this.mOnClick = onClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_recycler_item, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        NoteSource noteSource = notes.get(position);

        holder.itemView.setTag(holder.getLayoutPosition());
        holder.content.setTag(holder.getLayoutPosition());

        holder.content.setText(String.valueOf(noteSource.getText()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


    class Holder extends RecyclerView.ViewHolder {

        private TextView content;

        public Holder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.item_note_content);
            content.setOnClickListener(mOnClick);
        }
    }
}
