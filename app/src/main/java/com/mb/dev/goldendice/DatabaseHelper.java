package com.mb.dev.goldendice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GoldenDice.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "results";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RESULT = "result";
    private static final String COLUMN_DICE_TYPE = "dice_type";

    private int insertCount = 0;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_RESULT + " INTEGER, " +
                COLUMN_DICE_TYPE + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addResult(int result, String diceType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_RESULT, result);
        cv.put(COLUMN_DICE_TYPE, diceType);

        db.insert(TABLE_NAME, null, cv);

        insertCount++;
        if (insertCount >= 5) {
            db.execSQL("DELETE FROM " + TABLE_NAME);
            insertCount = 0;
        }
    }

    public List<Result> getAllResults() {
        List<Result> results = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_ID + " DESC LIMIT 5", null);

        if (cursor.moveToFirst()) {
            do {
                int resultColumnIndex = cursor.getColumnIndex(COLUMN_RESULT);
                int diceTypeColumnIndex = cursor.getColumnIndex(COLUMN_DICE_TYPE);

                int result = resultColumnIndex != -1 ? cursor.getInt(resultColumnIndex) : 0;
                String diceType = diceTypeColumnIndex != -1 ? cursor.getString(diceTypeColumnIndex) : null;

                results.add(new Result(result, diceType));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return results;
    }
}