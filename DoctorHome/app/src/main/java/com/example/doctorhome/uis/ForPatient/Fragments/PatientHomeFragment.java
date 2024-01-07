package com.example.doctorhome.uis.ForPatient.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;

public class PatientHomeFragment extends Fragment {
    Button btnFind;
    Button btnCheck;

    TextView txtFullname;
    NavController navController;
    UserRepository userRepository;
    SharedPreferences sharedPreferences;
    User currentUser;
    int currentUserId;

    public PatientHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_patient_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getWidget();
        addEvent();
        super.onViewCreated(view, savedInstanceState);
    }

    private void getWidget() {
        btnCheck = requireView().findViewById(R.id.btnCheck);
        btnFind = requireView().findViewById(R.id.btnFind);
        txtFullname = requireView().findViewById(R.id.txtHello);
        navController = NavHostFragment.findNavController(PatientHomeFragment.this);
        userRepository = new UserRepository(new DBHelper(requireContext()));
        sharedPreferences = requireContext().getSharedPreferences("app", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("doctorId", 17);
        editor.apply();
        if(sharedPreferences.contains("userId")) {
            currentUserId = sharedPreferences.getInt("userId", 1);
            currentUser = userRepository.getUserById(currentUserId);
            String text = "Hi, " + currentUser.getFullname();
            txtFullname.setText(text);
        }

    }
    private void addEvent() {
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_scheduleFragment);
            }
        });
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_homeFragment_to_bookingFragment);
            }
        });
    }

}