package com.example.booklist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchResultsAdapter extends RecyclerView.Adapter<SearchResultsAdapter.ViewHolder> {


    private static Context context;
    public static ArrayList<Book> bookList = new ArrayList<>();

    public SearchResultsAdapter(Context context, ArrayList<Book> bookList){
        this.context = context;
        this.bookList = bookList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder { //implements View.OnClickListener {
        private final TextView textName;
        private final TextView textAuthor;

        public ViewHolder(View itemView) {
            super(itemView);
            textName = (TextView) itemView.findViewById(R.id.textView);
            textAuthor = (TextView) itemView.findViewById(R.id.textView2);

        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_results_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book b = bookList.get(position);
        holder.textName.setText(bookList.get(position).name);
        holder.textAuthor.setText(bookList.get(position).author);
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

