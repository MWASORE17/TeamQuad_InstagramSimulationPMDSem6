package com.example.chyntia.simulasi_ig.view.model.entity;

/**
 * Created by Chyntia on 6/17/2017.
 */

public class Data_Likes {
    public String username, profPic, created_at;

    public Data_Likes(String profPic, String username, String created_at){
        this.profPic =  profPic;
        this.username = username;
        this.created_at = created_at;
    }
}
