package com.example.carparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    FloatingActionButton btn;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.floatingActionButtonID);
        btn.setOnClickListener(this);

        drawerLayout = findViewById(R.id.drawerID);
        navigationView = findViewById(R.id.navigationViewID);

        toolbar = findViewById(R.id.toolBarID);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.logout_menu_bar);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawerOpen, R.string.drawerClose);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutID){
            Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId()==R.id.settingsID){
            Fragment fragment = new SettingsFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        // close navigation drawer using "closeDrawer()" method
        drawerLayout.closeDrawer(GravityCompat.START,true);

        Fragment fragment;

        if(menuItem.getItemId()==R.id.homeID){
            fragment = new HomeFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(menuItem.getItemId()==R.id.profileID){
            fragment = new ProfileFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(menuItem.getItemId()==R.id.findLotID){
            fragment = new FindLotFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        else if(menuItem.getItemId()==R.id.aboutID){
            fragment = new AboutFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        if(menuItem.getItemId()==R.id.feedbackID){
            fragment = new FeedbackFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        if(menuItem.getItemId()==R.id.floatingActionButtonID){
            fragment = new ReserveListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
        if(menuItem.getItemId()==R.id.paymentID){
            alertFunction();
        }

        return false;
    }

    public void alertFunction(){
        AlertDialog.Builder alertDialogBuilder;

        alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle(R.string.reservationDetails);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setNeutralButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder;

        alertDialogBuilder = new AlertDialog.Builder(this);

        alertDialogBuilder.setTitle("Do you want to turn off this window ?");
        alertDialogBuilder.setIcon(R.drawable.exit);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.floatingActionButtonID){
            Fragment fragment = new ReserveListFragment();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentID, fragment).commit();
        }
    }
}
