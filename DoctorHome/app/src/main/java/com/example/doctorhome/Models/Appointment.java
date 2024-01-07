package com.example.doctorhome.Models;

public class Appointment {
    int id;
    int patientId;
    int doctorId;
    int workingSlotId;
    String time;
    int status;
    String description;
    String address;

    public Appointment(int id, int patientId, int doctorId, int workingSlotId, String time, int status, String description, String address) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.workingSlotId = workingSlotId;
        this.time = time;
        this.status = status;
        this.description = description;
        this.address = address;
    }

    public Appointment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getWorkingSlotId() {
        return workingSlotId;
    }

    public void setWorkingSlotId(int workingSlotId) {
        this.workingSlotId = workingSlotId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
