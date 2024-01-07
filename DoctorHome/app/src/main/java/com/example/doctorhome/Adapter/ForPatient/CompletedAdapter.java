package com.example.doctorhome.Adapter.ForPatient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.Appointment;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;

import java.util.ArrayList;

public class CompletedAdapter extends RecyclerView.Adapter<CompletedAdapter.MyViewHolder> {
    Context context;
    UserRepository userRepository;
    ArrayList<Appointment> arrayList;
    private UpComingAdapter.OnItemClickListener listener;
    private UpComingAdapter.OnButtonClickListener buttonClickListener;
    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }
    public void setOnButtonClickListener(UpComingAdapter.OnButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(UpComingAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
    public CompletedAdapter(Context context, ArrayList<Appointment> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        userRepository = new UserRepository(new DBHelper(this.context));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_patient_list_completed, parent, false);
        return new MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Appointment appointment = arrayList.get(position);
        User doctor = userRepository.getUserById(appointment.getDoctorId());
        holder.txtDrName.setText(doctor.getFullname());
        holder.txtDescription.setText(appointment.getDescription());
        holder.txtAddress.setText(appointment.getAddress());
        holder.txtTime.setText(appointment.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
            }
        });
        holder.btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtDrName;
        TextView txtDescription;
        TextView txtAddress;
        TextView txtTime;
        Button btnAgain;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDrName = itemView.findViewById(R.id.txtDrName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtAddress = itemView.findViewById(R.id.txtPlace);
            txtTime = itemView.findViewById(R.id.txtTime);
            btnAgain = itemView.findViewById(R.id.btnAgain);
        }
    }
}
