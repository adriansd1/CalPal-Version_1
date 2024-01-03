package com.example.calpal_version1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


    public class DatabaseHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "macros.db";
        private static final String TABLE_NAME = "food_table";
        private static final String COL1 = "ID";
        protected static final String COL2 = "Fat";
        protected static final String COL3 = "Carbs";
        protected static final String COL4 = "Protein";
        protected static final String COL5 = "Name";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TABLE_NAME +
                    " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2 + " INTEGER, " +
                    COL3 + " INTEGER, " +
                    COL4 + " INTEGER, " +
                    COL5 + " VARCHAR)";
           /*String createTable = "CREATE TABLE " + TABLE_NAME +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL2 + " INTEGER, " +
                    COL3 + " INTEGER, " +
                    COL4 + " INTEGER, " +
                    COL5 + " VARCHAR)";*/
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }

        public boolean addData(int fat, int carbs, int protein, String name) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, fat);
            contentValues.put(COL3, carbs);
            contentValues.put(COL4, protein);
            contentValues.put(COL5, name);
            Log.d(DATABASE_NAME, "addData: Adding " +  fat + carbs + protein + " to " + TABLE_NAME);
            long result = db.insert(TABLE_NAME, null, contentValues);
            if(result == -1){return false;}
            else{return true;}
        }

        public Cursor getData(){
            SQLiteDatabase db =  this.getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_NAME;
            Cursor data = db.rawQuery(query, null);
            return data;
        }
        public Cursor getFoodId(String name){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "SELECT " + COL1 + " FROM " + TABLE_NAME + " WHERE " + COL5 + " = '" + name + "'";
            Cursor data = db.rawQuery(query, null);
            return data;
        }

        public void updateName(String newName, int id, String oldName){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "UPDATE " + TABLE_NAME + " SET " + COL5 + " = '" + newName +
                    "' WHERE " + COL1 + " = '" + id + "'" + " AND " + COL5
                    + " = '" + oldName + "'";
            //String query = "UPDATE " + TABLE_NAME + " SET " + COL5 + " = '" + newName + "' WHERE " + COL5 + " = '" + oldName + "'";
            Log.d(DATABASE_NAME, "updateName: query: " + query);
            Log.d(DATABASE_NAME, "updateName: Setting name to " + newName);
            db.execSQL(query);
        }

        public void deleteFood(int id, String name){
            SQLiteDatabase db = this.getWritableDatabase();
            String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                    COL1 + " = '" + id + "' AND " + COL5
                    + " = '" + name + "'";
            Log.d(DATABASE_NAME, "deleteName: query: " + query);
            Log.d(DATABASE_NAME, "deleteName: deleting " + name);
            db.execSQL(query);
        }

    }