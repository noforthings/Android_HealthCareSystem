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
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorVer3Adapter extends RecyclerView.Adapter<DoctorVer3Adapter.DoctorViewHolder> {
    private ArrayList<User> doctors;
    private final Context context;
    public DoctorVer3Adapter(Context context, ArrayList<User> doctors) {
        this.context = context;
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_card_doctor, parent, false);
        DoctorViewHolder doctorViewHolder = new DoctorViewHolder(view);
        return doctorViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewHolder holder, @SuppressLint("RecyclerView") int position) {
        User doctorUser = doctors.get(position);
        holder.name.setText(doctorUser.getFullname());
        holder.email.setText(doctorUser.getEmail());
        holder.phone.setText(doctorUser.getPhone());
        holder.major.setText(String.valueOf(doctorUser.getMajorId()));
        holder.desc.setText(doctorUser.getDescription());
        holder.doctorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateDialog(doctorUser,position);
            }
        });
    }

    private void openUpdateDialog(User doctorUser,int position) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_admin_popup_update_doctor);
        EditText txtName = dialog.findViewById(R.id.name_update);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.majors, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        EditText txtEmail = dialog.findViewById(R.id.email_update);
        EditText txtPhone = dialog.findViewById(R.id.phone_update);
        EditText txtDescription = dialog.findViewById(R.id.description2_update);
        Button btn_update = dialog.findViewById(R.id.update_confirm_btn);
        btn_update.setText("Edit");
        txtName.setText(doctors.get(position).getFullname());
        txtEmail.setText(doctors.get(position).getEmail());
        txtPhone.setText(doctors.get(position).getPhone());
        txtDescription.setText(doctors.get(position).getDescription());
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String docName, docDesc, email, phone;
                docName = txtName.getText().toString();
                email = txtEmail.getText().toString();
                phone = txtPhone.getText().toString();
                docDesc = txtDescription.getText().toString();
                doctorUser.setFullname(docName);
                doctorUser.setDescription(docDesc);
                doctorUser.setEmail(email);
                doctorUser.setPhone(phone);
                UserRepository userRepository = new UserRepository(new DBHelper(context));
                userRepository.updateUser(doctorUser);
                doctors.set(position,doctorUser);
                notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (doctors != null)
            return doctors.size();
        return 0;

    }

    public static class DoctorViewHolder extends RecyclerView.ViewHolder {

        CircleImageView image;
        TextView name,email,phone,major,desc;
        RelativeLayout doctorItem;
        public DoctorViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            doctorItem = itemView.findViewById(R.id.doctor_item);
            image = itemView.findViewById(R.id.doc_img);
            name = itemView.findViewById(R.id.doc_name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            major = itemView.findViewById(R.id.major);
            desc = itemView.findViewById(R.id.description);
        }
    }
}
