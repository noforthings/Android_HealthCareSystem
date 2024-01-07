package com.example.doctorhome.Adapter.ForPatient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.example.doctorhome.uis.ForPatient.BookingDetailActivity;

import java.util.ArrayList;

public class Doctor2Adapter extends RecyclerView.Adapter<Doctor2Adapter.DoctorListViewHolder> {
    ArrayList<User> arrayList;
    Context mContext;


    public Doctor2Adapter(Context context, ArrayList<User> dentistLocations ) {
        this.mContext = context;
        this.arrayList = dentistLocations;
    }

    @NonNull
    @Override
    public DoctorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_patient_list_doctor,parent,false);
        return new DoctorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorListViewHolder holder, int position) {
        User user = arrayList.get(position);


        holder.name.setText(user.getFullname());
        holder.desc.setText(user.getDescription());
        holder.phone.setText(user.getPhone());
        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("app", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("doctorId", user.getId());
                editor.apply();
                Intent intent = new Intent(mContext, BookingDetailActivity.class);
                mContext.startActivity(intent);
            }


        });
    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class DoctorListViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name, desc, phone;
        ConstraintLayout layoutItem;

        public DoctorListViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            layoutItem = itemView.findViewById(R.id.layout_item);
            image = itemView.findViewById(R.id.imgInRecycler);
            name = itemView.findViewById(R.id.txtDrName);
            desc = itemView.findViewById(R.id.txtDescription);
            phone = itemView.findViewById(R.id.txtPhone);
        }
    }
}

