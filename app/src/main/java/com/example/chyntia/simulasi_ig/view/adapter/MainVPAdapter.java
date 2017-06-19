package com.example.chyntia.simulasi_ig.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.chyntia.simulasi_ig.view.fragment.user.LoginFragment;
import com.example.chyntia.simulasi_ig.view.fragment.user.SignUpFragment;

/**
 * Created by Chyntia on 6/7/2017.
 */

public class MainVPAdapter extends FragmentPagerAdapter {

    public MainVPAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new LoginFragment();
            case 1:
                return new SignUpFragment();
            default :
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Login";
            case 1:
                return "Sign Up";

        }
        return null;
    }
}
