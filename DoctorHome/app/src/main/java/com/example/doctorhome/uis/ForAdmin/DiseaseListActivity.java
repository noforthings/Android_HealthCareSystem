package com.example.doctorhome.uis.ForAdmin;

import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Adapter.ForAdmin.DiseaseAdapter;
import com.example.doctorhome.Models.DBHelper.DiseaseRepository;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.Disease;
import com.example.doctorhome.Models.DBHelper.MajorRepository;
import com.example.doctorhome.R;

import java.util.ArrayList;

public class DiseaseListActivity extends AppCompatActivity {
    private Button btn_add, btn_submit;
    RecyclerView diseaseRecycler;
    ImageButton btn_back;
    RecyclerView.Adapter adapter;
    ArrayList<Disease> disease;
    DiseaseRepository diseaseRepository;
    EditText txtDisName,txtDescription;
    Spinner spinnerMajor;
    TextView textView;
    Disease newDisease;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_manage_disease);
        getWidget();
        diseaseRepository = new DiseaseRepository(new DBHelper(this));
        disease_list_recycler();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(DiseaseListActivity.this);
                dialog.setContentView(R.layout.activity_admin_add_disease);
                txtDisName = dialog.findViewById(R.id.disease_name_add);
                spinnerMajor = dialog.findViewById(R.id.major2);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(DiseaseListActivity.this,R.array.majors, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMajor.setAdapter(adapter);
                spinnerMajor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String text = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                txtDescription = dialog.findViewById(R.id.disease_desc_add);
                btn_submit = dialog.findViewById(R.id.submitbtn2);
                textView = dialog.findViewById(R.id.disease_update);
                btn_submit.setText("Submit");
                textView.setText("Add Disease");
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String disName, disDesc;
                        int id,major;
                        id = disease.size()+1;
                        disName = txtDisName.getText().toString();
                        disDesc = txtDescription.getText().toString();
                        newDisease = new Disease();
                        newDisease.setDiseaseName(disName);
                        newDisease.setDescription(disDesc);
                        diseaseRepository.addDisease(newDisease);
                        disease.set(disease.size()-1,newDisease);
                        adapter.notifyDataSetChanged();
                        diseaseRecycler.scrollToPosition(disease.size() -1);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }

        });
    }

    private void getWidget() {
        btn_add = findViewById(R.id.add_disease_btn);
        diseaseRecycler = findViewById(R.id.disease_list);
        btn_back = findViewById(R.id.btnBack);
        //diseaseRepository = new DiseaseRepository(new DBHelper(this));
    }
    private void disease_list_recycler() {
        diseaseRecycler.setHasFixedSize(true);
        diseaseRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        disease = new ArrayList();
        disease = diseaseRepository.getAllDiseaseByName();
        adapter = new DiseaseAdapter(this,disease);
        diseaseRecycler.setAdapter(adapter);

    }
    public void goBackAdmin(){
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiseaseListActivity.this, AdminMainActivity.class);
                startActivity(intent);
            }
        });
    }
}
