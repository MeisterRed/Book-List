package com.example.booklist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class AllBooksFragment extends Fragment implements BooksDBService.DBCallBackInterface {

    ArrayList<Book> books;
    RecyclerView listDisplay;
    BookListAdapter adapter;
    BooksDBService dbService;

    public AllBooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbService = ((MyApp)getActivity().getApplication()).dbService;
        dbService.getInstance(getActivity());
        dbService.listener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_books, container, false);
        listDisplay = view.findViewById(R.id.All_Books);
        listDisplay.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new BookListAdapter(getActivity(), new ArrayList<>(0));
        listDisplay.setAdapter(adapter);

        dbService.getAllBook();

        books = ((MyApp)getActivity().getApplication()).bookList;
        adapter.bookList = books;

        return view;
    }

    @Override
    public void BookAdded() {

    }

    @Override
    public void listOfBooksFromDB(List<Book> list) {
        adapter.bookList = new ArrayList<>(list);
        adapter.notifyDataSetChanged();

        //dbService.getAllBook();
    }

    @Override
    public void listOfSpecificBooksFromDB(List<Book> list) {
        adapter.bookList = new ArrayList<>(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void bookFromDB(List<Book> list) {

    }

    @Override
    public void BookDeleted() {
        dbService.getAllBook();
    }
}