package com.example.vinayakahebbar.doctorapp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.vinayakahebbar.doctorapp.interfaces.OnImageLoaded;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class ImageHelper {
    private OnImageLoaded onImageLoaded;
    public void loadBitmapFromURL(final String src) {
        new AsyncTask<Void, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                try {
                    URL url = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    return bitmap;
                } catch (IOException e) {
                    // Log exception
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                onImageLoaded.LoadImage(bitmap);
            }
        }.execute();
    }

    public void setOnImageLoaded(OnImageLoaded onImageLoaded) {
        this.onImageLoaded = onImageLoaded;
    }
}
