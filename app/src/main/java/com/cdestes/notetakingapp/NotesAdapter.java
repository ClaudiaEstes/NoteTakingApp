package com.cdestes.notetakingapp;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter <NotesAdapter.MyViewHolder> {

    private List<Notes> notesList;
    private CustomItemClickListener listener;

    public class MyViewHolder extends ViewHolder {
        public TextView title;//, content;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            //content = view.findViewById(R.id.content);
        }
    }

    public NotesAdapter(List <Notes> notesList) {
        this.notesList = notesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row, parent, false);
        final ViewHolder mViewHolder = new MyViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, mViewHolder.getPosition());
            }
        });

        return (MyViewHolder) mViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Notes note = notesList.get(position);
        Log.i("onBindViewHolder", "position"+position);
        holder.title.setText(note.getTitle());
        //holder.content.setText(note.getContent());
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
}