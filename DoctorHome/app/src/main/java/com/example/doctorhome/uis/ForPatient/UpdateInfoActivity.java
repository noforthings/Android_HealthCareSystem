package com.example.doctorhome.uis.ForPatient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;

public class UpdateInfoActivity extends AppCompatActivity {
    Button btnSave;
    EditText etFullName, etEmail, etPhone;
    ImageButton btnBack;
    ImageView userImage;
    User currentUser;
    UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_update_info);
        getWidget();
        addEvent();
    }
    private void getWidget() {
        etFullName = findViewById(R.id.txtFullName);
        etEmail = findViewById(R.id.txtEmail);
        etPhone = findViewById(R.id.txtPhone);
        btnSave = findViewById(R.id.btnChange2);
        btnBack = findViewById(R.id.btnBack3);
        userImage = findViewById(R.id.profile_image2);
        userRepository = new UserRepository(new DBHelper(this));
        SharedPreferences sharedPreferences = this.getSharedPreferences("app", Context.MODE_PRIVATE);
        int userId;
        if(sharedPreferences.contains("userId")) {
            userId = sharedPreferences.getInt("userId", 2);
            currentUser = userRepository.getUserById(userId);
        }
        etFullName.setText(currentUser.getFullname());
        etEmail.setText(currentUser.getEmail());
        etPhone.setText(currentUser.getPhone());
    }
    private void addEvent() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserInfo();
            }
        });
    }
    private void updateUserInfo() {
        String fullname = etFullName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        if (fullname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            Toast.makeText(UpdateInfoActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(UpdateInfoActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return;
        }
        if (!isValidPhone(phone)) {
            Toast.makeText(UpdateInfoActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
            etPhone.requestFocus();
            return;
        }

        if (phone.length() < 10) {
            Toast.makeText(UpdateInfoActivity.this, "Phone number must be at least 10 digits long", Toast.LENGTH_SHORT).show();
            etPhone.requestFocus();
            return;
        }
        currentUser.setFullname(fullname);
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        if(userRepository.updateUser(currentUser)) {
            Toast.makeText(UpdateInfoActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(UpdateInfoActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
            etFullName.requestFocus();
        }

    }
    private boolean isValidEmail(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }
    private boolean isValidPhone(String phone) {
        return phone.matches("\\d+");
    }
}