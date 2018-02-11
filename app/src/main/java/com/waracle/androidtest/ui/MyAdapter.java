package com.waracle.androidtest.ui;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.ImageLoader;
import com.waracle.androidtest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vijay on 11/02/2018.
 */
public class MyAdapter extends BaseAdapter {

    // Can you think of a better way to represent these items???
    private JSONArray mItems;
    private FragmentActivity activity;

    public MyAdapter(FragmentActivity activity) {
        this(activity, new JSONArray());
    }

    public MyAdapter(FragmentActivity activity, JSONArray items) {
        this.activity = activity;
        mItems = items;
    }

    @Override
    public int getCount() {
        return mItems.length();
    }

    @Override
    public Object getItem(int position) {
        try {
            return mItems.getJSONObject(position);
        } catch (JSONException e) {
            Log.e("", e.getMessage());
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View root = inflater.inflate(R.layout.list_item_layout, parent, false);
        if (root != null) {
            TextView title = (TextView) root.findViewById(R.id.title);
            TextView desc = (TextView) root.findViewById(R.id.desc);
            ImageView image = (ImageView) root.findViewById(R.id.image);
            try {
                JSONObject object = (JSONObject) getItem(position);
                title.setText(object.getString("title"));
                desc.setText(object.getString("desc"));
                new ImageLoader(image).execute(object.getString("image"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return root;
    }

    public void setItems(JSONArray items) {
        mItems = items;
    }
}
