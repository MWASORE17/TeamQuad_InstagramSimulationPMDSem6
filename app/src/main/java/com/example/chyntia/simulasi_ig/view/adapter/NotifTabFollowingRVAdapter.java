package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Notif;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chyntia on 6/18/2017.
 */

public class NotifTabFollowingRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Data_Notif> user;
    Context context;
    private boolean isButtonClicked = false;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public NotifTabFollowingRVAdapter(List<Data_Notif> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification_tab_you, parent, false);

        loginDBAdapter = new LoginDBAdapter(context);
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        return new NotifTabFollowingRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final NotifTabFollowingRVAdapter.ViewHolder _holder = (NotifTabFollowingRVAdapter.ViewHolder) holder;
        final Data_Notif _user = this.user.get(position);
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView

        if((_user.content_type.equals("Like") || _user.content_type.equals("Comment")) && (loginDBAdapter.getIDUserPosting(_user.id_post) == loginDBAdapter.getID(userName) || _user.user_id == loginDBAdapter.getIDUserPosting(_user.id_post))) {
            _holder.nama.setVisibility(View.GONE);
            _holder.content.setVisibility(View.GONE);
            _holder.created_at.setVisibility(View.GONE);
            _holder.pp.setVisibility(View.GONE);
        }
        else {
            if (loginDBAdapter.getUserName(_user.user_id).equals(userName))
                _holder.nama.setText("You");
            else
                _holder.nama.setText(loginDBAdapter.getUserName(_user.user_id));

            if (_user.content_type.equals("Follow") && loginDBAdapter.getUserName(_user.user_id).equals(userName))
                _holder.content.setText(" are following " + loginDBAdapter.getUserName(_user.following));
            else if (_user.content_type.equals("Follow") && loginDBAdapter.getUserName(_user.following).equals(userName))
                _holder.content.setText(" started following you");
            else if (_user.content_type.equals("Follow"))
                _holder.content.setText(" started following " + loginDBAdapter.getUserName(_user.following));
            else if (_user.content_type.equals("Like")) {
                _holder.content.setText(" liked " + loginDBAdapter.getUserName(loginDBAdapter.getIDUserPosting(_user.id_post)) + "'s post");
                _holder.photo.setVisibility(View.VISIBLE);
                Picasso
                        .with(context)
                        .load(new File(loginDBAdapter.getPhoto(_user.id_post)))
                        .resize(dpToPx(40), dpToPx(40))
                        .centerCrop()
                        .error(R.drawable.ic_account_circle_black_24dp)
                        .into(_holder.photo);
            } else if (_user.content_type.equals("Comment")) {
                _holder.content.setText(" commented on\n" + loginDBAdapter.getUserName(loginDBAdapter.getIDUserPosting(_user.id_post)) + "'s post");
                _holder.photo.setVisibility(View.VISIBLE);
                Picasso
                        .with(context)
                        .load(new File(loginDBAdapter.getPhoto(_user.id_post)))
                        .resize(dpToPx(40), dpToPx(40))
                        .centerCrop()
                        .error(R.drawable.ic_account_circle_black_24dp)
                        .into(_holder.photo);
            }


            if (loginDBAdapter.checkProfPic(_user.user_id) != null) {
                Picasso
                        .with(context)
                        .load(new File(_user.profPic))
                        .resize(dpToPx(20), dpToPx(20))
                        .centerCrop()
                        .error(R.drawable.ic_account_circle_black_24dp)
                        .into(_holder.pp);
            } else
                _holder.pp.setImageResource(R.drawable.ic_account_circle_black_24dp);


    /*
            _holder.btn.setText(_user.status);
            if(_user.status == "Follow"){
                _holder.btn.setTextColor(Color.WHITE);
                _holder.btn.setBackgroundResource(R.drawable.btn_follow);
            }
            else{
                _holder.btn.setTextColor(Color.BLACK);
                _holder.btn.setBackgroundResource(R.drawable.btn_following);
            }

            _holder.btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    if(_user.status == "Follow")
                        isButtonClicked = !isButtonClicked;

                    if(isButtonClicked){
                        loginDBAdapter.insert_Follow(loginDBAdapter.getID(userName),loginDBAdapter.getID(_holder.nama.getText().toString()),String.valueOf(System.currentTimeMillis()));
                        _holder.btn.setText("Following");
                        _holder.btn.setTextColor(Color.BLACK);
                        v.setBackgroundResource(R.drawable.btn_following);
                    }

                    else{
                        loginDBAdapter.delete_Following(loginDBAdapter.getID(_holder.nama.getText().toString()));
                        _holder.btn.setText("Follow");
                        _holder.btn.setTextColor(Color.WHITE);
                        v.setBackgroundResource(R.drawable.btn_follow);
                    }

                }
            });*/
            //animate(holder);

            _holder.created_at.setText(getTimeAgo(Long.parseLong(_user.created_at)));
        }
    }

    private int dpToPx(int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }
/*
    public void follow_states(View v, FollowersRVAdapter.ViewHolder _holder, Data_Follow){
        if (v.getId() == R.id.btn) {
            isButtonClicked = !isButtonClicked; // toggle the boolean flag

            _holder.btn.setText(_user);
            /*
            _holder.btn.setText(isButtonClicked ? "Following" : "Follow");
            _holder.btn.setTextColor(isButtonClicked ? Color.BLACK : Color.WHITE);
            v.setBackgroundResource(isButtonClicked ? R.drawable.btn_following : R.drawable.btn_follow);
        }
    }*/

    @Override
    public int getItemCount() {
        //returns the number of elements the RecyclerView will display
        return user.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Data_Notif data) {
        user.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data_Follow object
    public void remove(Data_Notif data) {
        int position = user.indexOf(data);
        user.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama, content, created_at;
        ImageView pp, photo;
        Button btn;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.username_notif);
            pp = (ImageView) itemView.findViewById(R.id.pp_notif);
            btn = (Button) itemView.findViewById(R.id.btn_notif);
            content = (TextView) itemView.findViewById(R.id.content_notif);
            created_at = (TextView) itemView.findViewById(R.id.time_notif);
            photo = (ImageView) itemView.findViewById(R.id.photo_notif);
        }
    }

    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return null;
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}

