package com.example.bluetoothmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class DeviceOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_options);

        //receive intent from the main activity
        Intent intent = getIntent();
        String deviceName = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Set title to proper settings
        TextView title = findViewById(R.id.optionsTitle);
        title.setText(deviceName);
        title.setTextSize(30);
        title.setTypeface(title.getTypeface(), Typeface.BOLD);
        title.setAllCaps(true);


    }

}
