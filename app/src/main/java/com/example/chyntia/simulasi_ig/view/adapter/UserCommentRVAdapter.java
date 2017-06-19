package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Follow;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Comments;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chyntia on 6/14/2017.
 */

public class UserCommentRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Data_Comments> user;
    Context context;
    private boolean isButtonClicked = false;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;

    public UserCommentRVAdapter(List<Data_Comments> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_comment, parent, false);

        loginDBAdapter = new LoginDBAdapter(context);
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        return new UserCommentRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        final UserCommentRVAdapter.ViewHolder _holder = (UserCommentRVAdapter.ViewHolder) holder;
        final Data_Comments _user = this.user.get(position);
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        _holder.nama.setText(_user.username_comments);

        if(loginDBAdapter.checkProfPic(loginDBAdapter.getID(_user.username_comments))!=null){
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

        _holder.created_at.setText(HomeRVAdapter.getTimeAgo(Long.parseLong(_user.created_at)));
        _holder.comments_content.setText(_user.comments_content);
        //animate(holder);
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
    public void insert(int position, Data_Comments data) {
        user.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data_Follow object
    public void remove(Data_Follow dataFollow) {
        int position = user.indexOf(dataFollow);
        user.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama, created_at, comments_content;
        ImageView pp;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.username_comment);
            pp = (ImageView) itemView.findViewById(R.id.pp_comment);
            created_at = (TextView) itemView.findViewById(R.id.time_comment);
            comments_content = (TextView) itemView.findViewById(R.id.comments_content);
        }
    }
}
