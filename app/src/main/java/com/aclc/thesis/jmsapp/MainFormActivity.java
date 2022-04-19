package com.aclc.thesis.jmsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.aclc.thesis.jmsapp.databinding.ActivityMainFormBinding;
import com.aclc.thesis.jmsapp.models.Users;
import com.aclc.thesis.jmsapp.preference.UserPreference;
import com.aclc.thesis.jmsapp.preference.UserPreferenceImpl;

public class MainFormActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainFormBinding binding;
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_main_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainFormActivity.this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        drawer.setDrawerListener(toggle);

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

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_form);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}