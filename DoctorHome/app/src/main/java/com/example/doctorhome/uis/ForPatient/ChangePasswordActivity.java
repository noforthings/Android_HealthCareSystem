package com.example.doctorhome.uis.ForPatient;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;

public class ChangePasswordActivity extends AppCompatActivity {
    Button btnChange;
    ImageButton btnBack;
    EditText txtOldPassword, txtNewPassword, txtConfirmPassword;
    User currentUser;
    UserRepository userRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_change_password);
        getWidget();
        addEvent();
    }
    private void getWidget() {
        btnChange = findViewById(R.id.btnChange2);
        btnBack = findViewById(R.id.btnBack);
        txtOldPassword = findViewById(R.id.txtFullName);
        txtNewPassword = findViewById(R.id.txtEmail);
        txtConfirmPassword = findViewById(R.id.txtPhone);

        userRepository = new UserRepository(new DBHelper(this));
        SharedPreferences sharedPreferences = this.getSharedPreferences("app", Context.MODE_PRIVATE);
        int userId;
        if(sharedPreferences.contains("userId")) {
            userId = sharedPreferences.getInt("userId", 2);
            currentUser = userRepository.getUserById(userId);
        }
    }
    private void addEvent() {
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = txtOldPassword.getText().toString();
                String newPassword = txtNewPassword.getText().toString();
                String confirmPassword = txtConfirmPassword.getText().toString();

                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }
                currentUser.setPassword(newPassword);
                if(userRepository.updateUser(currentUser)) {
                    Toast.makeText(ChangePasswordActivity.this, "Update success full!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(ChangePasswordActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}