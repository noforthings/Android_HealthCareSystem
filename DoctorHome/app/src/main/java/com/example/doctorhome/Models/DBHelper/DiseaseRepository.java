package com.example.doctorhome.Models.DBHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doctorhome.Models.Disease;

import java.util.ArrayList;

public class DiseaseRepository {
    private final String TABLE_NAME = "DISEASE";
    private DBHelper dbHelper;

    public DiseaseRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @SuppressLint("Range")
    public ArrayList<Disease> getAllDiseaseByName() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Disease> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {

                Disease disease = new Disease();
                disease.setId(Integer.parseInt(cursor.getString(0)));
                disease.setDiseaseName(cursor.getString(1));
                disease.setDescription(cursor.getString(2));
                disease.setMajorId(Integer.parseInt(cursor.getString(3)));
                list.add(disease);

            } while (cursor.moveToNext());
        }

        return list;
    }
    public void addDisease(Disease disease) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", disease.getId());
        values.put("name", disease.getDiseaseName());
        values.put("description", disease.getDescription());
        values.put("major_id", disease.getMajorId());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }
    public void updateDisease(Disease disease) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", disease.getDiseaseName());
        values.put("description", disease.getDescription());
        values.put("major_id", disease.getMajorId());
        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(disease.getId())});
        db.close();
    }
}
