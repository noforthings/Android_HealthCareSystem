package com.example.doctorhome.uis.ForPatient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Adapter.ForPatient.SpecialityAdapter;
import com.example.doctorhome.Models.Appointment;
import com.example.doctorhome.Models.DBHelper.AppointmentRepository;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.DiseaseRepository;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.DBHelper.WorkingSlotRepository;
import com.example.doctorhome.Models.Disease;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.Models.WorkingSlot;
import com.example.doctorhome.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingDetailActivity extends AppCompatActivity {
    RecyclerView speRecycler;
    int doctorId;
    int userId;
    int selectedSlot;
    DBHelper dbHelper;
    String selectedTime;
    User currentUser;
    User currentDoctor;
    TextView txtDoctorName;
    TextView txtDoctorEmail;
    TextView txtDoctorPhone;
    Button book;

    String username;
    UserRepository userRepository;
    List<WorkingSlot> listSlot;
    Spinner timeSpinner;
    List<String> dateList;
    List<SlotItem> slotItemList;
    WorkingSlotRepository workingSlotRepository;
    SharedPreferences sharedPreferences;
    AppointmentRepository appointmentRepository;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_booking);
        getWidget();
        addEvent();
        diseaseRecycler();
    }
    private void getWidget() {
        book = findViewById(R.id.btnBookApointment);
        back = findViewById(R.id.btnBack2);
        txtDoctorName = findViewById(R.id.txtDrNameInRecycler);
        txtDoctorEmail = findViewById(R.id.txtDoctorEmail);
        txtDoctorPhone = findViewById(R.id.txtDoctorPhone);
        speRecycler = findViewById(R.id.rSpe);

        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        userRepository = new UserRepository(new DBHelper(this));
        workingSlotRepository = new WorkingSlotRepository(new DBHelper(this));
        appointmentRepository = new AppointmentRepository(new DBHelper(this));

        doctorId = sharedPreferences.getInt("doctorId", 14);
        userId = sharedPreferences.getInt("currentUserId", 2);
        currentDoctor = userRepository.getUserById(doctorId);
        currentUser = userRepository.getUserById(userId);
        txtDoctorName.setText(currentDoctor.getFullname());
        txtDoctorName.setText(currentDoctor.getFullname());
        txtDoctorEmail.setText("Email: " + currentDoctor.getEmail());
        txtDoctorPhone.setText("Phone: " + currentDoctor.getPhone());

//        }
        slotItemList = new ArrayList<>();
        timeSpinner = findViewById(R.id.timeSpinner);
        dateList= getNext7Days();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dateList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter);
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedTime = dateList.get(position);
                SlotItem selectedSlotItem = slotItemList.get(position);
                selectedSlot = selectedSlotItem.getSlotId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

    }
    private void diseaseRecycler() {

        speRecycler.setHasFixedSize(true);
        speRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Disease> diseases = new ArrayList<>();
        int major_id;
        DiseaseRepository diseaseRepository = new DiseaseRepository(new DBHelper(this));
        diseases = diseaseRepository.getAllDiseaseByName();
        for (Disease disease: diseases
        ) {
            if(disease.getMajorId() == currentDoctor.getMajorId()) {
                list.add(disease.getDiseaseName());
            }
        }
        SpecialityAdapter adapter = new SpecialityAdapter(this,list);
        speRecycler.setAdapter(adapter);

    }
    private void addEvent() {
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Appointment appointment = new Appointment();
                appointment.setDoctorId(doctorId);
                appointment.setPatientId(userId);
                appointment.setTime(selectedTime);
                appointment.setStatus(0);
                appointment.setWorkingSlotId(selectedSlot);
                appointment.setDescription("You have an appointment with doctor.$Dan Phuong Hospital" );
                appointmentRepository.addAppointment(appointment);
                workingSlotRepository.updateTimeSlotStatus(selectedSlot, 1);
                Intent intent = new Intent(BookingDetailActivity.this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private List<String> getNext7Days() {
        List<String> dateList = new ArrayList<>();
        listSlot = workingSlotRepository.getAllSlotOfDoctorId(doctorId);
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String previousDay;
        String today = daysOfWeek[dayOfWeek - 1];
        if (dayOfWeek == 1) {
            previousDay = "Saturday";
        } else {
            previousDay = daysOfWeek[dayOfWeek - 2];
        }

        slotItemList.clear();
        for (WorkingSlot slot : listSlot) {
            if (slot.getIsBooked() == 0 && !Objects.equals(slot.getDay(), previousDay) && !Objects.equals(slot.getDay(), today)) {
                String date = slot.getDay();
                date += " " + slot.getTimeslot();
                dateList.add(date);
                SlotItem slotItem = new SlotItem(slot.getId(), date);
                slotItemList.add(slotItem);
            }
        }
        if(dateList.size() == 0) {
            book.setVisibility(View.GONE);
        }
        return dateList;

    }
    public class SlotItem {
        private int slotId;
        private String displayText;

        public SlotItem(int slotId, String displayText) {
            this.slotId = slotId;
            this.displayText = displayText;
        }

        public int getSlotId() {
            return slotId;
        }

        public String getDisplayText() {
            return displayText;
        }

        @NonNull
        @Override
        public String toString() {
            return displayText;
        }
    }
}