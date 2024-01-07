package com.example.doctorhome.uis.ForPatient.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Adapter.ForPatient.UpComingAdapter;
import com.example.doctorhome.Models.Appointment;
import com.example.doctorhome.Models.DBHelper.AppointmentRepository;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.WorkingSlotRepository;
import com.example.doctorhome.R;

import java.util.ArrayList;


public class fragUpcoming extends Fragment {
    private RecyclerView recyclerView;
    private UpComingAdapter adapter;
    int userId;
    private ArrayList<Appointment> data = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private AppointmentRepository appointmentRepository;
    private WorkingSlotRepository workingSlotRepository;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_upcoming, container, false);

        sharedPreferences = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        userId = 0;
        if (sharedPreferences.contains("userId")) {
            userId = sharedPreferences.getInt("userId", 0);
        }
        recyclerView = view.findViewById(R.id.upcomingRecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        appointmentRepository = new AppointmentRepository(new DBHelper(getContext()));
        workingSlotRepository = new WorkingSlotRepository(new DBHelper(getContext()));
        data = appointmentRepository.getAppointmentsByStatusForUser(userId, 0);
        ArrayList<Appointment> upComing = new ArrayList<>();
        for (Appointment appointment : data) {
            if (appointment.getStatus() == 0) {
                upComing.add(appointment);
            }
        }
        adapter = new UpComingAdapter(getActivity(), upComing);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UpComingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Appointment clickedAppointment = upComing.get(position);
                Toast.makeText(getActivity(), "Clicked on appointment: " + clickedAppointment.getWorkingSlotId(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnButtonClickListener(new UpComingAdapter.OnButtonClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onButtonClick(int position) {
                Appointment canceledAppointment = upComing.get(position);
                int appointmentId = canceledAppointment.getId();
                int slotId = canceledAppointment.getWorkingSlotId();
                appointmentRepository.updateAppointmentStatus(appointmentId, 2);
                workingSlotRepository.updateTimeSlotStatus(slotId, 0);
                data = appointmentRepository.getAppointmentsByStatusForUser(userId, 0);
                upComing.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }
}
