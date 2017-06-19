package com.example.chyntia.simulasi_ig.view.model.entity;

/**
 * Created by Chyntia on 3/24/2017.
 */

public class Gender {
    private String _name;
    private int _iconId;

    public Gender(String name, int iconId)
    {
        _name = name;
        _iconId = iconId;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_iconId() {
        return _iconId;
    }

    public void set_iconId(int _iconId) {
        this._iconId = _iconId;
    }
}
