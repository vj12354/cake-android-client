package com.waracle.androidtest.ui;

import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.waracle.androidtest.R;
import com.waracle.androidtest.data.Cake;
import com.waracle.androidtest.imageio.ImageCache;
import com.waracle.androidtest.imageio.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vijay on 11/02/2018.
 */
public class MyAdapter extends BaseAdapter {
    private List<Cake> mItems;
    private final ImageCache cache;
    private final LayoutInflater mInflater;

    public MyAdapter(FragmentActivity activity) {
        this(activity, new ArrayList<Cake>());
    }

    public MyAdapter(FragmentActivity activity, List<Cake> items) {
        mItems = items;
        cache = new ImageCache(activity);
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.list_item_layout, parent, false);
            holder = new ViewHolder();
            holder.titleTextView = (TextView) view.findViewById(R.id.title);
            holder.descTextView = (TextView) view.findViewById(R.id.desc);
            holder.urlImageView = (ImageView) view.findViewById(R.id.image);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        Cake cake = (Cake) getItem(position);

        holder.titleTextView.setText(cake.getTitle());
        holder.descTextView.setText(cake.getDesc());
//        if (cake.getUrl() == null || cake.getUrl().length() < 5) {
//            holder.urlImageView.setImageResource(R.drawable.ic_sync_problem_black_24dp);
//        } else {
            new ImageLoader(cache, holder.urlImageView).execute(cake.getUrl());
//        }

        return view;
    }

    public void setItems(List<Cake> items) {
        mItems = items;
    }

    static class ViewHolder {
        private TextView titleTextView;
        private TextView descTextView;
        private ImageView urlImageView;
    }
}
