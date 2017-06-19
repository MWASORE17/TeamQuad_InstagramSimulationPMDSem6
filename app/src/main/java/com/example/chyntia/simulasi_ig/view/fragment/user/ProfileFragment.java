package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.ProfileVPAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Chyntia on 5/21/2017.
 */

public class ProfileFragment extends Fragment {
    public static TextView profile_username;
    TextView total_following, total_followers, total_following_text, total_followers_text, total_post, total_post_text;
    ImageView ic_left, ic_right, ic_friend_recommendation;
    Button edit_btn, btn_follow;
    public static ImageView profile_pp;
    TabLayout tabLayoutProfile;
    private int tabIcons[] = new int[] { R.drawable.tab_profile_icon_grid_states, R.drawable.tab_profile_icon_list_states};
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean isButtonClicked = false;
    SessionManager session;
    LoginDBAdapter loginDBAdapter;
    String userName;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_profile, container, false);

        init(_view);
        event();

        return _view;
    }

    private void init(View view) {

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();

        // name
        userName = user.get(SessionManager.KEY_USERNAME);

        profile_username = (TextView) view.findViewById(R.id.toolbar_title);
        profile_username.setText(userName);

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setVisibility(View.GONE);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(R.drawable.ic_more_vert_black_24dp);

        edit_btn = (Button) view.findViewById(R.id.btn_edit);

        total_post = (TextView) view.findViewById(R.id.total_posts);
        total_post_text = (TextView) view.findViewById(R.id.total_posts_text);

        total_followers = (TextView) view.findViewById(R.id.total_followers);
        total_followers_text = (TextView) view.findViewById(R.id.total_followers_text);

        total_following = (TextView) view.findViewById(R.id.total_following);
        total_following_text = (TextView) view.findViewById(R.id.total_following_text);

        ic_friend_recommendation = (ImageView) view.findViewById(R.id.icon_friend_recommendation);
        ic_friend_recommendation.setVisibility(View.VISIBLE);

        profile_pp = (ImageView) view.findViewById(R.id.profile_photo);
        updateProfPic();

        ViewPager viewPagerProfile = (ViewPager) view.findViewById(R.id.viewpager_profile);

        tabLayoutProfile = (TabLayout) view.findViewById(R.id.sliding_tabs_profile);

        if(loginDBAdapter.check_TBPosting().equals("EMPTY") || loginDBAdapter.check_TBPostingProfile(loginDBAdapter.getID(userName)).equals("NOT EXIST")) {
            changefragment(new ProfileViewFragment(), "ProfileView");
            viewPagerProfile.setVisibility(view.GONE);
/*
            disable_tablayout();
            tabLayoutProfile.setSelectedTabIndicatorColor(0);
            setupTabIcons();*/
        }

        else{
            viewPagerProfile.setAdapter(new ProfileVPAdapter(getChildFragmentManager()));
            //viewPagerProfile.setCurrentItem(0);
            tabLayoutProfile.setupWithViewPager(viewPagerProfile);
            setupTabIcons();
        }

        btn_follow = (Button) view.findViewById(R.id.profile_btn_follow);
    }

    private void setupTabIcons() {
        tabLayoutProfile.getTabAt(0).setIcon(tabIcons[0]);
        tabLayoutProfile.getTabAt(1).setIcon(tabIcons[1]);
    }

    private int dpToPx(int dp)
    {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private void event() {
        ic_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new SettingsFragment(), "Settings");
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new EditProfileFragment(), "EditProfile");
            }
        });

        if(loginDBAdapter.check_TBPosting().equals("EMPTY")){

            total_post.setEnabled(false);

            total_post_text.setEnabled(false);
        }
        else{
            total_post.setText(String.valueOf(loginDBAdapter.getPostingProfile(loginDBAdapter.getID(userName)).size()));

            if(loginDBAdapter.getPostingProfile(loginDBAdapter.getID(userName)).size()>1)
                total_post_text.setText("posts");

            else
                total_post_text.setText("post");
        }

        if(loginDBAdapter.check_TBFollow().equals("EMPTY"))
        {
            total_following.setEnabled(false);

            total_following_text.setEnabled(false);

            total_followers.setEnabled(false);

            total_followers_text.setEnabled(false);
        }

        else {
            if (loginDBAdapter.checkFollowing().contains(loginDBAdapter.getID(userName))) {

                total_following.setEnabled(true);

                String totalFollowing = "" + loginDBAdapter.getAllFollowing(loginDBAdapter.getID(userName)).size();
                total_following.setText(totalFollowing);

                total_following.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).addfragment(new FollowingFragment(), "Following");
                    }
                });

                total_following_text.setEnabled(true);

                total_following_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).addfragment(new FollowingFragment(), "Following");
                    }
                });
            } else {
                total_following.setEnabled(false);

                total_following_text.setEnabled(false);
            }

            if (loginDBAdapter.checkFollowers().contains(loginDBAdapter.getID(userName))) {

                total_followers.setEnabled(true);

                String totalFollowers = "" + loginDBAdapter.getAllFollowers(loginDBAdapter.getID(userName)).size();
                total_followers.setText(totalFollowers);

                total_followers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).addfragment(new FollowersFragment(), "Followers");
                    }
                });

                total_followers_text.setEnabled(true);

                total_followers_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((MainActivity) getActivity()).addfragment(new FollowersFragment(), "Followers");
                    }
                });
            } else {
                total_followers.setEnabled(false);

                total_followers_text.setEnabled(false);
            }
        }

        ic_friend_recommendation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).addfragment(new FriendRecommendationFragment(), "FriendRecommendation");
            }
        });



        btn_follow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                follow_states(v);
            }
        });

    }

    public void follow_states(View v){
        if (v.getId() == R.id.profile_btn_follow) {
            isButtonClicked = !isButtonClicked; // toggle the boolean flag
            btn_follow.setText(isButtonClicked ? "Following" : "Follow");
            btn_follow.setTextColor(isButtonClicked ? Color.BLACK : Color.WHITE);
            v.setBackgroundResource(isButtonClicked ? R.drawable.btn_following : R.drawable.btn_follow);
        }
    }

    public void changefragment(Fragment fragment, String tag) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profile_content, fragment, tag).commit();
    }

    private void disable_tablayout(){
        LinearLayout tabStrip = ((LinearLayout)tabLayoutProfile.getChildAt(0));
        for(int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
    }

    private void updateProfPic(){
        if(loginDBAdapter.checkProfPic(loginDBAdapter.getID(userName))!=null){
            Picasso
                    .with(getContext())
                    .load(new File(loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName))))
                    .resize(dpToPx(80), dpToPx(80))
                    .centerCrop()
                    .error(R.drawable.ic_account_circle_black_128dp)
                    .into(profile_pp);
        }
        else
            profile_pp.setImageResource(R.drawable.ic_account_circle_black_128dp);
    }
}
