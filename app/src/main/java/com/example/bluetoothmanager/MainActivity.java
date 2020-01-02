package com.example.bluetoothmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements BTDeviceAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private BTDeviceAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public ArrayList<Device> dataset;

    private final static int REQUEST_ENABLE_BT = 1;
    private BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //recyclerView stuff
        recyclerView = findViewById(R.id.deviceList);
        //create and set layout manager
        layoutManager = new LinearLayoutManager(getApplication(),
                LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //specify adapter
        dataset = new ArrayList<>();
        mAdapter = new BTDeviceAdapter(this, dataset);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(this);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        //Get the current status of bluetooth
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ImageButton bluetoothButton = findViewById(R.id.imageButton);
        //if bluetooth is enabled, we color the sign accordingly
        if (bluetoothAdapter.isEnabled()) {
            bluetoothButton.setTag(R.drawable.bluetooth_blue);
            bluetoothButton.setImageResource(R.drawable.bluetooth_blue);
            this.startBluetoothSearch();
        } else {
            bluetoothButton.setTag(R.drawable.bluetooth_gray);
            bluetoothButton.setImageResource(R.drawable.bluetooth_gray);
        }
    }


    /** Function called when the 'activate bluetooth' button is tabbed */
    public void onActivateBluetooth( View view) throws Exception{
        if (bluetoothAdapter == null) {
            throw new Exception("com.example.randomapplicatoin MainActivity:37 did not find a " +
                    "bluetooth adapter in this device");
        }

        //retrieve the bluetooth button from the user interface
        ImageButton bluetoothButton = findViewById(R.id.imageButton);

        //Check if bluetooth is already enabled
        if (bluetoothAdapter.isEnabled()) {
            bluetoothButton.setImageResource(R.drawable.bluetooth_gray);
            bluetoothButton.setTag(R.drawable.bluetooth_gray);
            dataset.clear();
            mAdapter.notifyDataSetChanged();
            bluetoothAdapter.disable();
            //triggerPopUp("Notification", "You turned bluetooth off");
        } else {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            bluetoothButton.setImageResource(R.drawable.bluetooth_blue);
            bluetoothButton.setTag(R.drawable.bluetooth_blue);
            startBluetoothSearch();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent btOptionsPage = new Intent(this, DeviceOptionsActivity.class);
        btOptionsPage.putExtra("com.example.bluetoothmanager", position);
        this.startActivity(btOptionsPage);
    }

    /** Initiate the bluetooth search once bluetooth is on */
    private void startBluetoothSearch() {
        //Retrieve the paired devices first and display them in the Recyclerview
        while( !bluetoothAdapter.isEnabled()) {}
        Set<BluetoothDevice> knownDevices = bluetoothAdapter.getBondedDevices();
        if (knownDevices.size() > 0) {
            for ( BluetoothDevice knownDevice : knownDevices) {
                this.dataset.add(new Device(knownDevice.getName(), knownDevice.getAddress()));
            }
            mAdapter.notifyDataSetChanged();
        }

    }

    /** Initiates a pop up with VAR title and VAR text */
    private void triggerPopUp(String title, String text) {
        AlertDialog popupDialog = new AlertDialog.Builder(MainActivity.this).create();
        popupDialog.setTitle(title);
        popupDialog.setMessage(text);
        popupDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        popupDialog.show();
    }
}
