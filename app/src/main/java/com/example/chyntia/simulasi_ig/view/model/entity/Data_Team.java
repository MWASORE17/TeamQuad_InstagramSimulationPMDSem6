package com.example.chyntia.simulasi_ig.view.model.entity;

/**
 * Created by Chyntia on 4/14/2017.
 */

public class Data_Team {
    public String nama;
    public int img_id;
    public String job;
    public String email;

    public Data_Team(int img_id, String nama, String email, String job){
        this.nama =  nama;
        this.img_id = img_id;
        this.email = email;
        this.job = job;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
