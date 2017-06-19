package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.example.chyntia.simulasi_ig.view.utilities.BottomNavigationViewHelper;

import java.util.Set;

/**
 * Created by Chyntia on 5/9/2017.
 */

public class SettingsFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    TextView text,edit_profile,change_pass,posts_liked,language,faq,about_us, logout;
    ImageView ic_left,ic_right;
    SessionManager session;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_settings, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view) {

        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setText("Settings");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        edit_profile = (TextView) view.findViewById(R.id.edit_profile);
        change_pass = (TextView) view.findViewById(R.id.change_pass);
        posts_liked = (TextView) view.findViewById(R.id.posts_liked);
        language = (TextView) view.findViewById(R.id.language);
        faq = (TextView) view.findViewById(R.id.faq);
        about_us = (TextView) view.findViewById(R.id.about_us);
        logout = (TextView) view.findViewById(R.id.logout);
    }

    private void event() {
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new EditProfileFragment(), "EditProfile");
            }
        });

        change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new ChangePasswordFragment(), "ChangePassword");
            }
        });

        posts_liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new PostsLikedFragment(), "PostLikedFragment");
            }
        });

        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new LanguageFragment(), "Language");
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new FAQFragment(), "FAQ");
            }
        });

        about_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new AboutUsFragment(), "AboutUs");
            }
        });

        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = new SessionManager(getContext());
                session.logoutUser();
            }
        });
    }
}
