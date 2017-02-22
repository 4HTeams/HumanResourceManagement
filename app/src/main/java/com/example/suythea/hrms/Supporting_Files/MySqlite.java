package com.example.suythea.hrms.Supporting_Files;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.suythea.hrms.Class_Models.UserModel;

import java.util.ArrayList;

/**
 * Created by lolzzlolzz on 10/20/16.
 */

public class MySqlite extends SQLiteOpenHelper {
    Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyHRDatabase";

    ArrayList<String> tables;

    String create_tbl_current_user ="CREATE TABLE tbl_current_user (uid INT, username varchar(50), email varchar(100), profile_url varchar(100), type varchar(50), approval varchar(50) )";

    public MySqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

        tables = new ArrayList<>();
        tables.add("tbl_current_user");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_tbl_current_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUser(UserModel user) {

        // Delete Old Row if it exists
        deleteRow(tables.get(0));

        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("uid", user.getUid());
        values.put("username", user.getUsername());
        values.put("email", user.getEmail());
        values.put("profile_url", user.getProfile_url());
        values.put("type", user.getType());
        values.put("approval", user.getApproval());

        db.insert(tables.get(0), null, values);
        db.close();
    }

    private void deleteRow (String tblName){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + tblName);
        db.close();

    }

    private void getDataFromDB() {

        String query = "SELECT * FROM " + tables.get(0);

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            Log.d("Result", cursor.getString(0));
            Log.d("Result", cursor.getString(1));
            Log.d("Result", cursor.getString(2));
            Log.d("Result", cursor.getString(3));
            Log.d("Result", cursor.getString(4));
            Log.d("Result", cursor.getString(5));
        }

        db.close();
        cursor.close();
    }

    public int checkUserType() {

        String query = "SELECT * FROM " + tables.get(0);
        int type = 0;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()) {
            type = cursor.getColumnIndex("type");
            type = Integer.valueOf(cursor.getString(type));
        }

        db.close();
        cursor.close();

        return type;
    }


//    public ArrayList<NewsGridModel> getDataFromDB(int keyNote) {
//
//        ArrayList<NewsGridModel> array = new ArrayList<NewsGridModel>();
//
//        String query = "SELECT " + KEY_ID + ", " + KEY_WEBADDRESS + " FROM " + DATABASE_TABLE + " where " + KEY_KEYNOTE + " = " + keyNote;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        NewsGridModel model = null;
//        while(cursor.moveToNext()){
//
//            model = new NewsGridModel(cursor.getString(0),cursor.getString(1));
//            array.add(model);
//        }
//
//        cursor.close();
//        return array;
//    }
//
//    public void insertIntoDB(ArrayList<NewsGridModel> models, int keyNote) {
//
//        deleteAllTheOld(keyNote);
//
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = null;
//
//        for (int i = 0; i < models.size() ; i++){
//            values = new ContentValues();
//            values.put(KEY_ID, models.get(i).getId());
//            values.put(KEY_WEBADDRESS, models.get(i).getWebAddress());
//            values.put(KEY_KEYNOTE, String.valueOf(keyNote));
//            db.insert(DATABASE_TABLE, null, values);
//        }
//
//        db.close();
//    }
//
//    public void deleteAllTheOld(int keyNote){
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM " + DATABASE_TABLE + " where " + KEY_KEYNOTE + " = " + keyNote);
//    }

//    public void insert() {
//        SQLiteDatabase db = getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(KEY_ID, 1);
//        values.put(KEY_WEBADDRESS, "OK");
//
//        db.insert(DATABASE_TABLE, null, values);
//        db.close();
//    }



//    public void deleteAll(){
//        SQLiteDatabase db = getWritableDatabase();
//        db.execSQL("DELETE FROM " + DATABASE_TABLE);
//    }

//    public ArrayList<Topic> getAll() {
//        ArrayList<Topic> topics = new ArrayList<Topic>();
//
//        String query = "SELECT  * FROM " + DATABASE_TABLE;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        Topic topic = null;
//        while(cursor.moveToNext()){
//
//            topic = new Topic();
//            topic.setId(Integer.parseInt(cursor.getString(0)));
//            topic.setTopic(cursor.getString(1));
//            topic.setDescription(cursor.getString(2));
//
//            topics.add(topic);
//
//        }
//
//        cursor.close();
//        return topics;
//    }

}


