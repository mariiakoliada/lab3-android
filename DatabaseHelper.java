package com.example.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "questions.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "questions";
    private static final String COL_ID = "id";
    private static final String COL_QUESTION = "question";
    private static final String COL_DIFFICULTY = "difficulty";
    private static final String COL_TYPE = "type";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_QUESTION + " TEXT, " +
                COL_DIFFICULTY + " TEXT, " +
                COL_TYPE + " TEXT)";
        db.execSQL(createTable);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean insertRecord(String question, String difficulty, String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_QUESTION, question);
        values.put(COL_DIFFICULTY, difficulty);
        values.put(COL_TYPE, type);
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }
    public List<String> getAllRecords() {
        List<String> records = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
                String question = cursor.getString(cursor.getColumnIndexOrThrow(COL_QUESTION));
                String difficulty = cursor.getString(cursor.getColumnIndexOrThrow(COL_DIFFICULTY));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COL_TYPE));
                records.add("ID: " + id + "\nПитання: " + question +
                        "\nСкладність: " + difficulty + "\nТип: " + type);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return records;
    }
    public boolean isEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();
        return count == 0;
    }
    public void deleteAllRecords() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
    }
}
