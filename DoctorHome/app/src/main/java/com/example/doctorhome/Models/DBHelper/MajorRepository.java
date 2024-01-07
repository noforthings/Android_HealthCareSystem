package com.example.doctorhome.Models.DBHelper;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doctorhome.Models.Major;
import com.example.doctorhome.Models.User;

import java.util.ArrayList;
import java.util.List;
public class MajorRepository {
    private final String TABLE_NAME = "MAJOR";
    private DBHelper dbHelper;
    Major major;
    public MajorRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public List<Major> getAllMajor() {
        String statement = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(statement, null);

        List<Major> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            list.add(new Major(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)));
        }

        cursor.close();
        return list;
    }
    public String replaceIdToName(int replaceMajorId){
        String statement = "SELECT name FROM " + TABLE_NAME + " WHERE id == " + replaceMajorId;
        return statement;
    }
}
