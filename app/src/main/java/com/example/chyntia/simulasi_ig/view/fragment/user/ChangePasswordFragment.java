package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.app.Activity;
import android.content.Context;
import android.location.SettingInjectorService;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.Spinner_Adapter;
import com.example.chyntia.simulasi_ig.view.model.entity.Gender;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Chyntia on 5/9/2017.
 */

public class ChangePasswordFragment extends Fragment{
    TextView text;
    ImageView ic_left,ic_right;
    LoginDBAdapter loginDBAdapter;
    String userName;
    SessionManager session;
    EditText old_pass,new_pass,confirm_newPass;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_change_password, container, false);

        init(_view);
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
        text.setText("Change Password");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_right = (ImageView) view.findViewById(R.id.icon_right);

        old_pass = (EditText) view.findViewById(R.id.old_pass);
        new_pass = (EditText) view.findViewById(R.id.new_pass);
        confirm_newPass = (EditText) view.findViewById(R.id.confirm_newPass);

        ((MainActivity) view.getContext()).hide_bottom_nav_bar();
    }

    private void event() {
        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getContext());
                ((MainActivity) getActivity()).show_bottom_nav_bar();
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        ic_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean _isvalid = true;

                if (!loginDBAdapter.getUserPass(userName).equals(old_pass.getText().toString())) {
                    _isvalid = false;
                    Toast.makeText(getActivity(),
                            "Your password is invalid",
                            Toast.LENGTH_LONG).show();
                }

                else if (!new_pass.getText().toString().equals(confirm_newPass.getText().toString())) {
                    _isvalid = false;
                    Toast.makeText(getActivity(),
                            "Your password does not match",
                            Toast.LENGTH_LONG).show();
                }

                if (_isvalid) {
                    loginDBAdapter.updateUserPass(loginDBAdapter.getID(userName),new_pass.getText().toString());
                    hideKeyboard(getContext());
                    ((MainActivity) getActivity()).show_bottom_nav_bar();
                    ((MainActivity) getActivity()).changefragment(new SettingsFragment(), "Settings");
                }
            }
        });
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
