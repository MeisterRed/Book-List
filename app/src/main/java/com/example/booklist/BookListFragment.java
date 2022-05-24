package com.example.booklist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class BookListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public BookListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        Spinner dropdown = (Spinner) view.findViewById(R.id.spinner);
        dropdown.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.list_sort, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.setReorderingAllowed(true);

        switch (i) {
            case 0:
                Fragment allBookFragment = new AllBooksFragment();
                transaction.replace(R.id.fragmentContainerView, allBookFragment).commit();
                return;
            case 1:
                Fragment completedFragment = new CompletedFragment();
                transaction.replace(R.id.fragmentContainerView, completedFragment).commit();
                return;
            case 2:
                Fragment currentReadFragment = new CurrentReadFragment();
                transaction.replace(R.id.fragmentContainerView, currentReadFragment).commit();
                return;
            case 3:
                Fragment droppedFragment = new DroppedFragment();
                transaction.replace(R.id.fragmentContainerView, droppedFragment).commit();
                return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}