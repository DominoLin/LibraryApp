package com.swufe.library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swufe.library.R;
import com.swufe.library.bean.Book;


import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {

    List<Book> bookList;

    public CollectionAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView number,name,author,publish;
        public ViewHolder(View view) {
            super(view);
            number = view.findViewById(R.id.tv_collection_number);
            name = view.findViewById(R.id.tv_collection_name);
            author = view.findViewById(R.id.tv_collection_author);
            publish = view.findViewById(R.id.tv_collection_publish);
        }
    }

    private BookAdapter.OnItemClickListener listener;
    public void setOnItemClickListener(BookAdapter.OnItemClickListener listener){
        this.listener = listener;
    }

    private BookAdapter.OnItemLongClickListener longClickListener;
    public void setOnItemLongClickListener(BookAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Book book = bookList.get(position);
        holder.number.setText(String.valueOf(book.getNumber()));
        holder.name.setText(book.getName());
        holder.author.setText(book.getAuthor());
        holder.publish.setText(book.getPublish());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(longClickListener != null){
                    longClickListener.onClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        if(bookList == null){
            return 0;
        }
        return bookList.size();
    }


}
