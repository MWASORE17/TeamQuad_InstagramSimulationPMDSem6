package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.TabGridRVAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;

import java.util.HashMap;

/**
 * Created by Chyntia on 5/26/2017.
 */

public class TabGridFragment extends Fragment{
    public static final String ARG_PAGE = "ARG_PAGE";
    RecyclerView rv;
    SessionManager session;
    LoginDBAdapter loginDBAdapter;
    String userName;

    public TabGridFragment() {
        // Required empty public constructor
    }

    public static TabGridFragment newInstance() {

        return new TabGridFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();

        // name
        userName = user.get(SessionManager.KEY_USERNAME);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View _view = inflater.inflate(R.layout.fragment_tab_grid, container, false);

        init(_view);

        return _view;
    }

    private void init(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv_grid_profile);
        setupRV();
    }

    private void setupRV(){

        TabGridRVAdapter adapter = new TabGridRVAdapter(loginDBAdapter.getPostingProfile(loginDBAdapter.getID(userName)), getActivity().getApplication());
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));

    }
/*
    public List<Data_Posting_Grid> fill_with_data() {

        List<Data_Posting_Grid> data = new ArrayList<>();

        data.add(new Data_Posting_Grid(1,"ONE"));
        data.add(new Data_Posting_Grid(2,"TWO"));
        data.add(new Data_Posting_Grid(3,"THREE"));

        return data;
    }*/
}