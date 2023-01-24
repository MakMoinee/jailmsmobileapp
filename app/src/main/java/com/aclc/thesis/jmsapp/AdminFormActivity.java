package com.aclc.thesis.jmsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.ui.home.HomeFragment;
import com.aclc.thesis.jmsapp.ui.inmates.InmatesFragment;
import com.aclc.thesis.jmsapp.ui.profile.ProfileFragment;
import com.aclc.thesis.jmsapp.ui.scanqr.ScanQRFragment;
import com.aclc.thesis.jmsapp.ui.visithistory.VisitorHistoryFragment;
import com.google.android.material.navigation.NavigationView;

public class AdminFormActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Fragment homeFragment;
    private FragmentTransaction ft;
    private FragmentManager fm;
    private Toolbar toolbar;
    private UserPreference userPreference;
    private Fragment scanQRFragment;
    private Fragment profileFragment;
    private Fragment visitorHistoryFragment;
    private Fragment inmatesFragment;
    private TextView txtLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_admin_form);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Police JMS");
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        homeFragment = new HomeFragment(AdminFormActivity.this);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame, homeFragment, null);
        ft.commit();
        View headerView = navigationView.getHeaderView(0);
        txtLabel = headerView.findViewById(R.id.txtLabel);
        setAdmin();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setAdmin() {
        userPreference = new UserPreferenceImpl(AdminFormActivity.this);
        Users users = userPreference.getUsers();
        txtLabel.setText(users.getUserName());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                homeFragment = new HomeFragment(AdminFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, homeFragment, null);
                toolbar.setTitle("Police JMS");
                ft.commit();
                break;
            case R.id.nav_visit:
                visitorHistoryFragment = new VisitorHistoryFragment(AdminFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, visitorHistoryFragment, null);
                toolbar.setTitle("Visit History");
                ft.commit();
                break;
//            case R.id.nav_reports:
//                break;
//            case R.id.nav_inmates:
//                inmatesFragment = new InmatesFragment(AdminFormActivity.this);
//                fm = getSupportFragmentManager();
//                ft = fm.beginTransaction();
//                ft.replace(R.id.frame, inmatesFragment, null);
//                toolbar.setTitle("Inmates");
//                ft.commit();
//                break;
            case R.id.nav_profile:
                profileFragment = new ProfileFragment(AdminFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, profileFragment, null);
                toolbar.setTitle("Profile");
                ft.commit();
                break;
            case R.id.nav_qr:
                scanQRFragment = new ScanQRFragment(AdminFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, scanQRFragment, null);
                toolbar.setTitle("Scan QR");
                ft.commit();
                break;
            case R.id.nav_logout:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminFormActivity.this);
                DialogInterface.OnClickListener dListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_NEGATIVE:
                                userPreference = new UserPreferenceImpl(AdminFormActivity.this);
                                Users users = new Users();
                                userPreference.setUsers(users);
                                Intent intent = new Intent(AdminFormActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                };

                mBuilder.setMessage("Are you sure you want to log out?")
                        .setNegativeButton("Yes", dListener)
                        .setPositiveButton("No", dListener)
                        .setCancelable(true);
                mBuilder.show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}