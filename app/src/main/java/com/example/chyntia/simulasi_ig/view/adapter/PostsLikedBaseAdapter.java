package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Posting_Grid;

import java.util.Collections;
import java.util.List;

/**
 * Created by Chyntia on 5/12/2017.
 */

public class PostsLikedBaseAdapter extends BaseAdapter {
    Context context;
    List<Data_Posting_Grid> data = Collections.emptyList();

    public PostsLikedBaseAdapter(List<Data_Posting_Grid> data, Context context)
    {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub

        return data.get(position);
    }

    @Override
    public long getItemId(int position) {

        // TODO Auto-generated method stub

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ImageView img = new ImageView(this.context);

        //text.setText((CharSequence) data.get(position));

        //text.setGravity(Gravity.CENTER);

        img.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 178));

        img.setBackgroundResource(R.drawable.ic_heart_red);

        return img;
    }
}
