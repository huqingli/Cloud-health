package com.moy.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.moy.activity.R;
import java.util.ArrayList;

/**
 * Created by hu on 2018/12/15.
 */

public class MyDeviceAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<BluetoothDevice> mLeDevices;
    public MyDeviceAdapter(ArrayList mLeDevices, Context context) {
        this.mLeDevices = mLeDevices;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int i) {
        return mLeDevices.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BluetoothDevice localBluetoothDevice;
        ViewHolder viewHolder = new ViewHolder();
        if(view == null) {
            view = layoutInflater.inflate(R.layout.activity_device_scan, null);
            viewHolder.tv_deviceName =(TextView) view.findViewById(R.id.device_name);
            viewHolder.tv_deviceAddress = (TextView) view.findViewById(R.id.device_address);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        localBluetoothDevice = mLeDevices.get(i);
        final String str = localBluetoothDevice.getName();
        if(str != null && str.length() >0) {
            viewHolder.tv_deviceName.setText(str);
        }else {
            viewHolder.tv_deviceName.setText("未知设备");
        }
        viewHolder.tv_deviceAddress.setText(localBluetoothDevice.getAddress());
        return view;
    }

    class ViewHolder {
        TextView tv_deviceName;
        TextView tv_deviceAddress;
    }
}
