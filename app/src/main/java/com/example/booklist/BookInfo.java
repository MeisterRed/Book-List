package com.example.booklist;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BookInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
 APIService.APIListener {

    TextView title;
    TextView author;
    TextView desc;
    ImageView img;
    Button add;
    Spinner dropdown;
    Book book;
    Bitmap tempImage;
    BooksDBService dbService;
    APIService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        dbService = ((MyApp)getApplication()).dbService;
        dbService.getInstance(this);
        apiService = ((MyApp)getApplication()).getApiService();
        apiService.listener = this;
        tempImage = null;

        title = (TextView) findViewById(R.id.textView3);
        author = (TextView) findViewById(R.id.textView4);
        desc = (TextView) findViewById(R.id.textView5);
        dropdown = (Spinner) findViewById(R.id.spinner2);
        add = (Button) findViewById(R.id.button);
        img = (ImageView) findViewById(R.id.imageView);

        book = (Book) getIntent().getSerializableExtra("book");
        add.setText("Add");

        if (book.added == true) {
            add.setBackgroundColor(Color.parseColor("#FF0000"));
            add.setText("Delete");
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dbService.deleteBook(book);
                    Toast.makeText(getApplicationContext(), "Book has been removed from the list", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            dropdown.setVisibility(View.GONE);
        }

        dropdown.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.list_add, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        dropdown.setAdapter(adapter);

        title.setText(book.name);
        author.setText("By: " + book.author);
        desc.setText(book.description);
        desc.setMovementMethod(new ScrollingMovementMethod());
        tempImage = null;
        apiService.getBookImage(book.image);
        synchronized (this) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        img.setImageBitmap(tempImage);


       /* try {
            URL newurl = new URL(book.image);
            img.setImageBitmap(BitmapFactory.decodeStream(newurl.openConnection().getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(i) {
            case 0:
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbService.insertNewBook(book.name, book.author, book.description, "Completed", book.image, true);
                        Toast.makeText(getApplicationContext(), "Book has been added to the Completed category", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                return;
            case 1:
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbService.insertNewBook(book.name, book.author, book.description, "Currently Reading", book.image, true);
                        Toast.makeText(getApplicationContext(), "Book has been added to the Completed category", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                return;
            case 2:
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dbService.insertNewBook(book.name, book.author, book.description, "Dropped", book.image, true);
                        Toast.makeText(getApplicationContext(), "Book has been added to the Dropped category", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void searchDataListener(String jsonString) {

    }

    @Override
    public void imageDataListener(Bitmap image) {
        tempImage = image;
        synchronized (this) {
            notify();
        }
    }
}