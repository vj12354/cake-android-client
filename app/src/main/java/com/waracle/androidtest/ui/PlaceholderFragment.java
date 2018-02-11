package com.waracle.androidtest.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.waracle.androidtest.MainActivity;
import com.waracle.androidtest.R;

/**
 * Fragment is responsible for loading in some JSON and
 * then displaying a list of cakes with images.
 * Fix any crashes
 * Improve any performance issues
 * Use good coding practices to make code more secure
 */
public class PlaceholderFragment extends ListFragment {

    private static final String TAG = PlaceholderFragment.class.getSimpleName();

    private ListView mListView;
    protected MyAdapter mAdapter;

    public PlaceholderFragment() { /**/ }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create and set the list adapter.
        mAdapter = new MyAdapter(getActivity());
        mListView.setAdapter(mAdapter);
//        setListAdapter(mAdapter);

        refresh(MainActivity.JSON_URL);
    }

    public void refresh(String url) {
        // Load data from net.
        new DataLoader(this).execute(url);
    }

}
