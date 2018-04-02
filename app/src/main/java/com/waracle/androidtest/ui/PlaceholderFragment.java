package com.waracle.androidtest.ui;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

/**
 * Fragment is responsible for loading in some JSON and
 * then displaying a list of cakes with images.
 * Fix any crashes
 * Improve any performance issues
 * Use good coding practices to make code more secure
 */
public class PlaceholderFragment extends ListFragment {

    private static final String JSON_URL = "https://gist.githubusercontent.com/hart88/198f29ec5114a3ec3460/" +
            "raw/8dd19a88f9b8d24c23d9960f3300d0c917a4f07c/cake.json";

    MyAdapter mAdapter;

    public PlaceholderFragment() { /**/ }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create and set the list adapter.
        mAdapter = new MyAdapter(getActivity());
        setListAdapter(mAdapter);
        refresh();
    }

    public void refresh() {
        setListShown(false);
        // Load data from net.
        new DataLoader(this).execute(JSON_URL);
    }

}
