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

public class CurrentReadFragment extends Fragment implements BooksDBService.DBCallBackInterface {

    ArrayList<Book> books;
    RecyclerView listDisplay;
    SearchResultsAdapter adapter;
    BooksDBService dbService;

    public CurrentReadFragment() {
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
        View view = inflater.inflate(R.layout.fragment_current_read, container, false);

        listDisplay = view.findViewById(R.id.recyclerView3);
        listDisplay.setLayoutManager(new LinearLayoutManager(view.getContext()));
        adapter = new SearchResultsAdapter(getActivity(), new ArrayList<>(0));
        listDisplay.setAdapter(adapter);

        dbService.sortBooks("Currently Reading");

        books = ((MyApp)getActivity().getApplication()).bookList;
        adapter.bookList = books;

        return view;
    }

    @Override
    public void BookAdded() {

    }

    @Override
    public void listOfBooksFromDB(List<Book> list) {

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
        dbService.sortBooks("Currently Reading");
    }
}