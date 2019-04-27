package com.moy.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.moy.activity.BloodPressureActivity;
import com.moy.util.BluetoothUtils;
import com.moy.util.SampleGattAttributes;
import java.util.List;
import java.util.UUID;

public class BluetoothLeService extends Service {

    private static final String TAG = BluetoothLeService.class.getSimpleName();
    private BluetoothAdapter mAdapter;
    private String mBluetoothDeviceAddress;
    private BloodPressureActivity bloodPressureActivity = new BloodPressureActivity();
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic writeBluetoothGattCharacteristic;
    private BluetoothGattCharacteristic readBluetoothGattCharacteristic;
    private int mConnectionState = 0;
    private static final int STATE_CONNECTED = 2;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_DISCONNECTED = 0;
    private List<BluetoothGattService> serviceList;
    private List<BluetoothGattCharacteristic> characteristicsList;
    private BluetoothGattCharacteristic gattWriteCharacteristic;
    private BluetoothGattCharacteristic gattReadCharacteristic;
    private BluetoothGattService bluetoothGattService;
    private final IBinder mBinder = new MyBindler();
    final Intent mIntent = new Intent();
    public static final UUID UUID_READ = UUID.fromString("0000aaf2-0000-1000-8000-00805f9b34fb");
    public static final UUID UUID_WRITE= UUID.fromString("0000aaf1-0000-1000-8000-00805f9b34fb");

    public final static String ACTION_GATT_CONNECTED = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
//    public final static String EXTRA_DATA = "com.example.bluetooth.le.EXTRA_DATA";

    public final static UUID UUID_HEART_RATE_MEASUREMENT = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    public final static UUID CONNECTTION_UUID = UUID.fromString("0000aaf0-0000-1000-8000-00805f9b34fb");

    private final BluetoothGattCallback bluetoothGattCallback = new BluetoothGattCallback() {
        @SuppressLint("NewApi")
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {

            if (newState == BluetoothProfile.STATE_CONNECTED) { //连接成功
                System.out.println("蓝牙广播+++++++++++++++");
                broadcastUpdate(ACTION_GATT_CONNECTED);

                mBluetoothGatt.discoverServices(); //连接成功后就去找出该设备中的服务 private BluetoothGatt mBluetoothGatt;
            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {  //连接失败
                Log.i(TAG, "Disconnected from GATT server.");
           //     broadcastUpdate(ACTION_GATT_DISCONNECTED);
            }
        }

        @SuppressLint("NewApi")
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                    serviceList = mBluetoothGatt.getServices();
                    for (BluetoothGattService list:serviceList) {
                        Log.w(TAG, "服务" + list.getUuid() +"名字：" + list.toString());
                        characteristicsList = list.getCharacteristics();
                        for(BluetoothGattCharacteristic chat: characteristicsList) {
                            int charaProp = chat.getProperties();
                            //所有Characteristics按属性分类
                            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                                Log.d(TAG, "gattCharacteristic的UUID为:" + chat.getUuid());
                                Log.d(TAG, "gattCharacteristic的属性为:  可读");
//                                readUuid.add(chat.getUuid());
                            }
                            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_WRITE) > 0) {
                                Log.d(TAG, "gattCharacteristic的UUID为:" + chat.getUuid());
                                Log.d(TAG, "gattCharacteristic的属性为:  可写");
                          //      writeUuid.add(chat.getUuid());
                            }
                            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                                Log.d(TAG, "gattCharacteristic的UUID为:" + chat.getUuid());
                                Log.d(TAG, "gattCharacteristic的属性为:  通知");
                                //      writeUuid.add(chat.getUuid());
                            }
                            Log.w(TAG, "特征" + chat.getUuid());
                        }
                     //   displayGattServices(serviceList);
                    }
//              broadcastUpdate("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");

            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @SuppressLint("NewApi")
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {

            if (status == BluetoothGatt.GATT_SUCCESS) {
                   mBluetoothGatt.readCharacteristic(characteristic);
               broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", characteristic);
                }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            System.out.println("--------write success----- status:" + status);

           // readMsg();
        }

        @SuppressLint("NewApi")
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
       //  broadcastUpdate("com.example.bluetooth.le.ACTION_DATA_AVAILABLE", characteristic);
            if (characteristic.getValue() != null) {
                System.out.println("String+++++" + new String(characteristic.getValue()));
                System.out.println("String22222+++++" + IntsToStr(characteristic.getValue()));
                broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
            }
            System.out.println("--------onCharacteristicChanged-----");
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }

        @SuppressLint("NewApi")
        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            System.out.println("onDescriptorWriteonDescriptorWrite = " + status + ", descriptor =" + descriptor.getUuid().toString());
        }

        @Override
        public void onReliableWriteCompleted(BluetoothGatt gatt, int status) {
            super.onReliableWriteCompleted(gatt, status);
        }

        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
            System.out.println("rssi = " + rssi);
        }
    };




    public BluetoothLeService() {
        mAdapter = new BluetoothUtils().getmAdapter();
    }

    private void broadcastUpdate(String action) {
        try {
            Intent intent = new Intent(action);
            //    intent.setAction(action);
            //intent.setComponent(new ComponentName(ACTION_DATA_AVAILABLE, "com.moy.activity.BloodPressureActivity.MyReceiver"));
            Log.w(TAG, "int;" + intent.setAction(action));
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    private void broadcastUpdate(String action, BluetoothGattCharacteristic characteristic) {
        try {
             Intent intent = new Intent(action);
            //intent.setComponent(new ComponentName(ACTION_DATA_AVAILABLE, "com.moy.activity.BloodPressureActivity.MyReceiver"));
            intent.putExtra(BloodPressureActivity.DATA, characteristic.getValue());
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("NewApi")
    public boolean connect(final String address) {
        Log.w(TAG, "address is: " + address);
        if(mAdapter == null || address == null) {
            Log.w(TAG,"BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        // Previously connected device. Try to reconnect. (先前连接的设备。 尝试重新连接)
        if (mBluetoothDeviceAddress != null&& address.equals(mBluetoothDeviceAddress)&& mBluetoothGatt != null) {
            Log.d(TAG,"Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }
        final BluetoothDevice device = mAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found.  Unable to connect.");
            return false;
        }
        mBluetoothGatt = device.connectGatt(this, false, bluetoothGattCallback); //该函数才是真正的去进行连接
        Log.d(TAG, "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    @SuppressLint("NewApi")
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;
        return mBluetoothGatt.getServices();   //此处返回获取到的服务列表
    }

//    @SuppressLint("NewApi")
//    public void displayGattServices(List<BluetoothGattService> gattServices) {
//        if (gattServices == null)
//            return;
//        for (BluetoothGattService gattService : gattServices) { // 遍历出gattServices里面的所有服务
//            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
//            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) { // 遍历每条服务里的所有Characteristic
//                if (gattCharacteristic.getUuid().toString().equals("00000aaf2-0000-1000-8000-00805f9b34fb")) {
//                    // 有哪些UUID，每个UUID有什么属性及作用，一般硬件工程师都会给相应的文档。我们程序也可以读取其属性判断其属性。
//                    // 此处可以可根据UUID的类型对设备进行读操作，写操作，设置notification等操作
//                    // BluetoothGattCharacteristic gattNoticCharacteristic 假设是可设置通知的Characteristic
//                    // BluetoothGattCharacteristic gattWriteCharacteristic 假设是可读的Characteristic
////                    gattWriteCharacteristic = gattCharacteristic;
//                    System.out.println("gatt——————");
//                    readMsg();
////                    gattReadCharacteristic = gattCharacteristic;
////                    setCharacteristicNotification(gattReadCharacteristic, true);
//                }
//                System.out.println("gatt—+++++++++++++");
//            }
//        }
//    }


    @SuppressLint("NewApi")
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
        setCharacteristicNotification(characteristic, true);
    }

    @SuppressLint("NewApi")
    public void wirteCharacteristic(BluetoothGattCharacteristic characteristic) {

        if (mAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.writeCharacteristic(characteristic);
        Log.w(TAG, "写入成功");

    }

    @SuppressLint("NewApi")
    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (mAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID
                .fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
        if (descriptor != null) {
            System.out.println("write descriptor");
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);

        }
    }
@SuppressLint("NewApi")
public void sendMsg(String paramString) {
        if(mBluetoothGatt != null) {
            bluetoothGattService = mBluetoothGatt.getService(UUID.fromString("0000aaf0-0000-1000-8000-00805f9b34fb"));
            Log.w(TAG, "blue+:" + bluetoothGattService);
            writeBluetoothGattCharacteristic = bluetoothGattService.getCharacteristic(UUID_WRITE);
            Log.w(TAG, "blue11111+:" + writeBluetoothGattCharacteristic);
            if (writeBluetoothGattCharacteristic != null) {
                byte[] ps = paramString.getBytes();
                writeBluetoothGattCharacteristic.setValue(ps);
                wirteCharacteristic(writeBluetoothGattCharacteristic);
            }
        }
}
@SuppressLint("NewApi")
public void readMsg() {

    bluetoothGattService = mBluetoothGatt.getService(UUID.fromString("0000aaf0-0000-1000-8000-00805f9b34fb"));
    Log.w(TAG, "blue2+:" + bluetoothGattService);
    readBluetoothGattCharacteristic = bluetoothGattService.getCharacteristic(UUID_READ);
    Log.w(TAG, "blue2222+:" + readBluetoothGattCharacteristic);
    if(readBluetoothGattCharacteristic != null) {
        readCharacteristic(readBluetoothGattCharacteristic);
       // setCharacteristicNotification(readBluetoothGattCharacteristic, true);

        Log.w(TAG, "读取成功");
    }
}

    public String IntsToStr(byte[] paramArrayOfByte)
    {
        StringBuffer localStringBuffer = new StringBuffer();
        int i = 0;
        int j = paramArrayOfByte.length;
        if (i >= j) {
            return localStringBuffer.toString();
        } else if (i == paramArrayOfByte.length - 1) {
            localStringBuffer.append(Integer.toString(paramArrayOfByte[i] & 0xFF, 16));
        }
        for (;;)
        {
            i += 1;
            localStringBuffer.append(Integer.toString(paramArrayOfByte[i] & 0xFF, 16) + ",");
            break;
        }
        return localStringBuffer.toString();
    }

    @SuppressLint("NewApi")
    public void close()
    {
        if (this.mBluetoothGatt == null) {
            return;
        }
        this.mBluetoothGatt.close();
        this.mBluetoothGatt = null;
    }
    @Override
    public IBinder onBind(Intent paramIntent)
    {
        return null;
    }

    public boolean onUnbind(Intent paramIntent)
    {
        close();
        return super.onUnbind(paramIntent);
    }

    public class MyBindler extends Binder {
        public MyBindler() {

        }

        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }
}
