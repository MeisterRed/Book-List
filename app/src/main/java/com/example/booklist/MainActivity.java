package com.example.booklist;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
BooksDBService.DBCallBackInterface{

    BottomNavigationView bottomNavigationView;

    BookListFragment home = new BookListFragment();
    SearchFragment search = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.books);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.books:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, home).commit();
                return true;
            case R.id.search:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, search).commit();
                return true;

        }
        return false;
    }

    @Override
    public void BookAdded() {

    }

    @Override
    public void listOfBooksFromDB(List<Book> list) {

    }

    @Override
    public void listOfSpecificBooksFromDB(List<Book> list) {

    }

    @Override
    public void bookFromDB(List<Book> list) {

    }

    @Override
    public void BookDeleted() {

    }
}
