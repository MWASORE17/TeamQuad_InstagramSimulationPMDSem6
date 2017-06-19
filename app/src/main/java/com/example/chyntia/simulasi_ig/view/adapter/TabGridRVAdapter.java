package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.activity.MainActivity;
import com.example.chyntia.simulasi_ig.view.fragment.user.PhotoDetailFragment;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Follow;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Posting_Grid;
import com.example.chyntia.simulasi_ig.view.model.entity.session.SessionManager;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Chyntia on 6/16/2017.
 */

public class TabGridRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Data_Posting_Grid> user;
    Context context;
    private boolean isButtonClicked = false;
    LoginDBAdapter loginDBAdapter;
    SessionManager session;
    String userName;

    public TabGridRVAdapter(List<Data_Posting_Grid> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item_grid_profile, parent, false);

        loginDBAdapter = new LoginDBAdapter(context);
        loginDBAdapter = loginDBAdapter.open();

        session = new SessionManager(context);
        HashMap<String, String> user = session.getUserDetails();

        userName = user.get(SessionManager.KEY_USERNAME);

        return new TabGridRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,int position) {
        final TabGridRVAdapter.ViewHolder _holder = (TabGridRVAdapter.ViewHolder) holder;
        final Data_Posting_Grid _user = this.user.get(position);
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView

        Picasso
                .with(context)
                .load(new File(_user.img_path))
                .resize(dpToPx(20), dpToPx(20))
                .centerCrop()
                .error(R.drawable.ic_account_circle_black_24dp)
                .into(_holder.photo);

        _holder.photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = _user.posting_id;
                PhotoDetailFragment pdf = new PhotoDetailFragment();
                final Bundle args = new Bundle();
                args.putInt("POSITION", position);
                pdf.setArguments(args);
                ((MainActivity) v.getContext()).changefragment(pdf, "PhotoDetail");
            }
        });
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
    public void insert(int position, Data_Posting_Grid data) {
        user.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data_Follow object
    public void remove(Data_Follow dataFollow) {
        int position = user.indexOf(dataFollow);
        user.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            photo = (ImageView) itemView.findViewById(R.id.photo_grid_profile);

        }
    }
}
