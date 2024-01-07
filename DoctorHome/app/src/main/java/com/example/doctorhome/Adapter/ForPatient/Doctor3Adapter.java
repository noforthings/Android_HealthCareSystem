package com.example.doctorhome.Adapter.ForPatient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.example.doctorhome.uis.ForPatient.BookingDetailActivity;

import java.util.ArrayList;

public class Doctor3Adapter extends RecyclerView.Adapter<Doctor3Adapter.DentistViewHolder> {
    ArrayList<User> dentistLocations;
    Context mContext;


    public Doctor3Adapter(Context context, ArrayList<User> dentistLocations) {
        this.mContext = context;
        this.dentistLocations = dentistLocations;
    }

    @NonNull
    @Override
    public DentistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_patient_card_doctor_horizontal,parent,false);
        DentistViewHolder dentistViewHolder = new DentistViewHolder(view);
        return dentistViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DentistViewHolder holder, int position) {

        User user = dentistLocations.get(position);


        holder.name.setText(user.getFullname());
        holder.desc.setText(user.getDescription());
        holder.dentistItem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("doctorId", user.getId());
                editor.apply();
                Intent intent = new Intent(mContext , BookingDetailActivity.class);
                mContext.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return dentistLocations.size();
    }

    public static class DentistViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, desc;
        CardView dentistItem;

        public DentistViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            dentistItem = itemView.findViewById(R.id.dentist_item);
            image = itemView.findViewById(R.id.dentist_image);
            name = itemView.findViewById(R.id.dentist_name);
            desc = itemView.findViewById(R.id.dentist_desc);
        }
    }

}
