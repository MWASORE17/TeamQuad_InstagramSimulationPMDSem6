package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;

/**
 * Created by Chyntia on 6/3/2017.
 */

public class HomeViewFragment extends Fragment {

    public HomeViewFragment() {
        // Required empty public constructor
    }

    public static HomeViewFragment newInstance() {
        return new HomeViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_home_view, container, false);

        return _view;
    }
}