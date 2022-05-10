package com.aclc.thesis.jmsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.ui.home.HomeFragment;
import com.aclc.thesis.jmsapp.ui.profile.ProfileFragment;
import com.aclc.thesis.jmsapp.ui.scanqr.ScanQRFragment;

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
                ft.commit();
                break;
            case R.id.nav_profile:
                profileFragment = new ProfileFragment(AdminFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, profileFragment, null);
                ft.commit();
                break;
            case R.id.nav_qr:
                scanQRFragment = new ScanQRFragment(AdminFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, scanQRFragment, null);
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