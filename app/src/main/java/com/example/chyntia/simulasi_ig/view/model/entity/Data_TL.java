package com.example.chyntia.simulasi_ig.view.model.entity;

import android.media.CamcorderProfile;

/**
 * Created by Chyntia on 5/28/2017.
 */

public class Data_TL {
    public String nama, img_path, caption, location, profPic;
    public int comment, like, _id;

    public Data_TL(int _id, String profPic, String nama,String img_path, int comment, int like, String caption, String location){
        this._id = _id;
        this.nama =  nama;
        this.profPic = profPic;
        this.img_path = img_path;
        this.comment = comment;
        this.like = like;
        this.caption = caption;
        this.location = location;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUserId() { return _id;}
}
