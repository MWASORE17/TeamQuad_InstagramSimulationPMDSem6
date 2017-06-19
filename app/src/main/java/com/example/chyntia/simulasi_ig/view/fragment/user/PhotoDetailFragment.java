package com.example.chyntia.simulasi_ig.view.fragment.user;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.adapter.TabGridRVAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Chyntia on 6/16/2017.
 */

public class PhotoDetailFragment extends Fragment{
    private BottomNavigationView bottomNavigationView;
    TextView text, user_name, time, caption, location, username_caption, count_likes, view_comment;
    ImageView ic_left, ic_right, ic_send, photo, pp, comment;
    TabGridRVAdapter tabGridRVAdapter;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;
    LikeButton like;
    int posting_id;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public PhotoDetailFragment() {
        // Required empty public constructor
    }

    public static PhotoDetailFragment newInstance() {
        return new PhotoDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        posting_id = getArguments().getInt("POSITION");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_photo_detail, container, false);

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
        text.setText("Photo");

        ic_left = (ImageView) view.findViewById(R.id.icon_left);
        ic_left.setImageResource(R.drawable.ic_arrow_back_black_24dp);

        ic_right = (ImageView) view.findViewById(R.id.icon_right);
        ic_right.setImageResource(0);

        pp = (ImageView) view.findViewById(R.id.pp_photo_detail);
        if(loginDBAdapter.checkProfPic(loginDBAdapter.getID(userName))!=null){
            Picasso
                    .with(getContext())
                    .load(new File(loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName))))
                    .resize(dpToPx(20), dpToPx(20))
                    .centerCrop()
                    .error(R.drawable.ic_account_circle_black_24dp)
                    .into(pp);
        }
        else
            pp.setImageResource(R.drawable.ic_account_circle_black_24dp);

        user_name = (TextView) view.findViewById(R.id.nama_photo_detail);
        user_name.setText(userName);

        photo = (ImageView) view.findViewById(R.id.photo_area_photo_detail);
        Picasso
                .with(getContext())
                .load(new File(loginDBAdapter.getPhoto(posting_id)))
                .resize(dpToPx(80), dpToPx(80))
                .centerCrop()
                .error(R.drawable.ic_account_circle_black_128dp)
                .into(photo);

        time = (TextView) view.findViewById(R.id.time_photo_detail);
        time.setText(getTimeAgo(Long.parseLong(loginDBAdapter.getPostingTime(posting_id))));

        caption = (TextView) view.findViewById(R.id.photo_caption_photo_detail);
        username_caption = (TextView) view.findViewById(R.id.username_caption_photo_detail);
        if(loginDBAdapter.checkCaption(posting_id).equals(""))
            caption.setVisibility(View.GONE);

        else {
            username_caption.setVisibility(View.VISIBLE);
            username_caption.setText(userName);
            caption.setVisibility(View.VISIBLE);
            caption.setText(loginDBAdapter.checkCaption(posting_id));
        }

        location = (TextView) view.findViewById(R.id.location_photo_detail);
        if(loginDBAdapter.checkLocation(posting_id).equals(""))
            location.setVisibility(View.GONE);

        else {
            location.setVisibility(View.VISIBLE);
            location.setText(loginDBAdapter.checkLocation(posting_id));
        }

        like = (LikeButton) view.findViewById(R.id.icon_like_photo_detail);
        count_likes = (TextView) view.findViewById(R.id.count_likes_photo_detail);
        if(loginDBAdapter.isLike(posting_id,loginDBAdapter.getID(userName))) {
            like.setLiked(true);
            like.setLikeDrawableRes(R.drawable.ic_heart_red);
        }

        else {
            like.setLiked(false);
            like.setUnlikeDrawableRes(R.drawable.ic_heart_outline_grey);
        }

        if(loginDBAdapter.check_TBLikes().equals("NOT EMPTY")){
            count_likes.setVisibility(View.VISIBLE);

            if(loginDBAdapter.getAllLikes(posting_id).size()==0)
                count_likes.setVisibility(View.GONE);

            else if(loginDBAdapter.getAllLikes(posting_id).size()==1)
                count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(posting_id).size())+" like");

            else
                count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(posting_id).size())+" likes");
        }

        like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                count_likes.setVisibility(View.VISIBLE);

                loginDBAdapter.insert_Likes(posting_id,loginDBAdapter.getID(userName),loginDBAdapter.getUserProfPic(loginDBAdapter.getID(userName)),String.valueOf(System.currentTimeMillis()));

                if(loginDBAdapter.getAllLikes(posting_id).size()==0)
                    count_likes.setVisibility(View.GONE);

                else if(loginDBAdapter.getAllLikes(posting_id).size()==1)
                    count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(posting_id).size())+" like");

                else
                    count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(posting_id).size())+" likes");

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
                count_likes.setVisibility(View.VISIBLE);

                loginDBAdapter.delete_Likes(posting_id,loginDBAdapter.getID(userName));

                if(loginDBAdapter.getAllLikes(posting_id).size()==0)
                   count_likes.setVisibility(View.GONE);

                else if(loginDBAdapter.getAllLikes(posting_id).size()==1)
                    count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(posting_id).size())+" like");

                else
                    count_likes.setText(String.valueOf(loginDBAdapter.getAllLikes(posting_id).size())+" likes");
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

        comment = (ImageView) view.findViewById(R.id.icon_comment_photo_detail);
        view_comment = (TextView) view.findViewById(R.id.comments_photo_detail);
        comment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int position = posting_id;
                UserCommentFragment ucf = new UserCommentFragment();
                final Bundle args = new Bundle();
                args.putInt("POSITION", position);
                ucf.setArguments(args);
                ((MainActivity) v.getContext()).changefragment(ucf, "UserComment");
            }
        });

        if(loginDBAdapter.check_TBComments().equals("NOT EMPTY")) {
            view_comment.setVisibility(View.VISIBLE);
            if(loginDBAdapter.getAllComments(posting_id).size() == 0){
                view_comment.setVisibility(View.GONE);
            }
            else if(loginDBAdapter.getAllComments(posting_id).size() == 1) {
                view_comment.setText("View 1 comment");
                view_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = posting_id;
                        UserCommentFragment ucf = new UserCommentFragment();
                        final Bundle args = new Bundle();
                        args.putInt("POSITION", position);
                        ucf.setArguments(args);
                        ((MainActivity) v.getContext()).changefragment(ucf, "UserComment");
                    }
                });
            }

            else{
                view_comment.setText("View all "+String.valueOf(loginDBAdapter.getAllComments(posting_id).size())+" comments");
                view_comment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = posting_id;
                        UserCommentFragment ucf = new UserCommentFragment();
                        final Bundle args = new Bundle();
                        args.putInt("POSITION", position);
                        ucf.setArguments(args);
                        ((MainActivity) v.getContext()).changefragment(ucf, "UserComment");
                    }
                });
            }
        }
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
                ((MainActivity) getActivity()).changefragment(new ProfileFragment(),"Profile");
            }
        });
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
