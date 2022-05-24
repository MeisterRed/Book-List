package com.example.booklist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder>{
    private static Context context;
    public static ArrayList<Book> bookList = new ArrayList<>();

    public BookListAdapter(Context context, ArrayList<Book> bookList){
        this.context = context;
        this.bookList = bookList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{ //implements View.OnClickListener {
        private final TextView textName;
        private final TextView textAuthor;
        private final TextView textCategory;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.BookName2);
            textAuthor = (TextView) itemView.findViewById(R.id.BookAuthor2);
            textCategory = (TextView) itemView.findViewById(R.id.Category2);



        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_books_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book b = bookList.get(position);
        holder.textName.setText(bookList.get(position).name);
        holder.textAuthor.setText(bookList.get(position).author);
        holder.textCategory.setText(bookList.get(position).category);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookInfo.class);
                intent.putExtra("book",bookList.get(holder.getLayoutPosition()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }
}
