package com.example.booklist;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BooksDBService {

    interface DBCallBackInterface{
        void BookAdded();
        void listOfBooksFromDB(List<Book> list);
        void listOfSpecificBooksFromDB(List<Book> list);
        void bookFromDB(List<Book> list);
        void BookDeleted();
    }

    BooksDB db;
    DBCallBackInterface listener;
    ExecutorService dbExecutor = Executors.newFixedThreadPool(4);
    Handler dbHandler = new Handler(Looper.getMainLooper());

    public BooksDB getInstance(Context context){
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                    BooksDB.class, "personal_book_list").fallbackToDestructiveMigration().build();
        }
        return db;
    }

    public void insertNewBook(String name, String author, String description, String category, String image, boolean added){
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().insertNewBook( new Book(name,author,description,category,image,added));
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.BookAdded();
                    }
                });
            }
        });
    }

    public void deleteBook(Book book) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.getDao().deleteBook(book);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.BookDeleted();
                    }
                });
            }
        });
    }

    public void getAllBook() {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Book> list = db.getDao().getAllBooks();
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.listOfBooksFromDB(list);
                    }
                });

            }
        });
    }

    public void getBook(String title) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Book> list = db.getDao().getBook(title);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.bookFromDB(list);
                    }
                });

            }
        });
    }

    public void sortBooks(String category) {
        dbExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Book> list = db.getDao().getSpecificBooks(category);
                dbHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.listOfSpecificBooksFromDB(list);
                    }
                });

            }
        });
    }
}
