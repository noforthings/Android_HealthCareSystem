package com.example.doctorhome.uis.ForPatient.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doctorhome.Adapter.ForPatient.VPAdapter;
import com.example.doctorhome.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class PatientScheduleFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager;
    NavController navController;
    public PatientScheduleFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_patient_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        navController = NavHostFragment.findNavController(PatientScheduleFragment.this);
        VPAdapter vp = new VPAdapter(getChildFragmentManager(), getLifecycle(), navController);
        viewPager.setAdapter(vp);
        String[] tabTitles = {"Upcoming", "Completed", "Cancelled"};
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();
        super.onViewCreated(view, savedInstanceState);
    }
}