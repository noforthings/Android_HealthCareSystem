package com.example.doctorhome.uis.ForDoctor.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.doctorhome.R;
import com.example.doctorhome.databinding.FragmentDoctorGalleryBinding;

public class GalleryFragment extends Fragment {

    private FragmentDoctorGalleryBinding binding;
    private ListView lvHospitalInfo;
    private ArrayAdapter<String> adapterInfo = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentDoctorGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getWidget(root);
        getEvent();

        galleryViewModel.getInfomations().observe(getViewLifecycleOwner(), informations -> {
            adapterInfo = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, informations);
            lvHospitalInfo.setAdapter(adapterInfo);
        });

        return root;
    }
    private void getWidget(View root) {
        lvHospitalInfo = root.findViewById(R.id.lvHospitalInfo);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) lvHospitalInfo.getLayoutParams();
        layoutParams.setMargins(0, 16, 0, 16);
    }
    private void getEvent(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}