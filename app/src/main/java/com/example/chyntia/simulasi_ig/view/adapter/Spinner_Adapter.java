package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chyntia.simulasi_ig.R;
import com.example.chyntia.simulasi_ig.view.model.entity.Gender;

import java.util.ArrayList;

/**
 * Created by Chyntia on 3/24/2017.
 */

public class Spinner_Adapter extends BaseAdapter {
    Context _context;
    ArrayList<Gender> _list;

    public Spinner_Adapter(Context context, ArrayList<Gender> list)
    {
        _context = context;
        _list = list;
    }

    @Override
    public int getCount() {
        return (_list == null)?0:  _list.size();
    }

    @Override
    public Object getItem(int i) {
        return _list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            LayoutInflater inflater = (LayoutInflater)_context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_spinner_items,viewGroup,false);
        }
        Gender gender = _list.get(i);

        TextView txtTitle = (TextView)view.findViewById(R.id.textView);
        ImageView imgView = (ImageView)view.findViewById(R.id.imageView);
        txtTitle.setText(gender.get_name());
        imgView.setImageResource(gender.get_iconId());
        return view;
    }
}
