package com.waracle.androidtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidParameterException;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {

    private static final String TAG = ImageLoader.class.getSimpleName();
    private ImageView imageView;

    public ImageLoader(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap == null) {
            return;
        } else {
            setImageView(imageView, bitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        return load(strings[0]);
    }

    /**
     * Simple function for loading a bitmap image from the web
     *
     * @param url       image url
     */
    public Bitmap load(String url) {
        if (TextUtils.isEmpty(url)) {
            throw new InvalidParameterException("URL is empty!");
        }

        // Can you think of a way to improve loading of bitmaps
        // that have already been loaded previously??

        try {
            return convertToBitmap(loadImageData(url));
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
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

    private static void setImageView(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
