package com.swufe.library.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swufe.library.R;
import com.swufe.library.bean.BorrowItem;
import com.swufe.library.bean.Lend;

import java.util.List;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.ViewHolder>{
    List<BorrowItem> borrowItemList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView deadline, book, borrow, back;
        public ViewHolder(View view){
            super(view);
            deadline = view.findViewById(R.id.tv_borrowItem_deadline);
            book = view.findViewById(R.id.tv_borrowItem_book);
            borrow = view.findViewById(R.id.tv_borrowItem_lend);
            back = view.findViewById(R.id.tv_borrowItem_back);
        }
    }

    public BorrowAdapter(List<BorrowItem> borrowItemList){
        this.borrowItemList = borrowItemList;
    }



    private BookAdapter.OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(BookAdapter.OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrow,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        BorrowItem item = borrowItemList.get(position);
        holder.deadline.setText(item.getDeadline());
        holder.book.setText(item.getBookName());
        holder.borrow.setText(item.getBorrow_date());
        holder.back.setText(item.getBack_date());

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
    public int getItemCount() {
        return borrowItemList.size();
    }


}

