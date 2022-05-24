package com.example.booklist;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(version = 4, entities = {Book.class})
abstract public class BooksDB extends RoomDatabase {
    public abstract BookDAO getDao();
}
