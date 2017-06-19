package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Chyntia on 6/2/2017.
 */

public class ShareToFragment extends Fragment {
    private BottomNavigationView bottomNavigationView;
    TextView text;
    ImageView ic_left, ic_right;
    static ImageView share_photo;
    EditText caption, location;
    SessionManager session;
    LoginDBAdapter loginDBAdapter;
    String userName;
    String photo_path;
    MyAsyncTask myAsyncTask;

    public ShareToFragment() {
        // Required empty public constructor
    }

    public static ShareToFragment newInstance() {
        return new ShareToFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photo_path = getArguments().getString("PHOTO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_share_to, container, false);

        init(_view);
        event();
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
        text.setText("Share To");

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(R.drawable.ic_check_black_24dp);

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        share_photo = (ImageView) view.findViewById(R.id.share_photo);
        caption = (EditText) view.findViewById(R.id.caption);
        location = (EditText) view.findViewById(R.id.location);

        Picasso
                .with(getContext())
                .load(new File(photo_path))
                .resize(dpToPx(80), dpToPx(80))
                .centerCrop()
                .error(R.drawable.ic_account_circle_black_128dp)
                .into(share_photo);
    }

    private int dpToPx(int dp)
    {
        float density = getContext().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    private void event() {
        ic_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getContext());
                ((MainActivity) getActivity()).onBackPressed();
            }
        });

        ic_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PrettyTime p = new PrettyTime();
                loginDBAdapter.insert_Posting(loginDBAdapter.getID(userName),location.getText().toString(),photo_path,caption.getText().toString(),String.valueOf(System.currentTimeMillis()),0,0);
                myAsyncTask = new MyAsyncTask();
                myAsyncTask.execute();
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

    public void showInputMethod() {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        boolean running;
        ProgressDialog progressDialog;

        @Override
        protected Void doInBackground(Void... params) {
            int i = 5;
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
            ((MainActivity) getActivity()).changefragment(new HomeFragment(), "Home");
            ((MainActivity) getActivity()).show_bottom_nav_bar();

            Toast.makeText(getActivity(),
                    "Upload Successfully",
                    Toast.LENGTH_LONG).show();

            progressDialog.dismiss();

        }

    }
}
