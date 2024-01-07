package com.example.doctorhome.Adapter.ForPatient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.example.doctorhome.uis.ForPatient.BookingDetailActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> {

    ArrayList<User> doctorLocations;

    Context mContext;

    public DoctorAdapter(Context context, ArrayList<User> doctorLocations) {
        this.mContext = context;
        this.doctorLocations = doctorLocations;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_patient_card_doctor_vertical,parent,false);
        DoctorViewHolder doctorViewHolder = new DoctorViewHolder(view);
        return doctorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, int position) {

        User users = doctorLocations.get(position);


        holder.name.setText(users.getFullname());
        holder.desc.setText(users.getDescription());
        holder.doctorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("doctorId", users.getId());
                editor.apply();
                Intent intent = new Intent(mContext, BookingDetailActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return doctorLocations.size();
    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        ShapeableImageView image;
        TextView name, desc;
        CardView doctorItem;

        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            doctorItem = itemView.findViewById(R.id.doctor_item);
            image = itemView.findViewById(R.id.doctorImage);
            name = itemView.findViewById(R.id.doctor_name);
            desc = itemView.findViewById(R.id.doctor_desc);
        }
    }
}
