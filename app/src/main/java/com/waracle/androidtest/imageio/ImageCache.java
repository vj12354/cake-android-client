package com.waracle.androidtest.imageio;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.waracle.androidtest.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.WeakHashMap;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageCache {
    private static final String TAG = ImageCache.class.getSimpleName();
    private Context context;
    private final WeakHashMap<String, Bitmap> urlBitmaps;
    private final WeakHashMap<String, String> localFilePaths;

    public ImageCache(Context context) {
        urlBitmaps = new WeakHashMap<>();
        localFilePaths = new WeakHashMap<>();
        this.context = context;
    }

    protected Bitmap getBitmap(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("URL is empty!");
        }
        if (urlBitmaps.containsKey(url)) {
            return urlBitmaps.get(url);
        }
        if (localFilePaths.containsKey(url)) {
            Bitmap bitmap = loadImageFromFile(localFilePaths.get(url));
            urlBitmaps.put(url, bitmap);
            return bitmap;
        }
        File imageFile = getImageFile(url);
        String absolutePath = imageFile.getAbsolutePath();
        if (imageFile.exists()) {
            Bitmap bitmap = loadImageFromFile(absolutePath);
            urlBitmaps.put(url, bitmap);
            localFilePaths.put(url, absolutePath);
            return bitmap;
        }
        try {
            byte[] imageBytes = loadImageData(url);
            storeImageBytesToFile(imageFile, imageBytes);
            Bitmap bitmap = convertToBitmap(imageBytes);

            localFilePaths.put(url, absolutePath);
            urlBitmaps.put(url, bitmap);
            return bitmap;
        } catch (IOException e) {
            Log.e(TAG, "Error while loading image: "+e.getMessage(), e);
            return null;
        }
    }

    private void storeImageBytesToFile(File imageFile, byte[] imageBytes) throws IOException {
        FileOutputStream fos = new FileOutputStream(imageFile);
        fos.write(imageBytes);
    }

    @NonNull
    private File getImageFile(String url) {
        File dir = context.getFilesDir();
        String name = url.substring(url.lastIndexOf('/')+1);
        return new File(dir, name);
    }

    private Bitmap loadImageFromFile(String file) {
        return BitmapFactory.decodeFile(file);
    }

    private static byte[] loadImageData(String url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        InputStream inputStream = null;
        try {
            try {
                // Read data from workstation
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                // Read the error from the workstation
                inputStream = connection.getErrorStream();
            }

            // Can you think of a way to make the entire
            // HTTP more efficient using HTTP headers??

            /*
            Answer: you could enable Http Response caching, to load from cache instead of downloading each time.
             */

            return StreamUtils.readUnknownFully(inputStream);
        } finally {
            // Close the input stream if it exists.
            StreamUtils.close(inputStream);

            // Disconnect the connection
            connection.disconnect();
        }
    }

    private static Bitmap convertToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}
