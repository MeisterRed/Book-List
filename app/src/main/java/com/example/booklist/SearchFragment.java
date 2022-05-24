package com.example.booklist;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements APIService.APIListener {

    ArrayList<Book> results = new ArrayList<Book>();
    APIService apiService;
    BookJsonService jsonService;
    SearchView bookSearchBar;
    SearchResultsAdapter adapter;
    RecyclerView resultsDisplay;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        apiService = ((MyApp)getActivity().getApplication()).getApiService();
        jsonService = ((MyApp)getActivity().getApplication()).getJsonService();
        apiService.listener = this;
        adapter = new SearchResultsAdapter(getActivity(), results);

        resultsDisplay = view.findViewById(R.id.recyclerView);
        resultsDisplay.setLayoutManager(new LinearLayoutManager(view.getContext()));
        resultsDisplay.setAdapter(adapter);

        bookSearchBar = (SearchView) view.findViewById(R.id.bookSearchBar);
        bookSearchBar.setQueryHint("Search for Book Title Here");
        bookSearchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("query", s);
                apiService.searchBookByTitle(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }

    @Override
    public void searchDataListener(String jsonString) {
        results = jsonService.getBooksFromJSON(jsonString);
        adapter = new SearchResultsAdapter(getActivity(), results);
        resultsDisplay.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void imageDataListener(Bitmap image) {

    }
}