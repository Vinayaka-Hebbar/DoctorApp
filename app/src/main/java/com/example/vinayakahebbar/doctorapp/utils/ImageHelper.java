package com.example.vinayakahebbar.doctorapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.vinayakahebbar.doctorapp.interfaces.OnImageLoaded;
import com.example.vinayakahebbar.doctorapp.utils.cache.FileCache;
import com.example.vinayakahebbar.doctorapp.utils.cache.MemoryCache;
import com.example.vinayakahebbar.doctorapp.utils.io.FileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Vinayaka Hebbar on 09-01-2018.
 */

public class ImageHelper {
    private long size = 0;//current allocated size
    private long limit = 1000000;//max memory in bytes


    private FileCache fileCache;
    private static final MemoryCache memoryCache = new MemoryCache();

    private OnImageLoaded onImageLoaded;

    private ExecutorService executorService;

    public ImageHelper(){
        setLimit(Runtime.getRuntime().maxMemory()/4);
    }

    public ImageHelper(Context context){
        fileCache=new FileCache(context);
        setLimit(Runtime.getRuntime().maxMemory()/4);
        executorService= Executors.newFixedThreadPool(5);
    }

    public void setLimit(long new_limit) {
        limit = new_limit;
    }

    public void loadBitmapFromURL(final String src) {
        new AsyncTask<Void, Integer, Bitmap>() {
            @Override
            protected Bitmap doInBackground(Void... params) {
                //from web
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
                memoryCache.put(src,bitmap);
            }

            @Override
            protected void onPreExecute() {
                Bitmap bitmap = memoryCache.get(src);
                if(bitmap != null) {
                    onImageLoaded.LoadImage(bitmap);
                    cancel(true);
                }
            }
        }.execute();
    }

    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException ignored) {}
        return null;
    }

    public void setOnImageLoaded(OnImageLoaded onImageLoaded) {
        this.onImageLoaded = onImageLoaded;
    }
}
