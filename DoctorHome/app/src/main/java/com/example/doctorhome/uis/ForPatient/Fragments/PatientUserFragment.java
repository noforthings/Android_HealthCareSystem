    package com.example.doctorhome.uis.ForPatient.Fragments;

    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.graphics.Color;
    import android.graphics.drawable.ColorDrawable;
    import android.os.Bundle;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.Window;
    import android.view.WindowManager;
    import android.widget.Button;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.navigation.NavController;
    import androidx.navigation.fragment.NavHostFragment;

    import com.example.doctorhome.uis.MainActivity;
    import com.example.doctorhome.Models.DBHelper.DBHelper;
    import com.example.doctorhome.Models.DBHelper.UserRepository;
    import com.example.doctorhome.Models.User;
    import com.example.doctorhome.R;
    import com.example.doctorhome.uis.ForPatient.ChangePasswordActivity;
    import com.example.doctorhome.uis.ForPatient.UpdateInfoActivity;


    public class PatientUserFragment extends Fragment {
        Button btnUpdateInfo, btnChangePassword, btnLogout;
        TextView userFullName;
        NavController navController;
        UserRepository userRepository;
        SharedPreferences sharedPreferences;
        User currentUser;



        public PatientUserFragment() {
            // Required empty public constructor
        }

        public interface LogoutListener {
            void onLogout();
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_patient_user, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            getWidget(view);
            addEvent();
            super.onViewCreated(view, savedInstanceState);
        }
        private void getWidget(View view) {
            btnChangePassword = view.findViewById(R.id.btnPassword);
            btnUpdateInfo = view.findViewById(R.id.btnInfo);
            btnLogout = view.findViewById(R.id.btnLogout);
            userFullName = view.findViewById(R.id.txtUserFullname);
            navController = NavHostFragment.findNavController(this);
            userRepository = new UserRepository(new DBHelper(requireContext()));
            sharedPreferences = requireContext().getSharedPreferences("app", Context.MODE_PRIVATE);
            if(sharedPreferences.contains("userId")) {
                int userId = sharedPreferences.getInt("userId", 2);
                currentUser = userRepository.getUserById(userId);
                userFullName.setText(currentUser.getFullname());
            }
        }
        private void addEvent() {
            MyClick myClick = new MyClick();
            btnLogout.setOnClickListener(myClick);
            btnChangePassword.setOnClickListener(myClick);
            btnUpdateInfo.setOnClickListener(myClick);
        }
        class MyClick implements View.OnClickListener {

            @Override
            public void onClick(View v) {
                if(v == btnLogout) {
                    openPopupLogout();
                } else if(v == btnChangePassword) {
                   Intent intent = new Intent(requireContext(), ChangePasswordActivity.class);
                   requireContext().startActivity(intent);
                } else if (v== btnUpdateInfo) {
                    Intent intent = new Intent(requireContext(), UpdateInfoActivity.class);
                    requireContext().startActivity(intent);
                }
            }
        }
        private void openPopupLogout() {
            final Dialog dialog = new Dialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_patient_popup_logout);

            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("app", Context.MODE_PRIVATE);

            Button btnOk = dialog.findViewById(R.id.btnLogoutAgain);
            Button btnCancel = dialog.findViewById(R.id.btnCancelAgain);
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String keyToRemove = "password";
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove(keyToRemove);
                    editor.apply();
                    Intent intent = new Intent(requireActivity(), MainActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                }
            });
        }
    }