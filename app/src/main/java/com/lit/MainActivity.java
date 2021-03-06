package com.lit;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private static final int REQUEST_ENABLE_BT = 1;
    private MyRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recycler view stuff
        recyclerView = findViewById(R.id.deviceRecyclerView);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // adds divider between devices
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }


    /* Ensures that Bluetooth can be enabled and asks user to turn on if not enabled */
    public void checkBluetoothSupport(View view) {
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), R.string.no_bluetooth_device, Toast.LENGTH_LONG).show();
        }

        if (!bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            getPairedDevices();
        }

    }

    public void getPairedDevices() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        if (pairedDevices.size() > 0) {
            final ArrayList<DeviceItem> deviceNames = new ArrayList<>();


            for (BluetoothDevice device : pairedDevices) {
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();

                DeviceItem item = new DeviceItem(deviceName, deviceHardwareAddress);
                deviceNames.add(item);

                Log.d("test", deviceName + deviceHardwareAddress);
            }



            adapter = new MyRecyclerViewAdapter(this, deviceNames);
            //adapter.setClickListener(this);
            adapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String name = deviceNames.get(position).getName();
                    Toast.makeText(MainActivity.this, name, Toast.LENGTH_LONG).show();
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }


    @Override
    /* Handles result of enabling Bluetooth */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), R.string.bluetooth_enabled, Toast.LENGTH_LONG).show();
                getPairedDevices();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), R.string.bluetooth_not_enabled, Toast.LENGTH_LONG).show();
            }
        }
    }
}
