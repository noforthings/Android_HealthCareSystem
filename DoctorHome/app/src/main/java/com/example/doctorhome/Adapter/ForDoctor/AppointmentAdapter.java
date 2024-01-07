package com.example.doctorhome.Adapter.ForDoctor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.Appointment;
import com.example.doctorhome.R;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private List<Appointment> appointments;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(Appointment appointment);
    }

    public AppointmentAdapter(List<Appointment> appointments, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        this.appointments = appointments;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor_appointment, parent, false);
        return new AppointmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        //binding data to ViewHolder
        holder.bind(appointment);
    }

    public class AppointmentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        private final TextView txtDescription;
        private final TextView txtDateTime;
        private final TextView txtDoctor;
        private final ImageView imgView;
        public AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.textViewDescription);
            txtDateTime = itemView.findViewById(R.id.textViewDateTime);
            txtDoctor = itemView.findViewById(R.id.textViewDoctor);
            imgView = itemView.findViewById(R.id.imageViewAppointment);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(appointments.get(position));
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (longClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    longClickListener.onItemLongClick(appointments.get(position));
                    return true;
                }
            }
            return false;
        }
        public void bind(Appointment app) {
            txtDescription.setText("Description: " + app.getDescription());
            txtDateTime.setText("Date: " + app.getTime());
            txtDoctor.setText(app.getAddress());
            imgView.setImageResource(R.drawable.dp);
        }
    }
    public int getItemCount() {
        return appointments.size();
    }
}
