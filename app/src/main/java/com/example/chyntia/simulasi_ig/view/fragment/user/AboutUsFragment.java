package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.AboutUsRVAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Team;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Chyntia on 5/9/2017.
 */

public class AboutUsFragment extends Fragment {
    TextView text;
    ImageView ic_left,ic_right;
    RecyclerView rv;

    public AboutUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_about_us, container, false);

        init(_view);
        setupRV();
        event();

        return _view;
    }

    private void init(View view) {
        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setText("About Us");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        rv = (RecyclerView) view.findViewById(R.id.recycler_view);

        ((MainActivity) view.getContext()).hide_bottom_nav_bar();
    }

    private void setupRV(){
        List<Data_Team> data = fill_with_data();

        AboutUsRVAdapter adapter = new AboutUsRVAdapter(data, getActivity().getApplication());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    public List<Data_Team> fill_with_data() {

        List<Data_Team> data = new ArrayList<>();

        data.add(new Data_Team(R.drawable.photo, "Chyntia", "chyntiachyn7@gmail.com", "Programmer"));
        data.add(new Data_Team(R.drawable.photo, "Santri", "santri@gmail.com", "Programmer"));
        data.add(new Data_Team(R.drawable.photo, "Agustini", "agustini@gmail.com", "Programmer"));
        return data;
    }

    private void event() {
        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).show_bottom_nav_bar();
                ((MainActivity) getActivity()).onBackPressed();
            }
        });
    }
}
