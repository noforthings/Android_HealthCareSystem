package com.example.doctorhome.uis;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorhome.Models.DBHelper.AppointmentRepository;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.DBHelper.WorkingSlotRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.example.doctorhome.uis.ForAdmin.AdminMainActivity;
import com.example.doctorhome.uis.ForDoctor.DoctorHomeActivity;
import com.example.doctorhome.uis.ForPatient.DashboardActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    TextInputEditText editText;
    TextInputLayout layoutUsername;
    TextInputLayout layoutPassword;
    TextInputEditText passwordEditText;
    Button loginButton;
    Button forgotButton;
    Button buttonSignup;
    View rootView;
    SharedPreferences sharedPreferences;
    UserRepository userRepository;
    WorkingSlotRepository workingSlotRepository;
    AppointmentRepository appointmentRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        getWidgets();
        addEvents();
        workingSlotRepository.resetYesterday();
        appointmentRepository.resetAppointmentsStatus();
    }

    @SuppressLint("SetTextI18n")
    private void getWidgets() {
        userRepository = new UserRepository(new DBHelper(this));
        workingSlotRepository = new WorkingSlotRepository(new DBHelper(this));
        appointmentRepository = new AppointmentRepository(new DBHelper(this));
        //get View
        editText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        layoutUsername = findViewById(R.id.layoutUsername);
        layoutPassword = findViewById(R.id.layoutPassword);
        loginButton = findViewById(R.id.btnLogin);
        buttonSignup = findViewById(R.id.btnSignup);
        forgotButton = findViewById(R.id.btnFogot);
        rootView = findViewById(android.R.id.content);

        //Load login status
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        if(sharedPreferences.contains("username") && sharedPreferences.contains("password")) {
            String username = sharedPreferences.getString("username", null);
            String password = sharedPreferences.getString("password", null);
            editText.setText(username);
            passwordEditText.setText(password);
            loginUser(username, password);
        }
        else if(sharedPreferences.contains("username")) {
            String username = sharedPreferences.getString("username", null);
            editText.setText(username);
        }
        else {
            editText.setText("demo");
            passwordEditText.setText("12345678");
        }
    }
    private void addEvents() {
        myClickListener clickListener = new myClickListener();
        rootView.setOnTouchListener(clickListener);
        loginButton.setOnClickListener(clickListener);
        forgotButton.setOnClickListener(clickListener);
        buttonSignup.setOnClickListener(clickListener);
    }

    class myClickListener implements View.OnClickListener, View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // Unfocused EditText when click outside
            hideKeyboard();
            editText.clearFocus();
            passwordEditText.clearFocus();
            return true;
        }

        @Override
        public void onClick(View v) {
            if (v == loginButton) {
                String username = Objects.requireNonNull(editText.getText()).toString().trim();
                String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();

                if (isValidInput(username, password)) {
                    loginUser(username, password);
                } else {
                    Toast.makeText(MainActivity.this, "Invalid input. Please check your credentials.", Toast.LENGTH_SHORT).show();
                }
            } else if (v == forgotButton) {

            } else if (v == buttonSignup) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        }
    }
    private void loginUser(String username, String password) {
        int type = userRepository.checkUserCredentials(username, password);
        if (type == -1) {
            Toast.makeText(MainActivity.this, "Login failed! Check your username and password.", Toast.LENGTH_SHORT).show();
        }
        else if (type == 0) { // log by patient
            //save user info
            saveUserInfo(username);
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);

            finish();
        } else if (type == 9999) { // log by admin
            saveUserInfo(username);
            Intent i = new Intent(MainActivity.this, AdminMainActivity.class);
            startActivity(i);
        } else { // log by doctor
            saveUserInfo(username);
            Intent i = new Intent(MainActivity.this, DoctorHomeActivity.class);
            startActivity(i);
        }
    }
    private void saveUserInfo(String username) {
        User currentUser = userRepository.getUserByUsername(username);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", currentUser.getUsername());
        editor.putString("password", currentUser.getPassword());
        editor.putInt("userId", currentUser.getId());
        editor.apply();
    }

    private boolean isValidInput(String username, String password) {
        return !username.isEmpty() && !password.isEmpty();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

}
