package com.example.carparking;

import androidx.appcompat.app.AppCompatActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class LoginScreen extends AppCompatActivity {

    ImageView data, wifi;
    TextView batteryStatusText;
    Toolbar batteryPercentagetoolbar;

    private BroadcastReceiver batterylevelReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            batteryStatusText.setText(String.valueOf(level) + "%");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        batteryPercentagetoolbar = findViewById(R.id.BatteryPercentageToolBarID);
        batteryStatusText = findViewById(R.id.batteryStatusOutputID);
        this.registerReceiver(this.batterylevelReciever, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        data = findViewById(R.id.dataStatusOutputID);
        wifi = findViewById(R.id.wifiStatusOutputID);
        checkNetworkStatus();
    }

    public void checkNetworkStatus(){
        boolean wifiConnected, dataConnected;
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        if(activeInfo!=null && activeInfo.isConnected()){
            wifiConnected = activeInfo.getType() == ConnectivityManager.TYPE_WIFI;
            dataConnected = activeInfo.getType() == ConnectivityManager.TYPE_MOBILE;
            if(wifiConnected){
                wifi.setImageResource(R.drawable.ic_wifi_black_24dp);
                data.setImageResource(R.drawable.ic_do_not_disturb_black_24dp);
            }
            else if(dataConnected){
                data.setImageResource(R.drawable.ic_mobile_data_black_24dp);
                wifi.setImageResource(R.drawable.ic_no_wifi_black_24dp);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent it = new Intent(getApplicationContext(), HomeScreen.class);
        startActivity(it);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
