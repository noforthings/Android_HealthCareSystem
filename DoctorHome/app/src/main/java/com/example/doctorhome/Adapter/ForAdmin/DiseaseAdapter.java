package com.example.doctorhome.Adapter.ForAdmin;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.DiseaseRepository;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.DBHelper.MajorRepository;
import com.example.doctorhome.Models.Disease;

import com.example.doctorhome.R;
import com.example.doctorhome.uis.ForAdmin.DoctorList2Activity;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder>{
    ArrayList<Disease> diseases;
    Context context;
    Disease disease;
    DiseaseRepository diseaseRepository;
    MajorRepository majorRepository;
    public DiseaseAdapter (Context context, ArrayList<Disease> diseases) {
        this.context = context;
        this.diseases = diseases;
        diseaseRepository = new DiseaseRepository(new DBHelper(this.context));
    }
    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_card_disease, parent, false);
        return new DiseaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, @SuppressLint("RecyclerView") int position) {
        disease = diseases.get(position);

        holder.dis_name.setText(disease.getDiseaseName());
        holder.major.setText(String.valueOf(disease.getMajorId()));
        holder.dis_desc.setText(disease.getDescription());
        holder.diseaseItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditDialog(disease,position);
            }
        });
    }

    private void showEditDialog(Disease disease, int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_admin_add_disease);

        EditText txtName = dialog.findViewById(R.id.disease_name_add);
        EditText txtDescription = dialog.findViewById(R.id.disease_desc_add);
        Button btn_add = dialog.findViewById(R.id.submitbtn2);
        TextView textView = dialog.findViewById(R.id.disease_update);
        btn_add.setText("Edit");
        textView.setText("Edit Disease");
        txtName.setText(diseases.get(position).getDiseaseName());

        txtDescription.setText(diseases.get(position).getDescription());
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String disName, disDesc;
                disName = txtName.getText().toString();
                disDesc = txtDescription.getText().toString();
                DiseaseRepository diseaseRepository = new DiseaseRepository(new DBHelper(context));
                disease.setDiseaseName(disName);
                disease.setDescription(disDesc);
                diseaseRepository.updateDisease(disease);
                diseases.set(position,disease);
                notifyDataSetChanged();
            }
        });
        dialog.show();
    }
    @Override
    public int getItemCount() {
        if (diseases != null)
            return diseases.size();
        return 0;
    }

    public class DiseaseViewHolder extends RecyclerView.ViewHolder{
        TextView dis_name, dis_desc, major;
        ConstraintLayout diseaseItem;
        Button showInfo;
        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);
            diseaseItem = itemView.findViewById(R.id.disease_item);
            dis_name = itemView.findViewById(R.id.disease_name);
            major = itemView.findViewById(R.id.major);
            dis_desc = itemView.findViewById(R.id.disease_desc);
        }
    }
}
