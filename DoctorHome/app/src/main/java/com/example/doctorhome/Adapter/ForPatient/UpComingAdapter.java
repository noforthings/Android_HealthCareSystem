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

public class UpComingAdapter extends RecyclerView.Adapter<UpComingAdapter.MyViewHolder> {
    Context context;
    UserRepository userRepository;
    ArrayList<Appointment> arrayList;
    private OnItemClickListener listener;
    private OnButtonClickListener buttonClickListener;

    // Interface for button click listener
    public interface OnButtonClickListener {
        void onButtonClick(int position);
    }
    public void setOnButtonClickListener(OnButtonClickListener buttonClickListener) {
        this.buttonClickListener = buttonClickListener;
    }

    // Interface for item click listener
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Method to set the click listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public UpComingAdapter(Context context, ArrayList<Appointment> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        userRepository = new UserRepository(new DBHelper(this.context));
    }
    public void setData(ArrayList<Appointment> newData) {
        this.arrayList = newData;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_patient_list_upcoming, parent, false);
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
        holder.btnCancel.setOnClickListener(new View.OnClickListener() {
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
        Button btnCancel;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDrName = itemView.findViewById(R.id.txtDrName);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtAddress = itemView.findViewById(R.id.txtPlace);
            txtTime = itemView.findViewById(R.id.txtTime);
            btnCancel = itemView.findViewById(R.id.btnCancel);
        }
    }
}
