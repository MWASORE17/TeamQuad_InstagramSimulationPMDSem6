package com.example.chyntia.simulasi_ig.view.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.chyntia.simulasi_ig.view.adapter.LoginDBAdapter;
import com.example.chyntia.simulasi_ig.view.model.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chyntia on 6/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
    }
    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase _db)
    {
        _db.execSQL(LoginDBAdapter.CREATE_TABLE_USER);
        _db.execSQL(LoginDBAdapter.CREATE_TABLE_FOLLOW);
        _db.execSQL(LoginDBAdapter.CREATE_TABLE_POSTING);
        _db.execSQL(LoginDBAdapter.CREATE_TABLE_COMMENTS);
        _db.execSQL(LoginDBAdapter.CREATE_TABLE_LIKES);
        _db.execSQL(LoginDBAdapter.CREATE_TABLE_NOTIFICATION);
    }
    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");

        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "TB_USER");
        _db.execSQL("DROP TABLE IF EXISTS " + "TB_FOLLOW");
        _db.execSQL("DROP TABLE IF EXISTS " + "TB_POSTING");
        _db.execSQL("DROP TABLE IF EXISTS " + "TB_COMMENTS");
        _db.execSQL("DROP TABLE IF EXISTS " + "TB_LIKES");
        _db.execSQL("DROP TABLE IF EXISTS " + "TB_NOTIFICATION");
        // Create a new one.
        onCreate(_db);
    }
}
