package com.example.doctorhome.uis.ForPatient.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Adapter.ForPatient.UpComingAdapter;
import com.example.doctorhome.Adapter.ForPatient.CompletedAdapter;
import com.example.doctorhome.Models.Appointment;
import com.example.doctorhome.Models.DBHelper.AppointmentRepository;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.R;
import com.example.doctorhome.uis.ForPatient.BookingDetailActivity;

import java.util.ArrayList;


public class fragCompleted extends Fragment {


    private RecyclerView recyclerView;
    private CompletedAdapter adapter;
    private ArrayList<Appointment> data = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    AppointmentRepository appointmentRepository;
    NavController navController;

    public fragCompleted(NavController navController) {
        this.navController = navController;
    }
    public fragCompleted() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_completed, container, false);
        recyclerView = view.findViewById(R.id.upcomingRecyler);
        sharedPreferences = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        int userId = 0;
        if (sharedPreferences.contains("userId")) {
            userId = sharedPreferences.getInt("userId", 0);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        appointmentRepository = new AppointmentRepository(new DBHelper(getContext()));
        data = appointmentRepository.getAppointmentsByStatusForUser(userId, 1);
        ArrayList<Appointment> completed = new ArrayList<>();
        for (Appointment appointment : data) {
            if (appointment.getStatus() == 1) {
                completed.add(appointment);
            }
        }
        adapter = new CompletedAdapter(getActivity(), completed);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new UpComingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Appointment clickedAppointment = completed.get(position);
                Toast.makeText(getActivity(), "Clicked on appointment: " + clickedAppointment.getWorkingSlotId(), Toast.LENGTH_SHORT).show();
            }
        });
        adapter.setOnButtonClickListener(new UpComingAdapter.OnButtonClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onButtonClick(int position) {
                Appointment canceledAppointment = completed.get(position);
                int doctorId = canceledAppointment.getDoctorId();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Toast.makeText(requireContext(), " " + doctorId , Toast.LENGTH_SHORT).show();
                editor.putInt("doctorId", doctorId);
                editor.apply();
                Intent intent = new Intent(requireContext(), BookingDetailActivity.class);
                requireContext().startActivity(intent);
            }
        });
        return view;
    }
}