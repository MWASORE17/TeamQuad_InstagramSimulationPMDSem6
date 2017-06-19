package com.example.chyntia.simulasi_ig.view.model.entity;

/**
 * Created by Chyntia on 6/15/2017.
 */

public class Data_Comments {
    public String username_comments, profPic, comments_content, created_at;

    public Data_Comments(String profPic, String username_comments, String comments_content, String created_at){
        this.profPic =  profPic;
        this.username_comments = username_comments;
        this.comments_content = comments_content;
        this.created_at = created_at;
    }
}
