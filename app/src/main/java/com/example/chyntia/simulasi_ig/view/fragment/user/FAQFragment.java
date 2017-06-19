package com.example.chyntia.simulasi_ig.view.fragment.user;

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
import com.example.chyntia.simulasi_ig.view.adapter.FAQExpandableRVAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chyntia on 5/9/2017.
 */

public class FAQFragment extends Fragment {
    private RecyclerView recyclerview;
    TextView text;
    ImageView ic_left,ic_right;

    public FAQFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_faq, container, false);

        init(_view);
        setupRV();
        event();

        return _view;
    }

    private void init(View view) {
        recyclerview = (RecyclerView) view.findViewById(R.id.faq_rv);

        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setText("FAQ");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        ((MainActivity) view.getContext()).hide_bottom_nav_bar();
    }

    private void setupRV(){
        List<FAQExpandableRVAdapter.Item> data = fill_with_data();

        FAQExpandableRVAdapter adapter = new FAQExpandableRVAdapter(data, getActivity().getApplication());
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    public List<FAQExpandableRVAdapter.Item> fill_with_data() {

        List<FAQExpandableRVAdapter.Item> data = new ArrayList<>();

        FAQExpandableRVAdapter.Item q1,q2,q3,q4,q5;

        q1 = new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.HEADER, "Question 1");
        q1.invisibleChildren = new ArrayList<>();
        q1.invisibleChildren.add(new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.CHILD, "Answer 1"));

        q2 = new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.HEADER, "Question 2");
        q2.invisibleChildren = new ArrayList<>();
        q2.invisibleChildren.add(new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.CHILD, "Answer 2"));

        q3 = new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.HEADER, "Question 3");
        q3.invisibleChildren = new ArrayList<>();
        q3.invisibleChildren.add(new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.CHILD, "Answer 3"));

        q4 = new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.HEADER, "Question 4");
        q4.invisibleChildren = new ArrayList<>();
        q4.invisibleChildren.add(new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.CHILD, "Answer 4"));

        q5 = new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.HEADER, "Question 5");
        q5.invisibleChildren = new ArrayList<>();
        q5.invisibleChildren.add(new FAQExpandableRVAdapter.Item(FAQExpandableRVAdapter.CHILD, "Answer 5"));

        data.add(q1);
        data.add(q2);
        data.add(q3);
        data.add(q4);
        data.add(q5);

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
