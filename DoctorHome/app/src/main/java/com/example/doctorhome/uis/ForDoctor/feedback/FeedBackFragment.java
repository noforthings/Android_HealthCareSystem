package com.example.doctorhome.uis.ForDoctor.feedback;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.doctorhome.R;
import com.example.doctorhome.databinding.FragmentFeedBackBinding;

public class FeedBackFragment extends Fragment {
    private FragmentFeedBackBinding binding;
    private ListView lvDoctorFeedback;
    private ArrayAdapter<String> adapterInfo = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FeedBackViewModel feedBackViewModel =
                new ViewModelProvider(this).get(FeedBackViewModel.class);

        binding = FragmentFeedBackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getWidget(root);
        getEvent();

        feedBackViewModel.getFeedbacks().observe(getViewLifecycleOwner(), feedbacks -> {
            adapterInfo = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, feedbacks);
            lvDoctorFeedback.setAdapter(adapterInfo);
        });

        return root;
    }
    private void getWidget(View root) {
        lvDoctorFeedback = root.findViewById(R.id.lvDoctorFeedback);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) lvDoctorFeedback.getLayoutParams();
        layoutParams.setMargins(0, 10, 0, 10);
    }
    private void getEvent(){

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}