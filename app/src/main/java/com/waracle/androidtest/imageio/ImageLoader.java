package com.waracle.androidtest.imageio;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.waracle.androidtest.R;

import java.lang.ref.WeakReference;

/**
 * Created by Riad on 20/05/2015.
 */
public class ImageLoader extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;
    private final ImageCache imageCache;

    public ImageLoader(ImageCache imageCache, ImageView imageView) {
        this.imageCache = imageCache;
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap == null) {
            imageViewWeakReference.get().setImageResource(R.drawable.ic_sync_problem_black_24dp);
        } else {
            setImageView(imageViewWeakReference.get(), bitmap);
        }
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        return imageCache.getBitmap(url);
    }

    private static void setImageView(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
