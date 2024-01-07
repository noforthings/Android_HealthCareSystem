package com.example.doctorhome.Models.DBHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.Calendar;

import com.example.doctorhome.Models.Appointment;

import java.util.ArrayList;

public class AppointmentRepository {

    private final String TABLE_NAME = "APPOINTMENT";
    private DBHelper dbHelper;

    public AppointmentRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    @SuppressLint("Range")
    public ArrayList<Appointment> getAppointmentsByStatusForUser(int userId, int status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Appointment> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE patient_id = '" + userId +"' " +
                "AND status = '" + status + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment();
                appointment.setId(Integer.parseInt(cursor.getString(0)));
                appointment.setPatientId(Integer.parseInt(cursor.getString(1)));

                appointment.setDoctorId(Integer.parseInt(cursor.getString(2)));
                String text = cursor.getString(6);
                String[] part = text.split("\\$");
                appointment.setDescription(part[0]);
                if (part.length >= 2) {
                    appointment.setAddress("Address: " + part[1]);
                } else {
                    appointment.setAddress("Dan Phuong Hospital");
                }
                appointment.setTime("At " + cursor.getString(4));
                appointment.setStatus(Integer.parseInt(cursor.getString(5)));
                appointment.setWorkingSlotId(Integer.parseInt(cursor.getString(3)));
                list.add(appointment);
            } while (cursor.moveToNext());
        }

        return list;
    }
    public ArrayList<Appointment> getAppointmentsByStatusForDoctor(int userId, int status) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ArrayList<Appointment> list = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_NAME
                + " WHERE doctor_id = '" + userId +"' " +
                "AND status = '" + status + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Appointment appointment = new Appointment();
                appointment.setId(Integer.parseInt(cursor.getString(0)));
                appointment.setPatientId(Integer.parseInt(cursor.getString(1)));

                appointment.setDoctorId(Integer.parseInt(cursor.getString(2)));
                String text = cursor.getString(6);
                String[] part = text.split("\\$");
                appointment.setDescription(part[0]);
                if (part.length >= 2) {
                    appointment.setAddress("Address: " + part[1]);
                } else {
                    // Handle the case where there is no second part
                    appointment.setAddress("Default Address"); // Replace with your default value or appropriate handling
                }
                appointment.setTime("At " + cursor.getString(4));
                appointment.setStatus(Integer.parseInt(cursor.getString(5)));
                appointment.setWorkingSlotId(Integer.parseInt(cursor.getString(3)));
                list.add(appointment);
            } while (cursor.moveToNext());
        }

        return list;
    }

    public void addAppointment(Appointment appointment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("patient_id", appointment.getPatientId());
        values.put("doctor_id", appointment.getDoctorId());
        values.put("slot_id", appointment.getWorkingSlotId());
        values.put("appointment_time", appointment.getTime());
        values.put("status", appointment.getStatus());
        values.put("description", appointment.getDescription());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void updateAppointmentStatus(int appointmentId, int newStatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", newStatus);
        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(appointmentId)});
        db.close();
    }
    public void resetAppointmentsStatus() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String previousDay;
        if (dayOfWeek == 1) {
            previousDay = "Saturday";
        } else {
            previousDay = daysOfWeek[dayOfWeek - 2];
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("status", 1);  // Reset status về 1
        db.update("APPOINTMENT", values, "slot_id IN (SELECT id FROM SLOT WHERE dayinweek= ? ) AND status= ? ", new String[]{previousDay, String.valueOf(0)});
        db.close();
    }
}