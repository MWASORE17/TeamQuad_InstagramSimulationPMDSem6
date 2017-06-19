package com.example.chyntia.simulasi_ig.view.model.entity;

/**
 * Created by Chyntia on 4/12/2017.
 */

public class Data_Follow {
    public String nama,status,pp;

    public Data_Follow(String pp, String nama, String status){
        this.nama =  nama;
        this.pp = pp;
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

}
