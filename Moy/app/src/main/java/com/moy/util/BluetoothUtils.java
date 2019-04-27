package com.moy.util;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hu on 2018/12/12.
 */

public class BluetoothUtils {
    private BluetoothAdapter mAdapter;

    public BluetoothUtils() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public BluetoothAdapter getmAdapter() {
        return mAdapter;
    }
    //打开蓝牙
    public void turnOnBluetooth(Activity activity, int requestCode) {
       Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
       activity.startActivityForResult(intent, requestCode);
    }

    //打开蓝牙可见性
    public void enableVisibily(Context context) {
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        context.startActivity(intent);
    }

    //查找设备
    public void findDevice() {
        assert (mAdapter != null);
        mAdapter.startDiscovery();
    }


    //获取已绑定设备
     public List<BluetoothDevice> getBondedDeviceList(){
        return new ArrayList<>(mAdapter.getBondedDevices());
    }
//    public void searchBluetooth() {
//        //判断蓝牙是否打开
//        if(!mAdapter.isEnabled()){
//            mAdapter.enable();
//        }
////        Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
////        enable.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 3600);
////        startActivity(enable);
//    }
//

}
