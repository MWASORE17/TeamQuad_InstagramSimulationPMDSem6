package com.example.chyntia.simulasi_ig.view.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.chyntia.simulasi_ig.view.Database.DatabaseHelper;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Follow;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Posting_Grid;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Comments;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Likes;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_Notif;
import com.example.chyntia.simulasi_ig.view.model.entity.Data_TL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Chyntia on 6/7/2017.
 */

public class LoginDBAdapter {
    static final String DATABASE_NAME = "Simulasi_Instagram.db";
    static final int DATABASE_VERSION = 1;
    public static final int NAME_COLUMN = 1;

    public static final String CREATE_TABLE_USER = "create table "+"TB_USER"+
            "( " +"User_ID"+" integer primary key autoincrement,"+ "User_USERNAME text,User_FULLNAME text,User_EMAIL text,User_PROFPIC text,User_PASSWORD text,User_GENDER text); ";

    public static final String CREATE_TABLE_FOLLOW = "create table "+"TB_FOLLOW"+
            "( " +"ID"+" integer primary key autoincrement,"+ "User_ID integer,User_ID_FOLLOWED_BY integer,Created_at text); ";

    public static final String CREATE_TABLE_POSTING = "create table "+"TB_POSTING"+
            "( " +"ID_POST"+" integer primary key autoincrement,"+ "User_ID integer,User_LOCATION text,User_IMGPATH text,User_CAPTION text,Created_at text,User_LIKES integer,User_COMMENTS integer); ";

    public static final String CREATE_TABLE_COMMENTS = "create table "+"TB_COMMENTS"+
            "( " +"ID_COMMENTS"+" integer primary key autoincrement,"+ "ID_POST integer,User_ID integer,User_PROFPIC text,Comments_content text,Created_at text); ";

    public static final String CREATE_TABLE_LIKES = "create table "+"TB_LIKES"+
            "( " +"ID_LIKES"+" integer primary key autoincrement,"+ "ID_POST integer,User_ID integer,User_PROFPIC text,Created_at text); ";

    public static final String CREATE_TABLE_NOTIFICATION = "create table "+"TB_NOTIFICATION"+
            "( " +"ID_NOTIF"+" integer primary key autoincrement,"+ "User_PROFPIC text,User_ID integer,Content_type text,Created_at text,ID_POST integer,User_ID_FOLLOWED_BY integer); ";

    // Variable to hold the database instance
    public SQLiteDatabase db;
    // Context of the application using the database.
    private final Context context;
    // Database open/upgrade helper
    private DatabaseHelper dbHelper;

    public  LoginDBAdapter(Context _context)
    {
        context = _context;
        dbHelper = new DatabaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public LoginDBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        db.close();
    }

    public  SQLiteDatabase getDatabaseInstance()
    {
        return db;
    }

    public void insertEntry(String userName, String fullName, String email, String profPic_path, String password, String gender)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("User_USERNAME", userName);
        newValues.put("User_FULLNAME", fullName);
        newValues.put("User_EMAIL", email);
        newValues.put("User_PROFPIC", profPic_path);
        newValues.put("User_PASSWORD", password);
        newValues.put("User_GENDER", gender);

        // Insert the row into your table
        db.insert("TB_USER", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insert_Follow(int id_following, int id_followed_by, String date)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("User_ID", id_following);
        newValues.put("User_ID_FOLLOWED_BY", id_followed_by);
        newValues.put("Created_at", date);

        // Insert the row into your table
        db.insert("TB_FOLLOW", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insert_Posting(int id_user, String user_location, String user_imgPath, String caption, String created_at, int likes, int comments)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("User_ID", id_user);
        newValues.put("User_LOCATION", user_location);
        newValues.put("User_IMGPATH", user_imgPath);
        newValues.put("User_CAPTION", caption);
        newValues.put("Created_at", created_at);
        newValues.put("User_LIKES", likes);
        newValues.put("User_COMMENTS", comments);

        // Insert the row into your table
        db.insert("TB_POSTING", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insert_Comments(int id_post, int id_user, String user_profPic, String comments_content, String created_at)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("ID_POST", id_post);
        newValues.put("User_ID", id_user);
        newValues.put("User_PROFPIC", user_profPic);
        newValues.put("Comments_content", comments_content);
        newValues.put("Created_at", created_at);

        // Insert the row into your table
        db.insert("TB_COMMENTS", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insert_Likes(int id_post, int id_user, String user_profPic, String created_at)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("ID_POST", id_post);
        newValues.put("User_ID", id_user);
        newValues.put("User_PROFPIC", user_profPic);
        newValues.put("Created_at", created_at);

        // Insert the row into your table
        db.insert("TB_LIKES", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public void insert_Notif(String user_profPic, int id_user, String content_type, String created_at, int id_post, int following)
    {
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put("User_PROFPIC", user_profPic);
        newValues.put("User_ID", id_user);
        newValues.put("Content_type", content_type);
        newValues.put("Created_at", created_at);
        newValues.put("ID_POST", id_post);
        newValues.put("User_ID_FOLLOWED_BY", following);

        // Insert the row into your table
        db.insert("TB_NOTIFICATION", null, newValues);
        ///Toast.makeText(context, "Reminder Is Successfully Saved", Toast.LENGTH_LONG).show();
    }

    public int deleteEntry(String UserName)
    {
        //String id=String.valueOf(ID);
        String where="User_USERNAME=?";
        int numberOFEntriesDeleted= db.delete("TB_USER", where, new String[]{UserName}) ;
        // Toast.makeText(context, "Number fo Entry Deleted Successfully : "+numberOFEntriesDeleted, Toast.LENGTH_LONG).show();
        return numberOFEntriesDeleted;
    }

    public void delete_Following(int id)
    {
        //String id=String.valueOf(ID);
        String where="User_ID_FOLLOWED_BY = "+id;
        db.delete("TB_FOLLOW", where, null) ;
    }

    public String getUserPass(String userName)
    {
        Cursor cursor=db.query("TB_USER", null, " User_USERNAME=?", new String[]{userName}, null, null, null);
        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password= cursor.getString(cursor.getColumnIndex("User_PASSWORD"));
        cursor.close();
        return password;
    }

    public void  updateEntry(String userName, String fullName, String email, String profPic_path, String password, String gender)
    {
        // Define the updated row content.
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("User_USERNAME", userName);
        updatedValues.put("User_FULLNAME", fullName);
        updatedValues.put("User_EMAIL", email);
        updatedValues.put("User_PROFPIC", profPic_path);
        updatedValues.put("User_PASSWORD", password);
        updatedValues.put("User_GENDER", gender);

        String where="User_USERNAME = ?";
        db.update("TB_USER",updatedValues, where, new String[]{userName});
    }

    public ArrayList<String> getUserData(String userName)
    {
        Cursor cursor=db.query("TB_USER", null, " User_USERNAME=?", new String[]{userName}, null, null, null);

        ArrayList<String> user_data = new ArrayList<String>();

        while(cursor.moveToNext()){

            user_data.add(cursor.getString(cursor.getColumnIndex("User_USERNAME")));
            user_data.add(cursor.getString(cursor.getColumnIndex("User_FULLNAME")));
            user_data.add(cursor.getString(cursor.getColumnIndex("User_EMAIL")));
            user_data.add(cursor.getString(cursor.getColumnIndex("User_PROFPIC")));
            user_data.add(cursor.getString(cursor.getColumnIndex("User_GENDER")));

        }

        cursor.close();

        return user_data;
    }

    public void updateUserData(int id, String userName, String fullName, String email, String gender){
        ContentValues updatedValues = new ContentValues();
        // Assign values for each row.
        updatedValues.put("User_USERNAME", userName);
        updatedValues.put("User_FULLNAME", fullName);
        updatedValues.put("User_EMAIL", email);
        updatedValues.put("User_GENDER", gender);

        String where="User_ID = "+id;

        db.update("TB_USER",updatedValues, where, null);
    }

    public void updateUserPass(int id, String new_pass){
        ContentValues updatedValues = new ContentValues();

        // Save the Data_Follow in Database
        updatedValues.put("User_PASSWORD", new_pass);

        String where="User_ID = "+id;

        db.update("TB_USER",updatedValues, where, null);
    }

    public int getID(String userName){
        Cursor cursor=db.query("TB_USER", null, " User_USERNAME=?", new String[]{userName}, null, null, null);

        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
        cursor.close();
        return id;
    }

    public String getUserName(int id){
        String where="User_ID = "+id;

        Cursor cursor=db.query("TB_USER", null, where, null, null, null, null);

        cursor.moveToFirst();
        String userName = cursor.getString(cursor.getColumnIndex("User_USERNAME"));
        cursor.close();
        return userName;
    }

    public List<Data_Follow> getUserRecommendation(int id){

        String query = "User_ID != "+id;

        Cursor cursor=db.query("TB_USER", null, query, null, null, null, null);

        List<Data_Follow> list = new ArrayList<>();

        while (cursor.moveToNext()){

            int _id = cursor.getInt(cursor.getColumnIndex("User_ID"));

            if(getIDFollow(id).contains(_id) == false) {
                Data_Follow _dataFollow = new Data_Follow(getUserProfPic(_id), getUserName(_id), "Follow");
                list.add(_dataFollow);
            }
        }

        cursor.close();

        return list;
    }

    public List<Data_Follow> getAllFollowing(int id){

        String where="User_ID = "+id;

        Cursor cursor=db.query("TB_FOLLOW", null, where, null, null, null, null);

        List<Data_Follow> list = new ArrayList<>();

        while (cursor.moveToNext()){

            int id_followed_by = cursor.getInt(cursor.getColumnIndex("User_ID_FOLLOWED_BY"));
            Data_Follow _dataFollow = new Data_Follow(getUserProfPic(id_followed_by),getUserNameFollowing(id_followed_by),"Following");
            list.add(_dataFollow);

        }

        cursor.close();

        return list;
    }

    public String getUserNameFollowing(int id){

        String where="User_ID = "+id;

        Cursor cursor=db.query("TB_USER", null, where, null, null, null, null);

        if(cursor.getCount()<1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }

        cursor.moveToFirst();
        String userNameFollowing = cursor.getString(cursor.getColumnIndex("User_USERNAME"));
        cursor.close();
        return userNameFollowing;
    }

    public List<Integer> getIDFollow(int id){

        String where="User_ID = "+id;

        Cursor cursor=db.query("TB_FOLLOW", null, where, null, null, null, null);

        List<Integer> list = new ArrayList<>();

        while (cursor.moveToNext()){
                list.add(cursor.getInt(cursor.getColumnIndex("User_ID_FOLLOWED_BY")));
        }

        cursor.close();

        return list;
    }

    public List<Integer> getIDFollowers(int id){

        String where="User_ID_FOLLOWED_BY = "+id;

        Cursor cursor=db.query("TB_FOLLOW", null, where, null, null, null, null);

        List<Integer> list = new ArrayList<>();

        while (cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndex("User_ID")));
        }

        cursor.close();

        return list;
    }

    public List<Data_Follow> getAllFollowers(int id){

        String where="User_ID_FOLLOWED_BY = "+id;

        Cursor cursor=db.query("TB_FOLLOW", null, where, null, null, null, null);

        List<Data_Follow> list = new ArrayList<>();

        Data_Follow _dataFollow;

        while (cursor.moveToNext()){

            int id_followers = cursor.getInt(cursor.getColumnIndex("User_ID"));

            if(isFollowing(id,id_followers))
                _dataFollow = new Data_Follow(getUserProfPic(id_followers),getUserNameFollowing(id_followers),"Following");
            else
                _dataFollow = new Data_Follow(getUserProfPic(id_followers),getUserNameFollowing(id_followers),"Follow");

            list.add(_dataFollow);

        }

        cursor.close();

        return list;
    }

    public String check_TBFollow(){

        Cursor cursor=db.query("TB_FOLLOW", null, null, null, null, null, null);

        if(cursor.getCount()==0){
            return "EMPTY";
        }

        cursor.close();

        return "NOT EMPTY";
    }

    public List<Integer> checkFollowers(){

        Cursor cursor=db.query("TB_FOLLOW", null, null, null, null, null, null);

        List<Integer> list = new ArrayList<>();

        while (cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndex("User_ID_FOLLOWED_BY")));
        }

        cursor.close();

        return list;
    }

    public List<Integer> checkFollowing(){

        Cursor cursor=db.query("TB_FOLLOW", null, null, null, null, null, null);

        List<Integer> list = new ArrayList<>();

        while (cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndex("User_ID")));
        }

        cursor.close();

        return list;
    }

    public boolean isFollowing(int user_id, int user_id_following) {
        Cursor cursor = db.rawQuery("SELECT User_ID_FOLLOWED_BY FROM TB_FOLLOW WHERE User_ID = "+user_id+" AND User_ID_FOLLOWED_BY = "+user_id_following,null);
        boolean following = (cursor.getCount() > 0);
        cursor.close();
        return following;
    }

    public boolean isExist(String text) {
        Cursor cursor = db.rawQuery("SELECT User_USERNAME FROM TB_USER WHERE User_USERNAME LIKE '%"+text+"%'",null);
        boolean exist = (cursor.getCount() > 0);
        cursor.close();
        return exist;
    }

    public List<Data_Follow> search(String text){

        String where="User_USERNAME LIKE '%"+text+"%'";

        Cursor cursor=db.query("TB_USER", null, where, null, null, null, null);

        List<Data_Follow> list = new ArrayList<>();

        Data_Follow _dataFollow = null;

        while (cursor.moveToNext()){

            String user = cursor.getString(cursor.getColumnIndex("User_USERNAME"));

            if(cursor.getCount()<1)
                _dataFollow = new Data_Follow("","User Not Found","");
            else
                _dataFollow = new Data_Follow(getUserProfPic(getID(user)), user, "Following");

            list.add(_dataFollow);

        }

        cursor.close();

        return list;
    }

    public String checkProfPic(int id){
        String where="User_ID = "+id;

        Cursor cursor=db.query("TB_USER", null, where, null, null, null, null);

        cursor.moveToFirst();

        String profPicChange = cursor.getString(cursor.getColumnIndex("User_PROFPIC"));
        cursor.close();
        return profPicChange;
    }

    public void updateUserProfPic(int id,String new_profPic){
        ContentValues updatedValues = new ContentValues();

        // Save the Data_Follow in Database
        updatedValues.put("User_PROFPIC", new_profPic);

        String where="User_ID = "+id;

        db.update("TB_USER",updatedValues, where, null);
    }

    public String getUserProfPic(int id)
    {
        String where="User_ID = "+id;

        Cursor cursor=db.query("TB_USER", null, where, null, null, null, null);
        if(cursor.getCount()<1) // ProfPic Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String profPic_path= cursor.getString(cursor.getColumnIndex("User_PROFPIC"));
        cursor.close();
        return profPic_path;
    }

    public String check_TBPosting(){

        Cursor cursor=db.query("TB_POSTING", null, null, null, null, null, null);

        if(cursor.getCount()==0){
            return "EMPTY";
        }

        cursor.close();

        return "NOT EMPTY";

    }

    public String check_TBPostingProfile(int user_id){

        String where="User_ID = "+user_id;

        Cursor cursor=db.query("TB_POSTING", null, where, null, null, null, null);

        if(cursor.getCount()==0){
            return "NOT EXIST";
        }

        cursor.close();

        return "EXIST";

    }

    public List<Data_TL> getAllPosting(int user_id){

        Cursor cursor=db.query("TB_POSTING", null, null, null, null, null, "ID_POST DESC");

        List<Data_TL> list = new ArrayList<>();

        Data_TL _data = null;

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
            if(isFollowing(user_id,id) || user_id == id || id == getID("airasia")) {
                String photo_path = cursor.getString(cursor.getColumnIndex("User_IMGPATH"));
                String caption = cursor.getString(cursor.getColumnIndex("User_CAPTION"));
                String location = cursor.getString(cursor.getColumnIndex("User_LOCATION"));

                _data = new Data_TL(0, getUserProfPic(id), getUserName(id), photo_path, 0, 0, caption, location);

                list.add(_data);
            }
        }

        cursor.close();

        return list;
    }

    public List<Data_Posting_Grid> getPostingProfile(int user_id){

        String where="User_ID = "+user_id;

        Cursor cursor=db.query("TB_POSTING", null, where, null, null, null, "ID_POST DESC");

        List<Data_Posting_Grid> list = new ArrayList<>();

        Data_Posting_Grid _data = null;

        while (cursor.moveToNext()){

            int posting_id = cursor.getInt(cursor.getColumnIndex("ID_POST"));
            String photo_path = cursor.getString(cursor.getColumnIndex("User_IMGPATH"));

            _data = new Data_Posting_Grid(posting_id,photo_path);

            list.add(_data);

        }

        cursor.close();

        return list;
    }

    public List<Data_TL> getPostingProfileDetail(int user_id){

        String where="User_ID = "+user_id;

        Cursor cursor=db.query("TB_POSTING", null, where, null, null, null, "ID_POST DESC");

        List<Data_TL> list = new ArrayList<>();

        Data_TL _data = null;

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
            if(user_id == id) {
                String photo_path = cursor.getString(cursor.getColumnIndex("User_IMGPATH"));
                String caption = cursor.getString(cursor.getColumnIndex("User_CAPTION"));
                String location = cursor.getString(cursor.getColumnIndex("User_LOCATION"));

                _data = new Data_TL(0, getUserProfPic(id), getUserName(id), photo_path, 0, 0, caption, location);

                list.add(_data);
            }
        }


        cursor.close();

        return list;
    }

    public String checkCaption(int id_post){
        String where="ID_POST = "+id_post;

        Cursor cursor=db.query("TB_POSTING", null, where, null, null, null, null);

        cursor.moveToFirst();

        String photo_caption = cursor.getString(cursor.getColumnIndex("User_CAPTION"));
        cursor.close();
        return photo_caption;
    }

    public String checkLocation(int id_post){
        String where="ID_POST = "+id_post;

        Cursor cursor=db.query("TB_POSTING", null, where, null, null, null, null);

        cursor.moveToFirst();

        String photo_location = cursor.getString(cursor.getColumnIndex("User_LOCATION"));
        cursor.close();
        return photo_location;
    }

    public String getPostingTime(int posting_id){
        Cursor cursor=db.query("TB_POSTING", null, " ID_POST = "+posting_id, null, null, null, null);

        cursor.moveToFirst();
        String posting_time = cursor.getString(cursor.getColumnIndex("Created_at"));
        cursor.close();
        return posting_time;
    }

    public String getPhoto(int posting_id){

        Cursor cursor=db.query("TB_POSTING", null, " ID_POST = "+posting_id, null, null, null, null);

        cursor.moveToFirst();
        String img_path = cursor.getString(cursor.getColumnIndex("User_IMGPATH"));
        cursor.close();
        return img_path;

    }

    public List<Data_Comments> getAllComments(int posting_id){

        String where="ID_POST = "+posting_id;

        Cursor cursor=db.query("TB_COMMENTS", null, where, null, null, null, null);

        List<Data_Comments> list = new ArrayList<>();

        Data_Comments _data = null;

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
            //String photo_path = cursor.getString(cursor.getColumnIndex("User_PROFPIC"));
            String comments_content = cursor.getString(cursor.getColumnIndex("Comments_content"));
            String created_at = cursor.getString(cursor.getColumnIndex("Created_at"));

            _data = new Data_Comments(getUserProfPic(id),getUserName(id),comments_content,created_at);

            list.add(_data);

        }

        cursor.close();

        return list;
    }

    public String check_TBComments(){

        Cursor cursor=db.query("TB_COMMENTS", null, null, null, null, null, null);

        if(cursor.getCount()==0){
            return "EMPTY";
        }

        cursor.close();

        return "NOT EMPTY";

    }

    public String check_TBLikes(){

        Cursor cursor=db.query("TB_LIKES", null, null, null, null, null, null);

        if(cursor.getCount()==0){
            return "EMPTY";
        }

        cursor.close();

        return "NOT EMPTY";

    }

    public List<Data_Likes> getAllLikes(int posting_id){

        String where="ID_POST = "+posting_id;

        Cursor cursor=db.query("TB_LIKES", null, where, null, null, null, null);

        List<Data_Likes> list = new ArrayList<>();

        Data_Likes _data = null;

        while (cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
            String photo_path = cursor.getString(cursor.getColumnIndex("User_PROFPIC"));
            String created_at = cursor.getString(cursor.getColumnIndex("Created_at"));

            _data = new Data_Likes(photo_path,getUserName(id),created_at);

            list.add(_data);

        }

        cursor.close();

        return list;
    }

    public void delete_Likes(int posting_id, int user_id)
    {
        //String id=String.valueOf(ID);
        String where="ID_POST = "+posting_id+" AND User_ID = "+user_id;
        db.delete("TB_LIKES", where, null);
    }

    public boolean isLike(int posting_id, int user_id) {
        Cursor cursor = db.rawQuery("SELECT User_ID FROM TB_LIKES WHERE User_ID = "+user_id+" AND ID_POST = "+posting_id,null);
        boolean like = (cursor.getCount() > 0);
        cursor.close();
        return like;
    }

    public List<Data_Notif> getAllNotif(int user_id){

        //String where="User_ID = "+user_id;

        Cursor cursor=db.query("TB_NOTIFICATION", null, null, null, null, null, "ID_NOTIF DESC");

        List<Data_Notif> list = new ArrayList<>();

        Data_Notif _data = null;

        while (cursor.moveToNext()){

            //String photo_path = cursor.getString(cursor.getColumnIndex("User_PROFPIC"));
            int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
            if(isFollowing(user_id,id) || id == user_id) {
                String content_type = cursor.getString(cursor.getColumnIndex("Content_type"));
                String created_at = cursor.getString(cursor.getColumnIndex("Created_at"));
                int following = cursor.getInt(cursor.getColumnIndex("User_ID_FOLLOWED_BY"));
                int id_post = cursor.getInt(cursor.getColumnIndex("ID_POST"));

                _data = new Data_Notif(getUserProfPic(id),id,content_type,created_at,id_post,following);

                list.add(_data);
            }
        }

        cursor.close();

        return list;
    }

    public String check_TBNotif(){

        Cursor cursor=db.query("TB_NOTIFICATION", null, null, null, null, null, null);

        if(cursor.getCount()==0){
            return "EMPTY";
        }

        cursor.close();

        return "NOT EMPTY";

    }

    public int check_TBNotifFollowing(int user_id){

        Cursor cursor=db.query("TB_NOTIFICATION", null, null, null, null, null, null);

        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
        if (isFollowing(user_id, id) || id == user_id)
            return id;
        return 0;

    }

    public boolean check_TBNotifYou(int user_id){

        Cursor cursor=db.query("TB_NOTIFICATION", null, null, null, null, null, null);

        cursor.moveToFirst();
        int following = cursor.getInt(cursor.getColumnIndex("User_ID_FOLLOWED_BY"));
        int id_post = cursor.getInt(cursor.getColumnIndex("ID_POST"));
        if(user_id == following || getIDPosting(user_id).contains(id_post))

            return true;

        return false;
    }

    public int getIDUserPosting(int id_post){
        Cursor cursor=db.query("TB_POSTING", null, " ID_POST = "+id_post, null, null, null, null);

        cursor.moveToFirst();
        int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
        cursor.close();
        return id;
    }

    public List<Integer> getIDPosting(int id){

        String where="User_ID = "+id;

        Cursor cursor=db.query("TB_POSTING", null, where, null, null, null, null);

        List<Integer> list = new ArrayList<>();

        while (cursor.moveToNext()){
            list.add(cursor.getInt(cursor.getColumnIndex("ID_POST")));
        }

        cursor.close();

        return list;
    }

    public List<Data_Notif> getNotifYou(int user_id){

        //String where="User_ID_FOLLOWED_BY = "+user_id;

        Cursor cursor=db.query("TB_NOTIFICATION", null, null, null, null, null, "ID_NOTIF DESC");

        List<Data_Notif> list = new ArrayList<>();

        Data_Notif _data = null;

        while (cursor.moveToNext()){


            //String photo_path = cursor.getString(cursor.getColumnIndex("User_PROFPIC"));
            int id = cursor.getInt(cursor.getColumnIndex("User_ID"));
            int id_post = cursor.getInt(cursor.getColumnIndex("ID_POST"));
            if(isFollowing(id,user_id) || getIDPosting(user_id).contains(id_post)) {

                String content_type = cursor.getString(cursor.getColumnIndex("Content_type"));
                String created_at = cursor.getString(cursor.getColumnIndex("Created_at"));


                _data = new Data_Notif(getUserProfPic(id), id, content_type, created_at, id_post, 0);

                list.add(_data);

            }
        }

        cursor.close();

        return list;
    }

}
