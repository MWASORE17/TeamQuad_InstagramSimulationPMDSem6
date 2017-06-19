package com.example.chyntia.simulasi_ig.view.fragment.user;

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
 * Created by Chyntia on 5/9/2017.
 */

public class PostsLikedFragment extends Fragment {
    GridView gridView;

    private BottomNavigationView bottomNavigationView;
    TextView text;
    ImageView ic_left,ic_right;

    public PostsLikedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_posts_liked, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view) {
        gridView = (GridView)view.findViewById(R.id.posts_liked_grid);
        //List<Data_Posting_Grid> data = fill_with_data();
        //PostsLikedBaseAdapter adapter = new PostsLikedBaseAdapter(data, getActivity().getApplication());
        //gridView.setAdapter(adapter);

        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setText("Likes");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        CircularProgressView progressView = (CircularProgressView) view.findViewById(R.id.progress_view);
        progressView.startAnimation();

        changefragment(new PostsLikedViewFragment(), "PostsLikedView");
        gridView.setVisibility(view.GONE);
        progressView.setVisibility(view.GONE);
    }

    private void event() {
        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
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

    public void changefragment(Fragment fragment, String tag) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.posts_liked_content, fragment, tag).commit();
    }
}
