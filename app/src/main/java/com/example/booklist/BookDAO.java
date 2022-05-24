package com.example.booklist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDAO {
    @Insert
    void insertNewBook(Book b);

    @Delete
    void deleteBook(Book b);

    @Query("Select * from Book where category = :category")
    List<Book> getSpecificBooks(String category);

    @Query("Select * from Book")
    List<Book> getAllBooks();

    @Query("Select * from Book where name = :name")
    List<Book> getBook(String name);
}
