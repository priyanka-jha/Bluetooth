package com.example.user.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button on, off, visible, listdevices;
    ListView listView;
    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> bluetoothAdapterSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        on = (Button) findViewById(R.id.on);
        off = (Button) findViewById(R.id.off);
        visible = (Button) findViewById(R.id.visible);
        listdevices = (Button) findViewById(R.id.devices);

        listView = (ListView) findViewById(R.id.listvoew);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bluetoothAdapter.isEnabled()) {
                    Intent on = new Intent(bluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(on, 0);
                    Toast.makeText(MainActivity.this, "Bluetooth Turned on", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Bluetooth Already On", Toast.LENGTH_SHORT).show();
                }
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.disable();
                Toast.makeText(MainActivity.this, "Turned Off", Toast.LENGTH_SHORT).show();
            }
        });
        visible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent req = new Intent(bluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                startActivityForResult(req, 0);
            }
        });
    }

    public void listdevices(View view) {
        bluetoothAdapterSet = bluetoothAdapter.getBondedDevices();
        ArrayList list = new ArrayList();
        for (BluetoothDevice bt : bluetoothAdapterSet) list.add(bt.getName());
        Toast.makeText(MainActivity.this, "Showing Paired Devices", Toast.LENGTH_SHORT).show();
        final ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(arrayAdapter);
    }
}
