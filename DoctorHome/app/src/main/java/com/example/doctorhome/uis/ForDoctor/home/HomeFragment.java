package com.example.doctorhome.uis.ForDoctor.home;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctorhome.Models.Appointment;
import com.example.doctorhome.Models.DBHelper.AppointmentRepository;
import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.R;
import com.example.doctorhome.Adapter.ForDoctor.AppointmentAdapter;
import com.example.doctorhome.databinding.FragmentDoctorHomeBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {
    private FragmentDoctorHomeBinding binding;
    private RecyclerView recyclerView;
    private TextView txtAlter;
    private AppointmentAdapter appointmentAdapter;
    private TableLayout tableAppointment;
    private Button btnSwitch;
    private AppointmentRepository appointmentRepository;
    SharedPreferences sharedPreferences;
    UserRepository userRepository;
    private ArrayList<Appointment> apps;

    //For table layout
    private TextView txtWeekRange;
    private TableRow trMonday, trTuesday, trWednesday, trThursday, trFriday, trSaturday, trSunday;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentDoctorHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        getWidget(root);
        homeViewModel.setAppointments(apps);

        homeViewModel.getAppointment().observe(getViewLifecycleOwner(), appointments -> {
            if (appointments.isEmpty()) {
                txtAlter.setVisibility(View.VISIBLE);
            } else {
                appointmentAdapter = new AppointmentAdapter(appointments, new AppItemEvent(), new AppItemEvent());
                recyclerView.setAdapter(appointmentAdapter);
            }
        });

        getEvent();

        return root;
    }
    private void getWidget(View root) {
        recyclerView = root.findViewById(R.id.recyclerAppointments);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        btnSwitch = root.findViewById(R.id.btnSwitch);
        tableAppointment = root.findViewById(R.id.tableAppointment);
        txtAlter = root.findViewById(R.id.txtAlterTextView);

        appointmentRepository = new AppointmentRepository(new DBHelper(getContext()));
        userRepository = new UserRepository(new DBHelper(getContext()));
        //get user id
        sharedPreferences = getActivity().getSharedPreferences("app", getContext().MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("password")) {
            String username = sharedPreferences.getString("username", null);
            int userId = sharedPreferences.getInt("userId", 7);
            //get list appointment of doctor
            apps = appointmentRepository.getAppointmentsByStatusForDoctor(userId, 0);
            Toast.makeText(requireContext(), "You have "+apps.size() + " waiting appointments!", Toast.LENGTH_LONG).show();
        }

        //get widget for table layout
        txtWeekRange = root.findViewById(R.id.textViewWeekRange);
        trMonday = root.findViewById(R.id.tableRowMonday);
        trTuesday = root.findViewById(R.id.tableRowTuesday);
        trWednesday = root.findViewById(R.id.tableRowWednesday);
        trThursday = root.findViewById(R.id.tableRowThursday);
        trFriday = root.findViewById(R.id.tableRowFriday);
        trSaturday = root.findViewById(R.id.tableRowSaturday);
        trSunday= root.findViewById(R.id.tableRowSunday);
        setTableAppointment();
    }

    private void getEvent() {
        btnSwitch.setOnClickListener(new ButtonEvent());
    }

    private void setTableAppointment() {
        setDateInWeek();

        //get widget for morning and afternoon of each day
        TextView t11 = (TextView) trMonday.getChildAt(1);
        TextView t12 = (TextView) trMonday.getChildAt(2);
        TextView t21 = (TextView) trTuesday.getChildAt(1);
        TextView t22 = (TextView) trTuesday.getChildAt(2);
        TextView t31 = (TextView) trWednesday.getChildAt(1);
        TextView t32 = (TextView) trWednesday.getChildAt(2);
        TextView t41 = (TextView) trThursday.getChildAt(1);
        TextView t42 = (TextView) trThursday.getChildAt(2);
        TextView t51 = (TextView) trFriday.getChildAt(1);
        TextView t52 = (TextView) trFriday.getChildAt(2);
        TextView t61 = (TextView) trSaturday.getChildAt(1);
        TextView t62 = (TextView) trSaturday.getChildAt(2);
        TextView t71 = (TextView) trSunday.getChildAt(1);
        TextView t72 = (TextView) trSunday.getChildAt(2);

        try {
        for (Appointment app : apps) {
            String[] appTime = app.getTime().split(" ");
            if (appTime[1].equals("Monday")) {
                if (appTime[2].equals("Morning")) {
                    //set morning monday
                    t11.setText(increaseAppointment(t11.getText().toString()));
                    t11.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                } else if (appTime[2].equals("Afternoon")) {
                    //set afternoon
                    t12.setText(increaseAppointment(t11.getText().toString()));
                    t12.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                }
            } else if (appTime[1].equals("Tuesday")) {
                if (appTime[2].equals("Morning")) {
                    t21.setText(increaseAppointment(t11.getText().toString()));
                    t21.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                } else if (appTime[2].equals("Afternoon")) {
                    t22.setText(increaseAppointment(t11.getText().toString()));
                    t22.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                }
            } else if (appTime[1].equals("Wednesday")) {
                if (appTime[2].equals("Morning")) {
                    t31.setText(increaseAppointment(t11.getText().toString()));
                    t31.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                } else if (appTime[2].equals("Afternoon")) {
                    t32.setText(increaseAppointment(t11.getText().toString()));
                    t32.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                }
            } else if (appTime[1].equals("Thursday")) {
                if (appTime[2].equals("Morning")) {
                    t41.setText(increaseAppointment(t11.getText().toString()));
                    t41.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                } else if (appTime[2].equals("Afternoon")) {
                    t42.setText(increaseAppointment(t11.getText().toString()));
                    t42.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                }
            } else if (appTime[1].equals("Friday")) {
                if (appTime[2].equals("Morning")) {
                    t51.setText(increaseAppointment(t11.getText().toString()));
                    t51.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                } else if (appTime[2].equals("Afternoon")) {
                    t52.setText(increaseAppointment(t11.getText().toString()));
                    t52.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                }
            } else if (appTime[1].equals("Saturday")) {
                if (appTime[2].equals("Morning")) {
                    t61.setText(increaseAppointment(t11.getText().toString()));
                    t61.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                } else if (appTime[2].equals("Afternoon")) {
                    t62.setText(increaseAppointment(t11.getText().toString()));
                    t62.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                }
            } else if (appTime[1].equals("Sunday")) {
                if (appTime[2].equals("Morning")) {
                    t71.setText(increaseAppointment(t11.getText().toString()));
                    t71.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                } else if (appTime[2].equals("Afternoon")) {
                    t72.setText(increaseAppointment(t11.getText().toString()));
                    t72.setBackgroundColor(android.graphics.Color.parseColor("#7da2f5"));
                }
            }
            }
        } catch(IndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error, please report to admin.", Toast.LENGTH_LONG).show();
        }
    }
    private String increaseAppointment(String string) {
        StringBuilder text = new StringBuilder();
        try {
            if (string.equals("On duty")) {
                text.append("Have ");
                int i = 1;
                text.append(i);
                text.append(" apps");
            } else {
                String[] split = string.split(" ");
                text.append(split[0] + " ");
                int i = Integer.parseInt(split[1]);
                i++;
                text.append(i);
                text.append(" apps");
            }
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Error, please report to admin.", Toast.LENGTH_LONG).show();
        }

        return text.toString();
    }
    private void setDateInWeek() {
        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();

        // Calculate the start date of the week
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date startDate = calendar.getTime();

        // Calculate the end date of the week
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        Date endDate = calendar.getTime();

        // Format the dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedStartDate = dateFormat.format(startDate);
        String formattedEndDate = dateFormat.format(endDate);
        // Display the week range in your TextView
        txtWeekRange.setText(formattedStartDate + " - " + formattedEndDate);

        String[] dateW = new String[7];
        for (int i=1; i<=7; i++) {
            calendar.set(Calendar.DAY_OF_WEEK, i);
            Date date = calendar.getTime();
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM", Locale.getDefault());

            dateW[i-1] = dateFormat1.format(date);
        }
        TextView t1 = (TextView) trMonday.getChildAt(0);
        t1.setText("Monday " + dateW[1]);
        TextView t2 = (TextView) trTuesday.getChildAt(0);
        t2.setText("Tuesday " + dateW[2]);
        TextView t3 = (TextView) trWednesday.getChildAt(0);
        t3.setText("Wednesday " + dateW[3]);
        TextView t4 = (TextView) trThursday.getChildAt(0);
        t4.setText("Thursday " + dateW[4]);
        TextView t5 = (TextView) trFriday.getChildAt(0);
        t5.setText("Friday " + dateW[5]);
        TextView t6 = (TextView) trSaturday.getChildAt(0);
        t6.setText("Saturday " + dateW[6]);
        TextView t0 = (TextView) trSunday.getChildAt(0);
        t0.setText("Sunday " + dateW[0]);

    }

    private class ButtonEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // Toggle layout
            if (txtAlter.getVisibility() == v.GONE) {
                if (recyclerView.getVisibility() == v.VISIBLE) {
                    recyclerView.setVisibility(v.GONE);
                    tableAppointment.setVisibility(v.VISIBLE);
                } else {
                    recyclerView.setVisibility(v.VISIBLE);
                    tableAppointment.setVisibility(v.GONE);
                }
            }
        }
    }

    private class AppItemEvent implements AppointmentAdapter.OnItemClickListener, AppointmentAdapter.OnItemLongClickListener {

        @Override
        public void onItemClick(Appointment appointment) {
            //Toast.makeText(requireContext(), "click", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onItemLongClick(Appointment appointment) {
            //Toast.makeText(requireContext(), "Long click", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}