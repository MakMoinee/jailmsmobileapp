package com.aclc.thesis.jmsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.navigation.ui.AppBarConfiguration;

import com.aclc.thesis.jmsapp.databinding.ActivityMainFormBinding;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;
import com.aclc.thesis.jmsapp.ui.home.HomeFragment;
import com.aclc.thesis.jmsapp.ui.qr.QRGeneratorFragment;

public class MainFormActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainFormBinding binding;
    private Fragment homeFragment = new HomeFragment();
    private Fragment qrFragment = new QRGeneratorFragment();
    private UserPreference userPreference;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TextView txtUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        setUser(headerView);
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(R.id.frame, homeFragment, null);
        ft.commit();
        navigationView.setCheckedItem(R.id.nav_home);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


//        DrawerLayout drawer = binding.drawerLayout;
//        NavigationView navigationView = binding.navView;
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_qr, R.id.nav_logout)
//                .setDrawerLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_form);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setUser(View headerView) {
        txtUsername = headerView.findViewById(R.id.Username);
        userPreference = new UserPreferenceImpl(MainFormActivity.this);
        Users users = userPreference.getUsers();
        txtUsername.setText(users.getUserName());
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                homeFragment = new HomeFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, homeFragment, null);
                ft.commit();
                navigationView.setCheckedItem(R.id.nav_home);
                drawer.closeDrawer(navigationView);
                return true;
            case R.id.nav_qr:
                qrFragment = new QRGeneratorFragment();
                fm = getSupportFragmentManager();
                ft = fm.beginTransaction();
                ft.replace(R.id.frame, qrFragment, null);
                ft.commit();
                navigationView.setCheckedItem(R.id.nav_qr);
                drawer.closeDrawer(navigationView);
                return true;
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
                return true;
        }
        return false;
    }
}