package com.example.doctorhome.uis.ForPatient.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Adapter.ForPatient.Doctor3Adapter;
import com.example.doctorhome.Adapter.ForPatient.DoctorAdapter;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.DiseaseRepository;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.Disease;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.example.doctorhome.uis.ForPatient.DoctorListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PatientBookingFragment extends Fragment {
    RecyclerView doctorRecycler;
    RecyclerView dentistRecycler;
    RecyclerView.Adapter adapter;
    NavController navController;
    AutoCompleteTextView autoCompleteTextView;

    DiseaseRepository diseaseRepository;
    UserRepository userRepository;
    ArrayList<Disease> diseases;
    ArrayList<User> doctors;


    public PatientBookingFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_patient_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        getWidget();
        showData();
        doctorRecycler();
        dentistRecycler();

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                int major_id = 0;
                for (Disease disease: diseases
                ) {
                    if(disease.getDiseaseName().equals(selectedItem)) {
                        major_id = disease.getMajorId();
                    }
                }
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("majorId", major_id);
                editor.apply();
                Intent intent = new Intent(requireContext(), DoctorListActivity.class);
                startActivity(intent);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public void getWidget() {
        doctorRecycler = requireView().findViewById(R.id.doctors_recycler);
        dentistRecycler = requireView().findViewById(R.id.dentist_recycler);
        autoCompleteTextView = requireView().findViewById(R.id.autoCompleteTextView);
        diseaseRepository = new DiseaseRepository(new DBHelper(requireContext()));
        diseases = diseaseRepository.getAllDiseaseByName();
        userRepository = new UserRepository(new DBHelper(requireContext()));
        navController = NavHostFragment.findNavController(this);
    }
    public void showData() {

        List<String> data = getDiseaseName();
        List<Integer> images = Arrays.asList(R.drawable.card, R.drawable.avatar, R.drawable.avatar);
        CustomAdapter adapter = new CustomAdapter(requireContext(), data, images);
        autoCompleteTextView.setAdapter(adapter);
    }
    public List<String> getDiseaseName() {
        List<String> diseaseName = new ArrayList<>();
        for (Disease d: diseases
        ) {
            diseaseName.add(d.getDiseaseName());
        }
        return diseaseName;
    }
    public class CustomAdapter extends ArrayAdapter<String> {
        private Context context;
        private List<String> data;
        private List<Integer> images;

        public CustomAdapter(Context context, List<String> data, List<Integer> images) {
            super(context, R.layout.item_patient_dropdown_autocomplete, data);
            this.context = context;
            this.data = data;
            this.images = images;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = convertView != null ? convertView : LayoutInflater.from(context).inflate(R.layout.item_patient_dropdown_autocomplete, parent, false);

            TextView textView = view.findViewById(R.id.textViewItem);
            ImageView imageView = view.findViewById(R.id.imageViewItem);

            textView.setText(getItem(position));

            return view;
        }

        @NonNull
        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return getView(position, convertView, parent);
        }
    }
    private void doctorRecycler() {

        doctorRecycler.setHasFixedSize(true);
        doctorRecycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<User> doctors = new ArrayList<>();
        doctors.add(userRepository.getUserByUsername("doctor1"));
        doctors.add(userRepository.getUserByUsername("doctor2"));
        doctors.add(userRepository.getUserByUsername("doctor3"));
        DoctorAdapter doctorAdapter = new DoctorAdapter(getContext(), doctors);
        doctorRecycler.setAdapter(doctorAdapter);

    }

    private void dentistRecycler() {
        dentistRecycler.setHasFixedSize(true);
        dentistRecycler.setLayoutManager(new LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false));

        ArrayList<User> dentists = new ArrayList<>();
        User u1 = userRepository.getUserByUsername("doctor1");
        User u3 = userRepository.getUserByUsername("doctor2");
        User u2 = userRepository.getUserByUsername("doctor3");

        dentists.add(u1);
        dentists.add(u2);
        dentists.add(u3);

        adapter = new Doctor3Adapter(requireContext(),dentists);
        dentistRecycler.setAdapter(adapter);

    }


}