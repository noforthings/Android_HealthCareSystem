package com.example.doctorhome.uis.ForDoctor.account;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.example.doctorhome.databinding.FragmentDoctorAccountBinding;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AccountFragment extends Fragment {

    private FragmentDoctorAccountBinding binding;
    private User doctor;
    private ImageView imgAvatar;
    private TextView txtCallHotline;
    private EditText editTxtDoctorName;
    private EditText editTxtDoctorPhone;
    private EditText editTxtDoctorEmail;
    //private EditText editTxtDoctorBirth;
    private EditText editTxtDoctorDescription;
    private ImageButton editButtonName, editButtonDate, editButtonPhone, editButtonEmailAddress, editButtonDescription;
    private Button btnUpdate;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = -1;
    private static final int REQUEST_CALL_PHONE = 2;
    SharedPreferences sharedPreferences;
    UserRepository userRepository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel =
                new ViewModelProvider(this).get(AccountViewModel.class);

        binding = FragmentDoctorAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getWidget(root);
        getEvent();

        // Observe changes in user-related data
        accountViewModel.getDoctorInfo().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                // Update UI based on user data
                editTxtDoctorName.setText(user.getFullname());
                //editTxtDoctorBirth.setText(user.getDoctor_avatar());
                editTxtDoctorPhone.setText(user.getPhone());
                editTxtDoctorEmail.setText(user.getEmail());
                editTxtDoctorDescription.setText(user.getDescription());
                setDoctorInfo(user);
            }
        });

        return root;
    }

    private void getWidget(View root) {
        imgAvatar = root.findViewById(R.id.imgDoctorAvatar);
        txtCallHotline = root.findViewById(R.id.txtCallHotline);
        editTxtDoctorName = root.findViewById(R.id.editTxtDoctorName);
        //editTxtDoctorBirth = root.findViewById(R.id.editTxtDoctorBirth);
        editTxtDoctorPhone = root.findViewById(R.id.editTxtDoctorPhone);
        editTxtDoctorEmail = root.findViewById(R.id.editTxtDoctorEmailAddress);
        editTxtDoctorDescription = root.findViewById(R.id.editTxtDoctorDescription);
        setEditTextFieldsEditable(false);

        editButtonName = root.findViewById(R.id.editButtonName);
        //editButtonDate = root.findViewById(R.id.editButtonDate);
        editButtonPhone = root.findViewById(R.id.editButtonPhone);
        editButtonEmailAddress = root.findViewById(R.id.editButtonTextEmailAddress);
        editButtonDescription = root.findViewById(R.id.editButtonDescription);

        btnUpdate = root.findViewById(R.id.btnUpdate);

        userRepository = new UserRepository(new DBHelper(getContext()));
        sharedPreferences = getActivity().getSharedPreferences("app", getContext().MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("password")) {
            int userId = sharedPreferences.getInt("userId", 1);
            doctor = userRepository.getUserById(userId);
            doctor.setImage(requireContext().getFilesDir().getPath() + "/profile_image"+doctor.getId()+".png");
            setDoctorInfo(doctor);
        }
        //set avatar
        String filePath = requireContext().getFilesDir().getPath() + "/profile_image"+doctor.getId()+".png";
        if (filePath != null) {
            imgAvatar.setImageURI(Uri.parse(filePath));
        }
    }
    private void getEvent() {
        // Set a click listener for each edit button
        editButtonName.setOnClickListener(v -> toggleEditableState(editTxtDoctorName));
        //editButtonDate.setOnClickListener(v -> toggleEditableState(editTxtDoctorBirth));
        editButtonPhone.setOnClickListener(v -> toggleEditableState(editTxtDoctorPhone));
        editButtonEmailAddress.setOnClickListener(v -> toggleEditableState(editTxtDoctorEmail));
        editButtonDescription.setOnClickListener(v -> toggleEditableState(editTxtDoctorDescription));

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (doctor != null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    builder.setTitle("Confirmation")
                            .setMessage("Are you sure you want to update?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (checkInfo()) {
                                        setUpdateDoctorInfo(doctor);
                                        if (userRepository.updateUser(doctor)) {
                                            Toast.makeText(requireContext(), "Your profile has been updated!", Toast.LENGTH_LONG).show();
                                            setEditTextFieldsEditable(false);
                                        } else {
                                            Toast.makeText(requireContext(), "Your profile has not been updated!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });
                    builder.create().show();
                }
            }
        });
        imgAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivityForResult(intent, PICK_IMAGE_REQUEST);
                } else {
                    Toast.makeText(requireContext(), "No app to handle image selection", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });
        txtCallHotline.setOnClickListener(new OtherEvent());
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            // Now you have the URI of the selected image, you can load it into your ImageView using Glide
            Glide.with(this)
                    .load(selectedImageUri)
                    .placeholder(R.drawable.ganyu) // Set a placeholder drawable while the image is loading
                    .error(R.drawable.ganyu) // Set a drawable for error cases
                    .into(imgAvatar);
            saveImageToInternalStorage(selectedImageUri);
        }
    }
    private void saveImageToInternalStorage(Uri imageUri) {
        try {
            InputStream inputStream = requireContext().getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // Save the bitmap to internal storage
            String fileName = "profile_image" + doctor.getId() + ".png";
            FileOutputStream outputStream = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();

            // You can now use the file path or file name for further use
            // String filePath = requireContext().getFilesDir() + "/" + fileName;
            // or
            // String filePath = getFilesDir().getPath() + "/profile_image.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private class OtherEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Confirmation")
                    .setMessage("Are you sure you want to call hotline?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //Check permission
                            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                // You have the permission, so you can proceed with the call
                                makePhoneCall();
                            } else {
                                // You don't have the permission, request it from the user
                                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PHONE);
                            }
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now make the call
                makePhoneCall();
            } else {
                // Permission denied, inform the user
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setDoctorInfo(User doctor) {
        editTxtDoctorName.setText(doctor.getFullname());
        //editTxtDoctorBirth.setText(doctor.getEmail());
        editTxtDoctorEmail.setText(doctor.getEmail());
        editTxtDoctorPhone.setText(doctor.getPhone());
        editTxtDoctorDescription.setText(doctor.getDescription());
    }
    private void setUpdateDoctorInfo(User doctor) {
        doctor.setFullname(String.valueOf(editTxtDoctorName.getText()));
        doctor.setEmail(String.valueOf(editTxtDoctorEmail.getText()));
        doctor.setPhone(String.valueOf(editTxtDoctorPhone.getText()));
        doctor.setDescription(String.valueOf(editTxtDoctorDescription.getText()));
        //editTxtDoctorBirth.setText(doctor.getEmail());
    }
    private void setEditTextFieldsEditable(boolean isEditable) {
        editTxtDoctorName.setEnabled(isEditable);
        //editTxtDoctorBirth.setEnabled(isEditable);
        editTxtDoctorEmail.setEnabled(isEditable);
        editTxtDoctorDescription.setEnabled(isEditable);
        editTxtDoctorPhone.setEnabled(isEditable);
    }
    private void toggleEditableState(EditText editText) {
        boolean isEditable = !editText.isEnabled();
        editText.setEnabled(isEditable);
    }
    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + txtCallHotline.getText())); // Replace with the actual phone number
        startActivity(intent);
    }
    private boolean checkInfo() {
        if (!isValidEmail(editTxtDoctorEmail.getText().toString())) {
            Toast.makeText(requireContext(), "Invalid email!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!isValidPhone(editTxtDoctorPhone.getText().toString())){
            Toast.makeText(requireContext(), "Invalid phone!", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (editTxtDoctorName.getText().toString().isEmpty()) {
            Toast.makeText(requireContext(), "Invalid name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private boolean isValidPhone(String phone) {
        String phonePattern = "\\d{10}";
        return phone.matches(phonePattern);
    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}