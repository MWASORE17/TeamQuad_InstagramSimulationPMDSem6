package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.fragment.user.UserCommentFragment;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_TL;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chyntia on 5/24/2017.
 */

public class HomeRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Data_TL> user = Collections.emptyList();
    Context context;
    private boolean isButtonClicked = false;
    LoginDBAdapter loginDBAdapter;
    String userName;
    SessionManager session;
    boolean isLike = false;
    boolean curr_state;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public HomeRVAdapter(List<Data_TL> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_timeline, parent, false);

        loginDBAdapter = new LoginDBAdapter(context);
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();

        // name
        userName = user.get(SessionManager.KEY_USERNAME);

        return new HomeRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final HomeRVAdapter.ViewHolder _holder = (HomeRVAdapter.ViewHolder) holder;
        final Data_TL _user = this.user.get(position);
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        _holder.nama.setText(_user.nama);

        if(_user.nama.equals("airasia"))
            _holder.title_advertisement.setVisibility(View.VISIBLE);

        if(loginDBAdapter.checkProfPic(loginDBAdapter.getID(_user.nama))!=null){
            Picasso
                    .with(context)
                    .load(new File(_user.profPic))
                    .resize(dpToPx(20), dpToPx(20))
                    .centerCrop()
                    .error(R.drawable.ic_account_circle_black_24dp)
                    .into(_holder.pp);
        }
        else
            _holder.pp.setImageResource(R.drawable.ic_account_circle_black_24dp);

        //animate(holder);
        _holder.comment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition();
                UserCommentFragment ucf = new UserCommentFragment();
                final Bundle args = new Bundle();
                args.putInt("POSITION", position);
                ucf.setArguments(args);
                ((MainActivity) v.getContext()).changefragment(ucf, "UserComment");
            }
        });

        if(loginDBAdapter.check_TBComments().equals("NOT EMPTY")) {
            _holder.view_comment.setVisibility(View.VISIBLE);
            if(loginDBAdapter.getAllComments(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size() == 0){
                _holder.view_comment.setVisibility(View.GONE);
            }
            else if(loginDBAdapter.getAllComments(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size() == 1) {
                _holder.view_comment.setText("View 1 comment");
                _holder.view_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size() - _holder.getAdapterPosition();
                        UserCommentFragment ucf = new UserCommentFragment();
                        final Bundle args = new Bundle();
                        args.putInt("POSITION", position);
                        ucf.setArguments(args);
                        ((MainActivity) v.getContext()).changefragment(ucf, "UserComment");
                    }
                });
            }

            else{
                _holder.view_comment.setText("View all "+String.valueOf(loginDBAdapter.getAllComments(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size())+" comments");
                _holder.view_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size() - _holder.getAdapterPosition();
                        UserCommentFragment ucf = new UserCommentFragment();
                        final Bundle args = new Bundle();
                        args.putInt("POSITION", position);
                        ucf.setArguments(args);
                        ((MainActivity) v.getContext()).changefragment(ucf, "UserComment");
                    }
                });
            }
        }

        /*
        if(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()) == 0) {
            _holder.count_likes.setVisibility(View.GONE);
        }

        else {
            _holder.count_likes.setVisibility(View.VISIBLE);
            if(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()) == 1) {
                _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" like");
            }

            else{
                _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" likes");
            }
        }*/

        if(loginDBAdapter.isLike(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition(),loginDBAdapter.getID(userName))) {
            _holder.like.setLiked(true);
            _holder.like.setLikeDrawableRes(R.drawable.ic_heart_red);
        }

        else {
            _holder.like.setLiked(false);
            _holder.like.setUnlikeDrawableRes(R.drawable.ic_heart_outline_grey);
        }

        if(loginDBAdapter.check_TBLikes().equals("NOT EMPTY")){
            _holder.count_likes.setVisibility(View.VISIBLE);

            if(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size()==0)
                _holder.count_likes.setVisibility(View.GONE);

            else if(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size()==1)
                _holder.count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size())+" like");

            else
                _holder.count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size())+" likes");
        }

        _holder.like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                _holder.count_likes.setVisibility(View.VISIBLE);

                loginDBAdapter.insert_Likes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition(),loginDBAdapter.getID(userName),loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName)),String.valueOf(System.currentTimeMillis()));
                loginDBAdapter.insert_Notif(loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName)),loginDBAdapter.getID(userName),"Like",String.valueOf(System.currentTimeMillis()),loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition(),0);

                if(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size()==0)
                    _holder.count_likes.setVisibility(View.GONE);

                else if(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size()==1)
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size())+" like");

                else
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size())+" likes");

                /*
                loginDBAdapter.updateUserLikes(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition())+1,loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition());
                if(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()) == 1) {
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" like");
                }

                else{
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" likes");
                }*/
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                _holder.count_likes.setVisibility(View.VISIBLE);

                loginDBAdapter.delete_Likes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition(),loginDBAdapter.getID(userName));

                if(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size()==0)
                    _holder.count_likes.setVisibility(View.GONE);

                else if(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size()==1)
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size())+" like");

                else
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).size())+" likes");
                /*
                loginDBAdapter.updateUserLikes(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition())-1,loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition());
                if(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()) == 1) {
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" like");
                }

                else{
                    _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" likes");
                }*/
            }
        });
/*
        _holder.like.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //if (v.getId() == R.id.icon_like) {
                    //isButtonClicked = !isButtonClicked;
                    // toggle the boolean flag
                    //_holder.like.setImageResource(isButtonClicked ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
                    curr_state = !isLike;

                    _holder.count_likes.setVisibility(View.VISIBLE);

                    if(curr_state)
                    {
                        loginDBAdapter.updateUserLikes(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition())+1,loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition());

                        _holder.like.setImageResource(R.drawable.ic_heart_red);

                        if(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()) == 1) {
                            _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" like");
                        }

                        else{
                            _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" likes");
                        }

                        curr_state = isLike;

                    }
                    else if(!curr_state)
                    {
                        loginDBAdapter.updateUserLikes(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition())-1,loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition());

                        _holder.like.setImageResource(R.drawable.ic_heart_outline_grey);

                        if(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()) == 1) {
                            _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" like");
                        }

                        else{
                            _holder.count_likes.setText(String.valueOf(loginDBAdapter.getCountLikes(loginDBAdapter.getAllPosting().size()-_holder.getAdapterPosition()))+" likes");
                        }

                        curr_state = !isLike;
                    }
                //}
            }
        });*/

        Picasso
                .with(context)
                .load(new File(_user.img_path))
                .resize(dpToPx(300), dpToPx(300))
                .centerCrop()
                .error(R.drawable.ic_account_circle_black_128dp)
                .into(_holder.photo);

        if(loginDBAdapter.checkCaption(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).equals(""))
            _holder.username_caption.setVisibility(View.GONE);

        else {
            _holder.username_caption.setVisibility(View.VISIBLE);
            _holder.username_caption.setText(_user.nama);
            _holder.caption.setVisibility(View.VISIBLE);
            _holder.caption.setText(_user.caption);
        }

        if(loginDBAdapter.checkLocation(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()).equals(""))
            _holder.user_location.setVisibility(View.GONE);

        else {
            _holder.user_location.setVisibility(View.VISIBLE);
            _holder.user_location.setText(_user.location);
        }

        _holder.time.setText(getTimeAgo(Long.parseLong(loginDBAdapter.getPostingTime(loginDBAdapter.getAllPosting(loginDBAdapter.getID(userName)).size()-_holder.getAdapterPosition()))));
    }

    private int dpToPx(int dp)
    {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

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
    public void insert(int position, Data_TL data) {
        user.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data_Follow object
    public void remove(Data_TL data) {
        int position = user.indexOf(data);
        user.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama, title_advertisement, time, username_caption, count_likes, caption, user_location, view_comment;
        ImageView pp,comment,photo;
        LikeButton like;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama_detail);
            pp = (ImageView) itemView.findViewById(R.id.pp_timeline);
            comment = (ImageView) itemView.findViewById(R.id.icon_comment);
            view_comment = (TextView) itemView.findViewById(R.id.comments);
            like = (LikeButton) itemView.findViewById(R.id.icon_like);
            title_advertisement = (TextView) itemView.findViewById(R.id.title_advertisement);
            time = (TextView) itemView.findViewById(R.id.time);
            photo = (ImageView) itemView.findViewById(R.id.photo_area);
            username_caption = (TextView) itemView.findViewById(R.id.username_caption);
            count_likes = (TextView) itemView.findViewById(R.id.count_likes);
            caption = (TextView) itemView.findViewById(R.id.photo_caption);
            user_location = (TextView) itemView.findViewById(R.id.photo_location);
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

