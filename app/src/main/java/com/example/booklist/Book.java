package com.example.booklist;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Book implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    String name;
    String author;
    String description;
    String category;
    String image;
    Boolean added;

    public Book(String name, String author, String description, String category, String image, Boolean added) {
        this.name = name;
        this.author = author;
        this.description = description;
        this.category = category;
        this.image = image;
        this.added = added;
    }

    public Book() {

    }

    public void setBookName(String name) {
        this.name = name;
    }

    public void setBookAuthor(String author){
        this.author = author;
    }

    public void setBookDescription(String description) {
        this.description = description;
    }

    public void setBookImage(String image) { this.image = image; }

    public void setBookListStatus(Boolean status) {this.added = status; }
}
