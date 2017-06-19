package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;

/**
 * Created by Chyntia on 6/3/2017.
 */

public class TabFollowingViewFragment extends Fragment {
    TextView tab_following_link;

    public TabFollowingViewFragment() {
        // Required empty public constructor
    }

    public static TabFollowingViewFragment newInstance() {
        return new TabFollowingViewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_tab_following_view, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view){
        tab_following_link = (TextView) view.findViewById(R.id.tab_following_link);
    }

    private void event() {
        tab_following_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new FriendRecommendationFragment(), "FriendRecommendation");
            }
        });
    }
}