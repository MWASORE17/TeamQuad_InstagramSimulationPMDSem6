package com.example.chyntia.simulasi_ig.view.model.entity;

/**
 * Created by Chyntia on 5/28/2017.
 */

public class Data_Posting_Grid {
    public String img_path;
    public int posting_id;

    public Data_Posting_Grid(int posting_id, String img_path){
        this.posting_id = posting_id;
        this.img_path = img_path;
    }
}
