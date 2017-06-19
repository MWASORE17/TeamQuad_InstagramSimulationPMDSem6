package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.FollowingRVAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.HomeRVAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.UserCommentRVAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;

import java.util.HashMap;

/**
 * Created by Chyntia on 5/28/2017.
 */

public class UserCommentFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    TextView text;
    ImageView ic_left,ic_right,ic_send;
    EditText comment;
    RecyclerView rv;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;
    MyAsyncTask myAsyncTask;
    int posting_id;

    public UserCommentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posting_id = getArguments().getInt("POSITION");
    }

    public static UserCommentFragment newInstance() {
        return new UserCommentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_user_comment, container, false);

        init(_view);
        event();
        setupRV();
        showInputMethod();

        return _view;
    }

    private void init(View view) {

        loginDBAdapter = new LoginDBAdapter(getContext());
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(getContext());
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        text = (TextView) view.findViewById(R.id.toolbar_title);
        text.setText("Comments");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        comment = (EditText) view.findViewById(R.id.commenttext);
        comment.requestFocus();

        rv = (RecyclerView) view.findViewById(R.id.comment_rv);

        ic_send = (ImageView) view.findViewById(R.id.send);

        ((MainActivity) view.getContext()).hide_bottom_nav_bar();
    }

    private void event() {
        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getContext());
                ((MainActivity) getActivity()).show_bottom_nav_bar();
                ((MainActivity) getActivity()).changefragment(new HomeFragment(),"Home");
            }
        });

        ic_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDBAdapter.insert_Comments(posting_id,loginDBAdapter.getID(userName),loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName)),comment.getText().toString(),String.valueOf(System.currentTimeMillis()));
                loginDBAdapter.insert_Notif(loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName)),loginDBAdapter.getID(userName),"Comment",String.valueOf(System.currentTimeMillis()),posting_id,0);
                hideKeyboard(getContext());
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute();
            }
        });
    }

    public void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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

    private void setupRV(){

        if(loginDBAdapter.check_TBComments().equals("NOT EMPTY")){
            UserCommentRVAdapter adapter = new UserCommentRVAdapter(loginDBAdapter.getAllComments(posting_id), getActivity().getApplication());
            rv.setAdapter(adapter);
            rv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        }
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        boolean running;
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            int i = 2;
            while(running){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(i-- == 0){
                    running = false;
                }

                publishProgress(i);

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setMessage("Loading...");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            running = true;

            progressDialog = ProgressDialog.show(getActivity(),"",
                    "Loading...");

            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    running = false;
                }
            });
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //getProfilePic();
            comment.setText("");
            setupRV();

            /*Toast.makeText(getActivity(),
                    "Your Profile Picture was Changed",
                    Toast.LENGTH_LONG).show();*/

            progressDialog.dismiss();

        }

    }
}
