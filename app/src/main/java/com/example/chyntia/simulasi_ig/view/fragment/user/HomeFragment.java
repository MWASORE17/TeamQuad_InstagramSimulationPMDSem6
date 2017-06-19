package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.adapter.HomeRVAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.HashMap;

/**
 * Created by Chyntia on 5/21/2017.
 */

public class HomeFragment extends Fragment {
    TextView text;
    ImageView ic_left,ic_right, ic_friend_recommendation;
    RecyclerView rv;
    Toolbar toolbar;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_home, container, false);

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

        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setVisibility(view.GONE);

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setVisibility(View.GONE);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setVisibility(View.GONE);

        ic_friend_recommendation = (ImageView) view.findViewById(R.id.icon_friend_recommendation);
        ic_friend_recommendation.setVisibility(View.GONE);

        rv = (RecyclerView) view.findViewById(R.id.recycler_view_timeline);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.logo);
        toolbar.setPadding(0,20,0,15);
        toolbar.setContentInsetsRelative(0,0);
        toolbar.setContentInsetsAbsolute(0,0);
    }

    private void setupRV(){

        if(loginDBAdapter.check_TBPosting().equals("EMPTY")){
            changefragment(new HomeViewFragment(), "HomeView");
            rv.setVisibility(View.GONE);
        }

        else{
            HomeRVAdapter adapter = new HomeRVAdapter(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)), getActivity().getApplication());
            rv.setAdapter(adapter);
            //rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            SnappyLinearLayoutManager layoutManager = new SnappyLinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setSnapType(SnapType.CENTER);
            layoutManager.setSnapInterpolator(new DecelerateInterpolator());
            rv.setLayoutManager(layoutManager);
            rv.smoothScrollToPosition(0);
        }
    }

    public void changefragment(Fragment fragment, String tag) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_content, fragment, tag).commit();
    }
}
