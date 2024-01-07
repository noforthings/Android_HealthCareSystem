package com.example.doctorhome.uis.ForDoctor.account;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.doctorhome.Models.User;

public class AccountViewModel extends ViewModel {
    private MutableLiveData<User> doctorInfo;
    public AccountViewModel() {
        doctorInfo = new MutableLiveData<>();
    }

    public MutableLiveData<User> getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(MutableLiveData<User> doctorInfo) {
        this.doctorInfo = doctorInfo;
    }
}