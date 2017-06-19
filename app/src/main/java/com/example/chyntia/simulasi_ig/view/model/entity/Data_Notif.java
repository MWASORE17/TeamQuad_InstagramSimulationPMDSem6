package com.example.chyntia.simulasi_ig.view.model.entity;

/**
 * Created by Chyntia on 6/18/2017.
 */

public class Data_Notif {
    public String content_type, profPic, created_at;
    public int user_id, id_post, following;

    public Data_Notif(String profPic, int user_id, String content_type, String created_at, int id_post, int following){
        this.profPic =  profPic;
        this.user_id = user_id;
        this.content_type = content_type;
        this.created_at = created_at;
        this.id_post = id_post;
        this.following = following;
    }
}
