package com.example.doctorhome.uis.ForDoctor.feedback;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class FeedBackViewModel extends ViewModel {
    private final MutableLiveData<ArrayList<String>> feedbacks = new MutableLiveData<>();

    public FeedBackViewModel() {
        ArrayList<String> initFeedback = generateInfo();
        feedbacks.setValue(initFeedback);
    }
    public LiveData<ArrayList<String>> getFeedbacks() {
        return feedbacks;
    }

    private ArrayList<String> generateInfo() {
        ArrayList<String> feedbacks = new ArrayList<>();
        feedbacks.add("Positive experience with the doctor.");
        feedbacks.add("Very knowledgeable and helpful.");
        feedbacks.add("Professional and caring staff.");
        feedbacks.add("Timely appointments and minimal waiting time.");
        feedbacks.add("Clean and well-maintained facility.");
        return feedbacks;
    }
}