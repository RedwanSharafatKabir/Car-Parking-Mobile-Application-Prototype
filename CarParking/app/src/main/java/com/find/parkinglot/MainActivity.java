package com.find.parkinglot;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener{

    ImageView data, wifi;
    TextView batteryStatusText;
    FloatingActionButton btn;
    DrawerLayout drawerLayout;
    Toolbar toolbar, batteryPercentagetoolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;

//    private BroadcastReceiver batterylevelReciever = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
//            batteryStatusText.setText(String.valueOf(level) + "%");
//        }
//    };

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

//        toolbar.inflateMenu(R.menu.settings_menu_bar);
//        batteryPercentagetoolbar = findViewById(R.id.BatteryPercentageToolBarID);
//        batteryStatusText = findViewById(R.id.batteryStatusOutputID);
//        this.registerReceiver(this.batterylevelReciever, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
//        data = findViewById(R.id.dataStatusOutputID);
//        wifi = findViewById(R.id.wifiStatusOutputID);
//        checkNetworkStatus();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.drawerOpen, R.string.drawerClose);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

//    public void checkNetworkStatus(){
//        boolean wifiConnected, dataConnected;
//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
//        if(activeInfo!=null && activeInfo.isConnected()){
//            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
//            dataConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
//            if(wifiConnected){
//                wifi.setImageResource(R.drawable.ic_wifi_black_24dp);
//                data.setImageResource(R.drawable.ic_do_not_disturb_black_24dp);
//            }
//            else if(dataConnected){
//                data.setImageResource(R.drawable.ic_mobile_data_black_24dp);
//                wifi.setImageResource(R.drawable.ic_no_wifi_black_24dp);
//            }
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.logoutID){
            finish();
            Intent it = new Intent(MainActivity.this, LoginScreen.class);
            startActivity(it);
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

        alertDialogBuilder.setTitle("Are you sure to leave ?\n you will be logged out");
        alertDialogBuilder.setIcon(R.drawable.exit);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Intent it = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(it);
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
