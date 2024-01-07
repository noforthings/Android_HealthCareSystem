package com.example.doctorhome.uis.ForPatient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Adapter.ForPatient.Doctor2Adapter;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;

import java.util.ArrayList;

public class DoctorListActivity extends AppCompatActivity  {

    RecyclerView doctor_list;
    NavController navController;
    RecyclerView.Adapter adapter;
    ImageView back;
    UserRepository userRepository;
    ArrayList<User> doctors;
    int major_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_doctor_list);
        getWidget();
        addEvent();
        Intent intent = getIntent();
        if (intent.hasExtra("major_id")) {
            major_id = intent.getIntExtra("major_id",1);
        }
        doctors = userRepository.getUserByMajorID(major_id);

        doctor_list_recycler();

    }

    public void getWidget() {
        back = findViewById(R.id.btnBack);
        doctor_list = findViewById(R.id.doctors_recycler);
        userRepository = new UserRepository(new DBHelper(this));
        SharedPreferences sharedPreferences =getSharedPreferences("app", Context.MODE_PRIVATE);
        if (sharedPreferences.contains("majorId")) {
            major_id = sharedPreferences.getInt("majorId",1);
        }
        doctors = userRepository.getUserByMajorID(major_id);
        doctor_list_recycler();
    }
    public void addEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void doctor_list_recycler() {
        doctor_list.setHasFixedSize(true);
        doctor_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new Doctor2Adapter(this,doctors);
        doctor_list.setAdapter(adapter);
    }

}