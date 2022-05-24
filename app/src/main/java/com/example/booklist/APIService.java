package com.example.booklist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class APIService {
    private String bookSearchURL= "https://www.googleapis.com/books/v1/volumes?q=";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface APIListener {
        void searchDataListener(String jsonString);
        void imageDataListener(Bitmap image);
    };

    APIListener listener;

    public void searchBookByTitle(String searchTerms){
        String url = bookSearchURL + searchTerms + "&maxResults=10";
        connect(url, false);
    }

    public void getBookImage(String imageURL) {
        connect(imageURL, true);
    }

    public void connect(String url, boolean imageOnly){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                try {
                    String jsonString = "";
                    URL urlObject = new URL(url);
                    httpURLConnection = (HttpURLConnection)urlObject.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setRequestProperty("Content-Type","application/json");

                    InputStream in = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);
                    if (imageOnly == true){
                        listener.imageDataListener(BitmapFactory.decodeStream(in));
                        notify();
                    }
                    else {
                        int inputStreamData = 0;
                        while ((inputStreamData = reader.read()) != -1) {
                            char current = (char) inputStreamData;
                            jsonString += current;
                        }

                        final String finalJsonString = jsonString;
                        networkingHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.searchDataListener(finalJsonString);
                            }
                        });
                    }

                } catch (MalformedURLException | ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    httpURLConnection.disconnect();
                }
            }
        });
    }
}
