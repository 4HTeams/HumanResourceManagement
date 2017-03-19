package com.example.suythea.hrms.Supporting_Files;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lolzzlolzz on 10/20/16.
 */

public class MySqlite extends SQLiteOpenHelper {

    Context context;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyHRDatabase";

    public static ArrayList<String> fields;

    String create_tbl_current_user ="CREATE TABLE tbl_jsonData (id INTEGER PRIMARY KEY AUTOINCREMENT, field_name varchar(50), data TEXT)";

    public MySqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;

        fields = new ArrayList<>();
        fields.add("currentUser");
        fields.add("industries");

        insertDefault();
    }

    private void insertDefault(){
        String query = "SELECT COUNT(*) FROM tbl_jsonData";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){
            if (Integer.valueOf(cursor.getString(0)) < 1){
                query = "INSERT INTO tbl_jsonData VALUES (null,'currentUser',''), (null,'industries','')";
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

    public void insertJsonDB(String field, String data) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE tbl_jsonData SET data = '" + data + "' where field_name = '" + field + "'");
        db.close();

    }

    public String getDataFromjsonField(String field, final String jsonField) {

        String query = "SELECT data from tbl_jsonData where field_name = '" + field + "'";
        String result = "";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while(cursor.moveToNext()){

            if (jsonField.equals("_all_db")){
                result = cursor.getString(0);
            }
            else {
                try {
                    result = new JSONArray(cursor.getString(0)).getJSONObject(0).getString(jsonField);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            break;
        }

        db.close();
        cursor.close();

        return result;
    }

    public void deleteField (String field) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE tbl_jsonData set data = '' where field_name= '" + field + "'");

    }

}


