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

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.NotifTabFollowingRVAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.NotifTabYouRVAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.nshmura.snappysmoothscroller.SnapType;
import com.nshmura.snappysmoothscroller.SnappyLinearLayoutManager;

import java.util.HashMap;

/**
 * Created by Chyntia on 5/20/2017.
 */

public class TabFollowingFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    RecyclerView rv;
    Toolbar toolbar;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;

    public TabFollowingFragment() {
        // Required empty public constructor
    }

    public static TabFollowingFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        TabFollowingFragment fragment = new TabFollowingFragment();
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
        View _view = inflater.inflate(R.layout.fragment_tab_following, container, false);

        init(_view);
        setupRV();

        return _view;
    }

    private void init(View v) {

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        rv = (RecyclerView) v.findViewById(R.id.recycler_view_tab_following);

    }

    private void setupRV(){

        if(loginDBAdapter.check_TBNotif().equals("EMPTY") || loginDBAdapter.check_TBNotifFollowing(loginDBAdapter.getID(userName)) == 0){
            changefragment(new TabFollowingViewFragment(), "TabFollowingView");
            rv.setVisibility(View.GONE);
        }

        else{
            NotifTabFollowingRVAdapter adapter = new NotifTabFollowingRVAdapter(loginDBAdapter.getAllNotif(loginDBAdapter.getID(userName)), getActivity().getApplication());
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            /*
            SnappyLinearLayoutManager layoutManager = new SnappyLinearLayoutManager(getActivity().getApplicationContext());
            layoutManager.setSnapType(SnapType.CENTER);
            layoutManager.setSnapInterpolator(new DecelerateInterpolator());
            rv.setLayoutManager(layoutManager);
            rv.smoothScrollToPosition(0);*/
        }
    }

    public void changefragment(Fragment fragment, String tag) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.tab_following_content, fragment, tag).commit();
    }
}
