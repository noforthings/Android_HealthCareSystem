package com.example.doctorhome.uis.ForDoctor;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.doctorhome.Models.DBHelper.DBHelper;
import com.example.doctorhome.Models.DBHelper.UserRepository;
import com.example.doctorhome.Models.User;
import com.example.doctorhome.R;
import com.example.doctorhome.databinding.ActivityDoctorHomeBinding;
import com.example.doctorhome.uis.MainActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

public class DoctorHomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDoctorHomeBinding binding;
    //Handle views in nav_header_main.xml
    private ImageView imgDoctorAvatar;
    private TextView txtDoctorName;
    private TextView txtDoctorDescription;
    private TextView txtDoctorPhone;
    private MenuItem itemLogout;
    private User doctor;
    private SharedPreferences sharedPreferences;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDoctorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You don't have any notification. Have a nice day!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_feedback, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //Get information for header view
        View headerView = binding.navView.getHeaderView(0);
        getHeaderWidget(headerView);

        //get item logout
        itemLogout = binding.navView.getMenu().findItem(R.id.action_settings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void getHeaderWidget(View v) {
        txtDoctorDescription = v.findViewById(R.id.txtDoctorDescription);
        txtDoctorName = v.findViewById(R.id.txtDoctorName);
        txtDoctorPhone = v.findViewById(R.id.txtDoctorPhone);
        imgDoctorAvatar = v.findViewById(R.id.imgDoctorAvatar);

        userRepository = new UserRepository(new DBHelper(this));
        //get user id
        sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);
        if (sharedPreferences.contains("username") && sharedPreferences.contains("password")) {
            String username = sharedPreferences.getString("username", null);
            int userId = sharedPreferences.getInt("userId", 1);
            doctor = userRepository.getUserByUsername(username);
            doctor.setImage(this.getFilesDir().getPath() + "/profile_image.png");
            setHeaderInfo(doctor);
        }
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            openPopupLogout();
            return true;
        }
        return super.onContextItemSelected(item);
    }
    private void setHeaderInfo(User doctor) {
        txtDoctorName.setText(doctor.getFullname());
        txtDoctorPhone.setText(doctor.getPhone());
        txtDoctorDescription.setText(doctor.getEmail());
        if (doctor.getImage() != null) {
            imgDoctorAvatar.setImageURI(Uri.parse(doctor.getImage()));
        }
    }
    private void openPopupLogout() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_doctor_popup_logout);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        SharedPreferences sharedPreferences = getSharedPreferences("app", MODE_PRIVATE);

        Button btnOk = dialog.findViewById(R.id.btnLogoutAgain);
        Button btnCancel = dialog.findViewById(R.id.btnCancelAgain);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyToRemove = "password";
                String id = "userId";
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(keyToRemove);
                editor.remove(id);
                editor.apply();
                Intent intent = new Intent(DoctorHomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}