package com.example.doctorhome.uis.ForAdmin;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Adapter.ForAdmin.DoctorVer3Adapter;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.Models.Major;
import com.example.doctorhome.Models.DBHelper.MajorRepository;
import com.example.doctorhome.R;

import java.util.ArrayList;



public class DoctorList2Activity extends AppCompatActivity {
    private Button btn_add, btn_submit_add;
    ImageButton btn_back;
    RecyclerView doctorRecycler;
    RecyclerView.Adapter adapter;
    UserRepository userRepository;
    ArrayList<User> doctors;
    ImageView imageView;
    MajorRepository majorRepository;
    EditText txtDocName,txtEmail,txtPhone ,txtDescription;
    Spinner spinnerMajor;
    User newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_doctor);
        getWidget();
        doctor_list_recycler();
        goBackAdmin();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openAddDialog();
                    }

                });
            }
        });
        //storeDataInArray();
    }
    public void getWidget(){
        btn_add = findViewById(R.id.add_doc_btn);
        btn_back = findViewById(R.id.btnBack);
        doctorRecycler = findViewById(R.id.doctor_list);
        userRepository = new UserRepository(new DBHelper(this));
        majorRepository = new MajorRepository(new DBHelper(this));
    }
    private void doctor_list_recycler() {
        doctorRecycler.setHasFixedSize(true);
        doctorRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        doctors = userRepository.getAlldoctor();
        adapter = new DoctorVer3Adapter(this,doctors);
        doctorRecycler.setAdapter(adapter);
    }
    public void goBackAdmin(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DoctorList2Activity.this, AdminMainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void openAddDialog(){
        Dialog dialog = new Dialog(DoctorList2Activity.this);
        dialog.setContentView(R.layout.layout_admin_popup_add_doctor);
        imageView = dialog.findViewById(R.id.doc_img_add);
        txtDocName = dialog.findViewById(R.id.doc_name_add);
        spinnerMajor = dialog.findViewById(R.id.major_add);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(DoctorList2Activity.this,R.array.majors, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMajor.setAdapter(spinnerAdapter);
        spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String text = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        txtEmail = dialog.findViewById(R.id.email_add);
        txtPhone = dialog.findViewById(R.id.phone_add);
        txtDescription = dialog.findViewById(R.id.description_add);
        btn_submit_add = dialog.findViewById(R.id.add_submit_btn);
        btn_submit_add.setText("Submit");
        btn_submit_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String docName,email, phone, docDesc,majorName;
                int major;
                docName = txtDocName.getText().toString();
                email = txtEmail.getText().toString();
                phone = txtPhone.getText().toString();
                docDesc = txtDescription.getText().toString();
                major = Integer.parseInt(spinnerMajor.getSelectedItem().toString());
                majorName = majorRepository.replaceIdToName(major);
                User u = new User(docName,email,phone,docDesc,major,majorName);
                doctors.add(u);
                userRepository.addUser(u);
                adapter.notifyDataSetChanged();
                doctorRecycler.scrollToPosition(doctors.size() -1);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
