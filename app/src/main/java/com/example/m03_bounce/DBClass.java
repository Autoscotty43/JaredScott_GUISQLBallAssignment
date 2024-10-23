package com.example.m03_bounce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

// SQL Table
public class DBClass extends SQLiteOpenHelper implements DB_Interface {
    public static final int DATABASE_VERSION = 8;
    public static final String DATABASE_NAME = "Bounce_DB_Name.db";
    private static final String TABLE_NAME = "sample_table";
    private static final String _ID = "_ID";
    private static final String _COL_1 = "X";
    private static final String _COL_2 = "Y";
    private static final String _COL_3 = "DX";
    private static final String _COL_4 = "DY";
    private static final String _COL_5 = "name";

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    _COL_1 + " FLOAT, " + _COL_2 + " FLOAT, " + _COL_3 + " FLOAT, " + _COL_4 + " FLOAT)";
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DBClass", "DB onCreate() " + SQL_CREATE_TABLE);
        db.execSQL(SQL_CREATE_TABLE);
        Log.d("DBClass", "DB onCreate()");
    }

    // Upgrade Table variables
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DBClass", "DB onUpgrade() to version " + DATABASE_VERSION);
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    // Count Table variables
    @Override
    public int count() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        Log.v("DBClass", "getCount=" + cnt);
        return cnt;
    }

    // Save Data
    @Override
    public void save(DataModel dataModel) {
        Log.v("DBClass", "save()=>  " + dataModel.toString());
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(_COL_1, dataModel.getModelX());
        values.put(_COL_2, dataModel.getModelY());
        values.put(_COL_3, dataModel.getModelDX());
        values.put(_COL_4, dataModel.getModelDY());

        db.insert(TABLE_NAME, null, values);
        db.close();
        dump();
    }

    // Update Data (No Usage)
    @Override
    public int update(DataModel dataModel) {
        return 0;
    }

    // Delete by LongID (No Usage)
    @Override
    public int deleteById(Long id) {
        return 0;
    }

    //Add Default Rows
    private void addDefaultRows() {
        if (count() <= 2) {
            Log.v("DBClass", "Adding default rows");
            this.save(new DataModel(1, 20.0f, 20.0f, -4.0f, 4.0f));
            this.save(new DataModel(2, 30f, 30f, 3f, -3f));
        }
    }

    // Find all
    @Override
    public List<DataModel> findAll() {
        List<DataModel> temp = new ArrayList<>();
        addDefaultRows();

        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                DataModel item = new DataModel(cursor.getInt(0), cursor.getFloat(1), cursor.getFloat(2), cursor.getFloat(3), cursor.getFloat(4));
                temp.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return temp;
    }

    // Get Name by LongID
    @Override
    public String getNameById(Long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String name = "";
        String query = "SELECT " + _COL_5 + " FROM " + TABLE_NAME + " WHERE " + _ID + " = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            name = cursor.getString(0);
        }

        cursor.close();
        return name;
    }

    // Dump Table
    private void dump() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            Log.v("DBClass", "DUMPING TABLE DATA");
            do {
                Log.v("DBClass", "ID: " + cursor.getInt(0) + " X: " + cursor.getFloat(1) + " Y: " + cursor.getFloat(2) + " DX: " + cursor.getFloat(3) + " DY: " + cursor.getFloat(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
