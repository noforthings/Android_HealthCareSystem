package com.example.doctorhome.uis.ForAdmin;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.doctorhome.R;
import com.example.doctorhome.uis.MainActivity;


public class AdminMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_doc,btn_major;
    private CardView docView,disView, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        getWidget();
        docView.setOnClickListener(this);
        disView.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }
    public void getWidget(){

        docView = findViewById(R.id.doctor_table);
        disView = findViewById(R.id.disease_table);
        btnLogout = findViewById(R.id.logoutx);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.doctor_table){
            Intent intent = new Intent(AdminMainActivity.this, DoctorList2Activity.class);
            startActivity(intent);
        }
        else if(i == R.id.disease_table){
            Intent intent = new Intent(AdminMainActivity.this, DiseaseListActivity.class);
            startActivity(intent);
        }
        else if(i == R.id.logoutx) {
            openPopupLogout();
        }
    }
    private void openPopupLogout() {
        final Dialog dialog = new Dialog(this);
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
        SharedPreferences sharedPreferences = getSharedPreferences("app", Context.MODE_PRIVATE);

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
                Intent intent = new Intent(AdminMainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}