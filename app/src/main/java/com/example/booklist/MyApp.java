package com.example.booklist;

import android.app.Application;

import java.util.ArrayList;

public class MyApp extends Application {
    BooksDBService dbService = new BooksDBService();
    APIService apiService = new APIService();
    BookJsonService jsonService = new BookJsonService();

    ArrayList<Book> bookList = new ArrayList<Book>(0);

    public BookJsonService getJsonService() {
        return jsonService;
    }

    public APIService getApiService(){
        return apiService;
    }
}
