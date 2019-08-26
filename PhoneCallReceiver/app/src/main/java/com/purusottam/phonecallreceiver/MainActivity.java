package com.sanjay.phonecallreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RegisterCall call = new RegisterCall();

    }
}
