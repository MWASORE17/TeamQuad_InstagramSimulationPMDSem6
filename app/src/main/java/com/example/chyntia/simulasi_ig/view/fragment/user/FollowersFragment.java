package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.FollowersRVAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;

import java.util.HashMap;

/**
 * Created by Chyntia on 5/23/2017.
 */

public class FollowersFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    TextView text;
    ImageView ic_left,ic_right;
    RecyclerView rv;
    Button btn;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;

    public FollowersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_followers, container, false);

        init(_view);
        setupRV();
        event();

        return _view;
    }

    private void init(View view) {

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setText("Followers");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        rv = (RecyclerView) view.findViewById(R.id.recycler_view);
        setupRV();

        btn = (Button) view.findViewById(R.id.btn);
    }

    private void setupRV(){

        FollowersRVAdapter adapter = new FollowersRVAdapter(loginDBAdapter.getAllFollowers(loginDBAdapter.getID(userName)), getActivity().getApplication());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }

    private void event() {
        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changefragment(new ProfileFragment(), "Profile");
            }
        });
    }
}
