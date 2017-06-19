package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;

/**
 * Created by Chyntia on 6/7/2017.
 */

public class LoginFragment extends Fragment {

    EditText username,pass;
    Button appCompatButtonLogin;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_login,container,false);

        init(view);
        event();

        return view;
    }

    private void init(View view){

        username = (EditText) view.findViewById(R.id.et_username);
        username.requestFocus();

        pass = (EditText) view.findViewById(R.id.et_password);
        appCompatButtonLogin = (Button) view.findViewById(R.id.Btn_Login);

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();
    }

    private void event(){
        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
    }

    private void Login(){
        String userName = username.getText().toString();
        String password = pass.getText().toString();

        // fetch the Password form database for respective user name
        String storedPassword = loginDBAdapter.getUserPass(userName);

        // check if the Stored password matches with  Password entered by user
        if(password.equals(storedPassword))
        {
            Toast.makeText(getContext(), "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
            session = new SessionManager(getContext());
            session.createLoginSession(userName);
            Intent i = new Intent(getContext(), MainActivity.class);
            // Closing all the Activities
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Staring Login Activity
            getContext().startActivity(i);
        }

        else
        {
            Toast.makeText(getContext(), "User Name or Password does not match", Toast.LENGTH_LONG).show();
            pass.setText("");
        }

    }
}
