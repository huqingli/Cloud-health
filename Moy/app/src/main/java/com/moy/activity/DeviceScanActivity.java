package com.moy.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import com.moy.adapter.MyDeviceAdapter;
import com.moy.util.BluetoothUtils;

import java.util.ArrayList;
import java.util.List;

public class DeviceScanActivity extends Activity  {


    private BluetoothAdapter mAdapter;
    private MyDeviceAdapter myDeviceAdapter;
    private ListView bleListView;
    private ArrayList<BluetoothDevice> mLeDevices;
    private boolean mScanning;

    private int REQUEST_ACCESS_COARSE_LOCATION=1;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.act_blelist);
        mLeDevices = new ArrayList<>();
        if(mLeDevices != null) {
            mLeDevices.clear();
        }
        bleListView = (ListView) findViewById(R.id.ble_list);
        mAdapter = new BluetoothUtils().getmAdapter();


        if(Build.VERSION.SDK_INT>=23) {
            //判断是否有权限
            if (ContextCompat.checkSelfPermission(DeviceScanActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_ACCESS_COARSE_LOCATION);
            //向用户解释，为什么要申请该权限
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.READ_CONTACTS)) {
                    Toast.makeText(DeviceScanActivity.this, "shouldShowRequestPermissionRationale", Toast.LENGTH_SHORT).show();
                }
            }
        }
        scanLeDevice(true);

        myDeviceAdapter = new MyDeviceAdapter(mLeDevices, DeviceScanActivity.this);

        bleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final BluetoothDevice mBluetoothDevice = mLeDevices.get(i);
                if(mBluetoothDevice == null){
                    return;
                }
                final Intent intent = new Intent();
                intent.setClass(DeviceScanActivity.this, BloodPressureActivity.class);
                intent.putExtra(BloodPressureActivity.EXTRAS_DEVICE_NAME, mBluetoothDevice.getName());
                intent.putExtra(BloodPressureActivity.EXTRAS_DEVICE_ADDRESS, mBluetoothDevice.getAddress());
                Log.w(DeviceScanActivity.class.getSimpleName(), "扫描结果：" + mBluetoothDevice.getAddress());
         //       Log.w(DeviceScanActivity.class.getSimpleName(),"itent：" +intent.getStringExtra(BloodPressureActivity.EXTRAS_DEVICE_ADDRESS));
                mAdapter.stopLeScan(mLeScanCallback);
                startActivity(intent);
                DeviceScanActivity.this.finish();
            }
        });
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            DeviceScanActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(!mLeDevices.contains(bluetoothDevice)) {
                        mLeDevices.add(bluetoothDevice);
                        Log.e("tag", "mLeScanCallback 搜索结果   " + bluetoothDevice.getAddress());
                    }
                    if(myDeviceAdapter.isEmpty()) {
                        Log.e("tag", "mLeDeviceListAdapter为空");
                    } else {
                        bleListView.setAdapter(myDeviceAdapter);
                    }

                }
            });
        }
    };


    @SuppressLint("NewApi")
    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            // 预先定义停止蓝牙扫描的时间（因为蓝牙扫描需要消耗较多的电量）

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mAdapter.stopLeScan(mLeScanCallback);
                }
            }, 10000);
            mScanning = true;
            // 定义一个回调接口供扫描结束处理
            mAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mAdapter.stopLeScan(mLeScanCallback);
        }
    }

    public final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: // Notify change
                    myDeviceAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

}
