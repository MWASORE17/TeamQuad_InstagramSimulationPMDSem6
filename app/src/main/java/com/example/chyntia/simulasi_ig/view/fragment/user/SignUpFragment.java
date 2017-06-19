package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.AuthenticationActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;

/**
 * Created by Chyntia on 6/7/2017.
 */

public class SignUpFragment extends Fragment {

    EditText username , fullname, email, password, confirm_password;
    TextInputLayout til_username, til_fullname, til_email, til_pass, til_confirm_pass;
    Button appCompatButtonSignUp;
    TextView login;

    private LoginDBAdapter loginDBAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        // Inflate the layout for this fragment
        init(_view);
        event();

        return _view;
    }

    private void init(View view){

        username = (EditText) view.findViewById(R.id.ET_Username);
        fullname = (EditText) view.findViewById(R.id.ET_FullName);
        email = (EditText) view.findViewById(R.id.ET_Email);
        password = (EditText) view.findViewById(R.id.ET_Password1);
        confirm_password = (EditText) view.findViewById(R.id.ET_Password2);
        appCompatButtonSignUp = (Button) view.findViewById(R.id.Btn_SignUp);

        til_username = (TextInputLayout) view.findViewById(R.id.TIL_Username);
        til_fullname = (TextInputLayout) view.findViewById(R.id.TIL_FullName);
        til_email = (TextInputLayout) view.findViewById(R.id.TIL_Email);
        til_pass = (TextInputLayout) view.findViewById(R.id.TIL_Password1);
        til_confirm_pass = (TextInputLayout) view.findViewById(R.id.TIL_Password2);

        login = (TextView) view.findViewById(R.id.TV_Login);

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();
    }

    private void event(){
        appCompatButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthenticationActivity.mViewPager.setCurrentItem(0);
            }
        });
    }

    private void SignUp(){
        String Username = username.getText().toString();
        String Fullname = fullname.getText().toString();
        String Email = email.getText().toString();
        String Password = password.getText().toString();
        String confirm_pass = confirm_password.getText().toString();

        boolean _isvalid = true;
        til_username.setErrorEnabled(false);
        til_fullname.setErrorEnabled(false);
        til_email.setErrorEnabled(false);
        til_pass.setErrorEnabled(false);

        if (TextUtils.isEmpty(Username)){
            _isvalid = false;
            til_username.setErrorEnabled(true);
            til_username.setError("Username is required");
        } else if (Username.length() < 7) {
            _isvalid = false;
            til_username.setErrorEnabled(true);
            til_username.setError("Username minimal 7");

        } else if (loginDBAdapter.isExist(Username)) {
            _isvalid = false;
            til_username.setErrorEnabled(true);
            til_username.setError("Username already exists");

        } else if (TextUtils.isEmpty(Fullname)) {
            _isvalid = false;
            til_fullname.setErrorEnabled(true);
            til_fullname.setError("Full Name is required");
        }
        else if (TextUtils.isEmpty(Email)) {
            _isvalid = false;
            til_email.setErrorEnabled(true);
            til_email.setError("Email is required");
        }
        else if (!AuthenticationActivity.isemailvalid(Email)) {
            _isvalid = false;
            til_email.setErrorEnabled(true);
            til_email.setError("Email is not valid");
        } else if (TextUtils.isEmpty(Password)) {
            _isvalid = false;
            til_pass.setErrorEnabled(true);
            til_pass.setError("Password is required");
        } else if (!AuthenticationActivity.ispasswordvalid(Password)) {
            _isvalid = false;
            til_pass.setErrorEnabled(true);
            til_pass.setError("Password is not valid. Password must contains at least 1 lowercase, 1 uppercase, 1 number, 1 special character and minimum 8 characters");
        } else if (TextUtils.isEmpty(confirm_pass)) {
            _isvalid = false;
            til_confirm_pass.setErrorEnabled(true);
            til_confirm_pass.setError("Re-Password is required");
        } else if (!Password.equals(confirm_pass)) {
            _isvalid = false;
            til_confirm_pass.setErrorEnabled(true);
            til_confirm_pass.setError("Password not match");
        }

        if (_isvalid) {
            // Save the Data_Follow in Database
            loginDBAdapter.insertEntry(Username, Fullname, Email, null, Password, "");
            Toast.makeText(getContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
            username.setText("");
            fullname.setText("");
            email.setText("");
            password.setText("");
            confirm_password.setText("");
            AuthenticationActivity.mViewPager.setCurrentItem(0);
        }
    }
}
