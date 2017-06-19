package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.fragment.user.AboutUsFragment;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Team;

import java.util.Collections;
import java.util.List;

/**
 * Created by Chyntia on 5/10/2017.
 */

public class AboutUsRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Data_Team> user = Collections.emptyList();
    Context context;

    public AboutUsRVAdapter(List<Data_Team> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the layout, initialize the View Holder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_team, parent, false);
        return new AboutUsRVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        final ViewHolder _holder = (ViewHolder) holder;
        final Data_Team _user = this.user.get(position);
        //Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        _holder.nama.setText(_user.getNama());
        _holder.pp.setImageResource(user.get(position).img_id);
        _holder.email.setText(_user.getEmail());
        _holder.job.setText(_user.getJob());
        //animate(holder);
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
    public void insert(int position, Data_Team data) {
        user.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data_Follow object
    public void remove(Data_Team data) {
        int position = user.indexOf(data);
        user.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nama,email,job;
        ImageView pp;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = (TextView) itemView.findViewById(R.id.nama_team);
            pp = (ImageView) itemView.findViewById(R.id.pp_team);
            email = (TextView) itemView.findViewById(R.id.email_team);
            job = (TextView) itemView.findViewById(R.id.job);
        }
    }
}
