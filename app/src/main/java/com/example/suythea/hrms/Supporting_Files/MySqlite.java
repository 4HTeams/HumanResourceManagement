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

    String create_tbl_current_user ="CREATE TABLE tbl_jsonData (id INTEGER PRIMARY KEY AUTOINCREMENT, field_name varchar(50), data TEXT)";

    public MySqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

        tables = new ArrayList<>();
        tables.add("currentUser");

        insertDefault();
        getDataFromDB();

    }

    private void insertDefault(){
        String query = "SELECT COUNT(*) FROM tbl_jsonData";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            if (Integer.valueOf(cursor.getString(0)) < 1){
                query = "INSERT INTO tbl_jsonData VALUES (null,'currentUser','')";
                db.execSQL(query);
            }
            break;
        }

        db.close();
        cursor.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_tbl_current_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertUser(String data) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE tbl_jsonData SET data = '" + data + "' where field_name = '" + tables.get(0) + "'");
        db.close();

    }

    private void getDataFromDB() {

        String query = "SELECT * FROM tbl_jsonData";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            Log.d("Result", cursor.getString(0));
            Log.d("Result", cursor.getString(1));
            Log.d("Result", cursor.getString(2));
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


