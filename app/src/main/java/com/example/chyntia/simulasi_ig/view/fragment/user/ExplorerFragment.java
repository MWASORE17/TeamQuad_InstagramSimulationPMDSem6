package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

/**
 * Created by Chyntia on 5/21/2017.
 */

public class ExplorerFragment extends Fragment {
    GridView gridView;
    private BottomNavigationView bottomNavigationView;
    TextView text;
    ImageView ic_left,ic_right;

    public ExplorerFragment() {
        // Required empty public constructor
    }

    public static ExplorerFragment newInstance() {
        return new ExplorerFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_explorer, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view) {
        gridView = (GridView)view.findViewById(R.id.gridView1);
        //List<Data_Posting_Grid> data = fill_with_data();
        //PostsLikedBaseAdapter adapter = new PostsLikedBaseAdapter(data, getActivity().getApplication());
        //gridView.setAdapter(adapter);

        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setText("Search");
        text.setTextColor(Color.parseColor("#b0bec5"));

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_search_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        CircularProgressView progressView = (CircularProgressView) view.findViewById(R.id.progress_view);
        progressView.startAnimation();
    }
/*
    public List<Data_Posting_Grid> fill_with_data() {

        List<Data_Posting_Grid> data = new ArrayList<>();

        data.add(new Data_Posting_Grid(1,"ONE"));
        data.add(new Data_Posting_Grid(2,"TWO"));
        data.add(new Data_Posting_Grid(3,"THREE"));
        data.add(new Data_Posting_Grid(4,"FOUR"));
        data.add(new Data_Posting_Grid(5,"FIVE"));
        data.add(new Data_Posting_Grid(6,"SIX"));
        data.add(new Data_Posting_Grid(7,"SEVEN"));
        data.add(new Data_Posting_Grid(8,"EIGHT"));
        data.add(new Data_Posting_Grid(9,"NINE"));

        return data;
    }*/

    private void event() {
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new SearchFragment(), "Search");
            }
        });

        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new SearchFragment(), "Search");
            }
        });
    }
}
