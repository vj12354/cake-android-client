package com.waracle.androidtest.ui;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.waracle.androidtest.StreamUtils;
import com.waracle.androidtest.data.Cake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 11/02/2018.
 */ // Async Task to perform data loading from network.
class DataLoader extends AsyncTask<String, Void, List<Cake>> {

    private static final String TAG = DataLoader.class.getSimpleName();

    private final PlaceholderFragment placeholderFragment;

    public DataLoader(PlaceholderFragment placeholderFragment) {
        this.placeholderFragment = placeholderFragment;
    }

    @Override
    protected List<Cake> doInBackground(String... strings) {
        try {
            JSONArray jsonArray = loadData(strings[0]);
            return getJsonArrayAsList(jsonArray);
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Cake> array) {
        super.onPostExecute(array);
        try {
            if (array == null) {
                Toast.makeText(placeholderFragment.getActivity(),
                        "Unable retrieve JSON data..", Toast.LENGTH_SHORT).show();
            } else {
                placeholderFragment.mAdapter.setItems(array);
                placeholderFragment.mAdapter.notifyDataSetChanged();
            }
            placeholderFragment.setListShown(true);
        } catch (Exception e) {
            Log.d(TAG, "Error: "+e.getMessage());
        }
    }

    @NonNull
    private List<Cake> getJsonArrayAsList(JSONArray jsonArray) throws JSONException {
        List<Cake> cakes = new ArrayList<>();
        for (int index = 0; index < jsonArray.length(); index++) {
            JSONObject object = jsonArray.getJSONObject(index);
            Cake cake = new Cake();
            cake.setTitle(object.getString("title"));
            cake.setDesc(object.getString("desc"));
            cake.setUrl(object.getString("image"));
            cakes.add(cake);
        }
        return cakes;
    }

    private JSONArray loadData(String jsonURL) throws IOException, JSONException {
        URL url = new URL(jsonURL);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            // Can you think of a way to improve the performance of loading data
            // using HTTP headers???
            /*
            Answer:
                You could enable Http Response caching, to load from cache instead of downloading from network each time.

            Also Would suggest using library such as retrofit, volley, OkHttp to retrieve JSON data into a List<Cake>
            that would be a lot simpler.
             */

            // Also, Do you trust any utils thrown your way????


            /* Answer: Not sure */
            byte[] bytes = StreamUtils.readUnknownFully(in);

            // Read in charset of HTTP content.
            String charset = parseCharset(urlConnection.getRequestProperty("Content-Type"));

            // Convert byte array to appropriate encoded string.
            String jsonText = new String(bytes, charset);

            // Read string as JSON.
            return new JSONArray(jsonText);
        } finally {
            urlConnection.disconnect();
        }
    }

    /**
     * Returns the charset specified in the Content-Type of this header,
     * or the HTTP default (ISO-8859-1) if none can be found.
     */
    private String parseCharset(String contentType) {
        if (contentType != null) {
            String[] params = contentType.split(",");
            for (int i = 1; i < params.length; i++) {
                String[] pair = params[i].trim().split("=");
                if (pair.length == 2) {
                    if (pair[0].equals("charset")) {
                        return pair[1];
                    }
                }
            }
        }
        return "UTF-8";
    }
}
