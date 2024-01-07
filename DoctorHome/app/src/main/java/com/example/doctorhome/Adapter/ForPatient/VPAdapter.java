package com.example.doctorhome.Adapter.ForPatient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavController;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doctorhome.uis.ForPatient.Fragments.fragCompleted;
import com.example.doctorhome.uis.ForPatient.Fragments.fragUpcoming;

public class VPAdapter extends FragmentStateAdapter {
    NavController navController;

    public VPAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public VPAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public VPAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, NavController navController) {
        super(fragmentManager, lifecycle);
        this.navController = navController;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                 return new fragUpcoming();
            default:
                return new fragCompleted(navController);

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}
