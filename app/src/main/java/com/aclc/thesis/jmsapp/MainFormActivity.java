package com.aclc.thesis.jmsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.aclc.thesis.jmsapp.preference.VisitorPreference;
import com.aclc.thesis.jmsapp.preference.VisitorPreferenceImpl;
import com.aclc.thesis.jmsapp.ui.home.HomeFragment;
import com.aclc.thesis.jmsapp.ui.profile.ProfileFragment;
import com.aclc.thesis.jmsapp.ui.qr.QRGeneratorFragment;
import com.google.android.material.navigation.NavigationView;

public class MainFormActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private Fragment homeFragment;
    private Fragment qrFragment;
    private UserPreference userPreference;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView txtUsername, txtContactNum;
    private Fragment profileFragment;
    private VisitorPreference visitorPreference;
    private Toolbar toolbar;
    private ImageView waveDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main_form);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("JMS");
        setSupportActionBar(toolbar);
        waveDown = findViewById(R.id.waveDown);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        setUser(headerView);
        homeFragment = new HomeFragment(MainFormActivity.this);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame, homeFragment, null);
        ft.commit();
        navigationView.setCheckedItem(R.id.nav_home);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }

    private void setUser(View headerView) {
        txtUsername = headerView.findViewById(R.id.Username);
        txtContactNum = headerView.findViewById(R.id.txtContactNum);
        userPreference = new UserPreferenceImpl(MainFormActivity.this);
        visitorPreference = new VisitorPreferenceImpl(MainFormActivity.this);
        Users users = userPreference.getUsers();
        txtUsername.setText(users.getUserName());
        String contactNum = visitorPreference.getVisitor().getContactNumber() != null ? visitorPreference.getVisitor().getContactNumber() : "";
        txtContactNum.setText(contactNum);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                homeFragment = new HomeFragment(MainFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, homeFragment, null);
                ft.commit();
                navigationView.setCheckedItem(R.id.nav_home);
                toolbar.setTitle("JMS");
                waveDown.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_profile:
                profileFragment = new ProfileFragment(MainFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, profileFragment, null);
                ft.commit();
                navigationView.setCheckedItem(R.id.nav_profile);
                toolbar.setTitle("Profile");
                waveDown.setVisibility(View.INVISIBLE);
                break;
            case R.id.nav_qr:
                qrFragment = new QRGeneratorFragment(MainFormActivity.this);
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, qrFragment, null);
                ft.commit();
                navigationView.setCheckedItem(R.id.nav_qr);
                toolbar.setTitle("QR Code");
                waveDown.setVisibility(View.VISIBLE);
                break;
            case R.id.nav_logout:
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainFormActivity.this);
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case DialogInterface.BUTTON_POSITIVE:
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                Users users = new Users();
                                new UserPreferenceImpl(MainFormActivity.this).setUsers(users);
                                Intent intent = new Intent(MainFormActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                break;
                        }
                    }
                };
                mBuilder.setMessage("Are You Sure You Want To Logout?")
                        .setCancelable(true)
                        .setNegativeButton("Yes", listener)
                        .setPositiveButton("No", listener);
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