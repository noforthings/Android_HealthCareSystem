package com.example.doctorhome.Models;

public class WorkingSlot {
    int id;
    String day;
    String timeslot;
    String doctorID;
    int isBooked;

    public WorkingSlot(int id, String day, String timeslot, String doctorID, int isBooked) {
        this.id = id;
        this.day = day;
        this.timeslot = timeslot;
        this.doctorID = doctorID;
        this.isBooked = isBooked;
    }

    public WorkingSlot() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public int getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(int isBooked) {
        this.isBooked = isBooked;
    }
}
