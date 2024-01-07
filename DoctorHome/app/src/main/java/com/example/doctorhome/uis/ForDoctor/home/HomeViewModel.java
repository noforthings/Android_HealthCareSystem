package com.example.doctorhome.uis.ForDoctor.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doctorhome.Models.Appointment;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<ArrayList<Appointment>> appointments = new MutableLiveData<>();

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<ArrayList<Appointment>> getAppointment() {
        return appointments;
    }
    public void setAppointments(ArrayList<Appointment> newAppointments) {
        appointments.setValue(newAppointments);
    }
}