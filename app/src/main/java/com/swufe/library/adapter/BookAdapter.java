package com.swufe.library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.swufe.library.R;
import com.swufe.library.bean.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    List<Book> mBookList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_bookItem_image;
        TextView tv_bookItem_name,tv_bookItem_author,tv_bookItem_publish;

        public ViewHolder (View view)
        {
            super(view);
            iv_bookItem_image = view.findViewById(R.id.iv_bookItem_image);
            tv_bookItem_name = view.findViewById(R.id.tv_bookItem_name);
            tv_bookItem_author = view.findViewById(R.id.tv_bookItem_author);
            tv_bookItem_publish = view.findViewById(R.id.tv_bookItem_publish);
        }

    }

    public  BookAdapter (List <Book> bookList){
        mBookList = bookList;
    }

    public interface OnItemClickListener{
        void onClick(int positon);
    }
    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    private OnItemLongClickListener longClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }



    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allbook,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Book book = mBookList.get(position);

        holder.tv_bookItem_name.setText(book.getName());
        holder.tv_bookItem_author.setText(book.getAuthor());
        holder.tv_bookItem_publish.setText(book.getPublish());

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
                if (longClickListener != null) {
                    longClickListener.onClick(position);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount(){
        if(mBookList == null){
            return 0;
        }
        return mBookList.size();
    }
}
