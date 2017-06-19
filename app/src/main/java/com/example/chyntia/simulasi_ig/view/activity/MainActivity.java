package com.example.chyntia.simulasi_ig.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.fragment.user.ChangePasswordFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.EditProfileFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.ExplorerFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.HomeFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.NotificationFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.PostingFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.PostsLikedFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.ProfileFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.SettingsFragment;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.example.chyntia.simulasi_ig.view.utilities.BottomNavigationViewHelper;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity{
    private BottomNavigationView bottomNavigationView;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new SessionManager(getApplicationContext());

        session.checkLogin();

        changefragment(new HomeFragment(), "Home");

        bottom_nav_bar();
    }

    public void changefragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content, fragment, tag).commit();
    }

    public void addfragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(R.id.content,fragment, tag).addToBackStack(null).commit();
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            this.show_bottom_nav_bar();
            super.onBackPressed();
        }
    }

    public void bottom_nav_bar(){
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changefragment(new HomeFragment(), "Home");
                    break;
                case R.id.navigation_explorer:
                    changefragment(new ExplorerFragment(), "Explorer");
                    break;
                case R.id.navigation_posting:
                    changefragment(new PostingFragment(), "Posting");
                    break;
                case R.id.navigation_notifications:
                    changefragment(new NotificationFragment(), "Notification");
                    break;
                case R.id.navigation_account:
                    changefragment(new ProfileFragment(), "Profile");
                    break;
            }
            return true;
            }
        });
    }

    public void hide_bottom_nav_bar()
    {
        bottomNavigationView.setVisibility(View.GONE);
    }

    public void show_bottom_nav_bar()
    {
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        // call the superclass method first
        super.onPause();

        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = this.getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    protected void onDestroy() {
        // call the superclass method first
        super.onDestroy();

        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = this.getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
