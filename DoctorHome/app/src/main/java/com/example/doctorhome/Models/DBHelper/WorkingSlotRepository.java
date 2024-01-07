package com.example.doctorhome.Models.DBHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doctorhome.Models.WorkingSlot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class WorkingSlotRepository {
    ArrayList<String> daysOfWeek = new ArrayList<>(Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    ));
    private final String TABLE_NAME = "SLOT";
    private DBHelper dbHelper;

    public WorkingSlotRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @SuppressLint("Range")
    public ArrayList<WorkingSlot> getAllSlotOfDoctorId(int userId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<WorkingSlot> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ?";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});
        if(cursor.moveToFirst()) {
            do {
                WorkingSlot workingSlot = new WorkingSlot();
                workingSlot.setId(Integer.parseInt(cursor.getString(0)));
                workingSlot.setDay(cursor.getString(1));
                workingSlot.setTimeslot(cursor.getString(2));
                workingSlot.setDoctorID(cursor.getString(3));
                workingSlot.setIsBooked(Integer.parseInt(cursor.getString(4)));


                list.add(workingSlot);
            } while (cursor.moveToNext());
        }

        return list;
    }
    public void updateTimeSlotStatus(int slotId, int newStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_booked", newStatus);
        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(slotId)});
        db.close();
    }
    public void resetYesterday() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] daysOfWeek = {"Sunday",  "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String previousDay;
        if (dayOfWeek == 1) {
            previousDay = "Saturday";
        } else {
            previousDay = daysOfWeek[dayOfWeek - 2];
        }
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("is_booked", 0);
        db.update(TABLE_NAME, values, "dayinweek=?", new String[]{previousDay});

    }

}
