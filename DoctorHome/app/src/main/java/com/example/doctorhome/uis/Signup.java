package com.example.doctorhome.uis;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Signup extends AppCompatActivity {

    private TextInputLayout emailTextInputLayout;
    private TextInputLayout passwordTextInputLayout;
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private TextInputLayout usernameTextInputLayout;
    private TextInputEditText usernameEditText;
    private Button btnSignUp;
    private Button btnBack;
    private boolean isValidEmail;
    private boolean isValidUsername;
    private boolean isValidPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWidget();
        addEvent();
    }

    private void getWidget() {
        emailTextInputLayout = findViewById(R.id.layoutEmail);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        passwordTextInputLayout = findViewById(R.id.layoutPassword);
        usernameEditText = findViewById(R.id.username);
        usernameTextInputLayout = findViewById(R.id.layoutUsername);
        btnBack = findViewById(R.id.btnLoginNew);
        btnSignUp = findViewById(R.id.btnSignupNew);
        isValidEmail = false;
        isValidUsername = false;
        isValidPassword = false;
    }

    private void addEvent() {
        emailEditText.addTextChangedListener(new MyTextChangeListener(emailEditText));
        passwordEditText.addTextChangedListener(new MyTextChangeListener(passwordEditText));
        usernameEditText.addTextChangedListener(new MyTextChangeListener(usernameEditText));
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    class MyTextChangeListener implements TextWatcher {
        View v;

        public MyTextChangeListener(View v) {
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (v == emailEditText) {
                String email = s.toString();
                if (!email.isEmpty() && !isValidEmail(email)) {
                    updateErrorState(emailTextInputLayout, "Email is not valid!", false);
                } else if (isValidEmail(email)) {
                    updateErrorState(emailTextInputLayout, null, true);
                }
            } else if (v == passwordEditText) {
                String password = s.toString();
                if (!password.isEmpty() && !isValidPassword(password)) {
                    updateErrorState(passwordTextInputLayout, "Password must be > 7 characters!", false);
                }
                if (isValidPassword(password)) {
                    updateErrorState(passwordTextInputLayout, null, true);
                }
            } else if (v == usernameEditText) {
                String userName = s.toString();
                if (!userName.isEmpty() && !isValidUsername(userName)) {
                    updateErrorState(usernameTextInputLayout, "Username must be > 5 characters!", false);
                }
                if (isValidUsername(userName)) {
                    updateErrorState(usernameTextInputLayout, null, true);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (v == passwordEditText && s.toString().isEmpty()) {
                updateErrorState(passwordTextInputLayout, "Required", false);
            } else if (v == usernameEditText && s.toString().isEmpty()) {
                updateErrorState(usernameTextInputLayout, "Required", false);
            } else if (v == emailEditText && s.toString().isEmpty()) {
                updateErrorState(emailTextInputLayout, "Required", false);
            }
        }

        private void updateErrorState(TextInputLayout textInputLayout, String helperText, boolean isValid) {
            textInputLayout.setHelperText(helperText);
            int color = isValid ? R.color.accent_900 : R.color.white;
            ColorStateList customTintList = ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), color));
            textInputLayout.setEndIconTintList(customTintList);

            if (v == emailEditText) {
                isValidEmail = isValid;
            } else if (v == passwordEditText) {
                isValidPassword = isValid;
            } else if (v == usernameEditText) {
                isValidUsername = isValid;
            }
        }
    }

    private boolean isValidEmail(CharSequence email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidUsername(CharSequence username) {
        return !TextUtils.isEmpty(username) && username.length() > 5 && !containsWhiteSpace(username.toString());
    }

    private boolean isValidPassword(CharSequence password) {
        return !TextUtils.isEmpty(password) && password.length() > 7;
    }

    private boolean containsWhiteSpace(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isWhitespace(s.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    private void signUp() {
        if (!isValidEmail || !isValidUsername || !isValidPassword) {
            return;
        }

        String username = String.valueOf(usernameEditText.getText());
        String email = String.valueOf(emailEditText.getText());
        String password = String.valueOf(passwordEditText.getText());
        User user = new User(username, password, email, 0, "New User");

        UserRepository userRepository = new UserRepository(new DBHelper(this));
        userRepository.addUser(user);

        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("username");
        editor.remove("password");
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();

        Intent intent = new Intent(Signup.this, MainActivity.class);
        startActivity(intent);
    }
}
