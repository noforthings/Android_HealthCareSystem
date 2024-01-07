package com.example.doctorhome.Adapter.ForPatient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.R;

import java.util.ArrayList;

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.SpecialityViewHolder> {
    ArrayList<String> diseases;
    Context context;

    public SpecialityAdapter(Context context, ArrayList<String> diseases) {
        this.context = context;
        this.diseases = diseases;
    }
    @NonNull
    @Override
    public SpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease_name,parent,false);
        return new SpecialityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityViewHolder holder, int position) {
        String disease = diseases.get(position);


        holder.name.setText(disease);
        holder.diseaseTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBooking();
            }

            private void goToBooking() {
                Toast.makeText(context.getApplicationContext(), holder.name.getText().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }
    public static class SpecialityViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ConstraintLayout diseaseTag;
        public SpecialityViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txtDisease);
            diseaseTag = itemView.findViewById(R.id.diseaseTag);
        }
    }
}
