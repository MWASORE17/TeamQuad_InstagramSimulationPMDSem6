package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.adapter.HomeRVAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_TL;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chyntia on 5/26/2017.
 */

public class TabDetailFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    TextView text;
    ImageView ic_left,ic_right, ic_friend_recommendation;
    RecyclerView rv;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;

    public TabDetailFragment() {
        // Required empty public constructor
    }

    public static TabDetailFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TabDetailFragment fragment = new TabDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View _view = inflater.inflate(R.layout.fragment_tab_detail, container, false);

        init(_view);
        setupRV();

        return _view;
    }

    private void init(View view) {

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        rv = (RecyclerView) view.findViewById(R.id.recycler_view_detail);

    }

    private void setupRV(){
        if(loginDBAdapter.check_TBPosting().equals("EMPTY")){
            rv.setVisibility(View.GONE);
        }

        else{
            HomeRVAdapter adapter = new HomeRVAdapter(loginDBAdapter.getPostingProfileDetail(loginDBAdapter.getID(userName)), getActivity().getApplication());
            rv.setAdapter(adapter);
            //rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            SnappyLinearLayoutManager layoutManager = new SnappyLinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setSnapType(SnapType.CENTER);
            layoutManager.setSnapInterpolator(new DecelerateInterpolator());
            rv.setLayoutManager(layoutManager);
            rv.smoothScrollToPosition(0);
        }
    }
/*
    public List<Data_TL> fill_with_data() {

        List<Data_TL> data = new ArrayList<>();

        data.add(new Data_TL(0,R.drawable.ic_account_circle_black_24dp, "katelouis", R.drawable.ic_comment_outline_grey, R.drawable.ic_heart_outline_grey));
        return data;
    }*/
}
