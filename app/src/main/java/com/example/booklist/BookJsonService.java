package com.example.booklist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BookJsonService {
    BooksDBService dbService;

    public ArrayList<Book> getBooksFromJSON(String json) {
        ArrayList<Book> bookList = new ArrayList<>(0);
        dbService = new BooksDBService();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++){
                JSONObject temp = items.getJSONObject(i);
                JSONObject temp2 = temp.getJSONObject("volumeInfo");
                String bookName = temp2.getString("title");
                String bookAuthor = temp2.getJSONArray("authors").getString(0);
                String bookDescription = temp2.getString("description");
                String bookImageURL = temp2.getJSONObject("imageLinks").getString("thumbnail");
                bookImageURL = bookImageURL.substring(0, 4) + "s" + bookImageURL.substring(4);

                Book b = new Book();
                b.setBookName(bookName);
                b.setBookAuthor(bookAuthor);
                b.setBookDescription(bookDescription);
                b.setBookImage(bookImageURL);
                b.setBookListStatus(false);
                bookList.add(b);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}
