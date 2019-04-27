package com.moy.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.moy.adapter.MyDeviceAdapter;
import com.moy.handler.ExaminationHandler;
import com.moy.service.BluetoothLeService;
import com.moy.util.BluetoothUtils;
import com.moy.util.ConnectUtil;
import com.moy.util.MyApplication;
import com.moy.util.ToastUtile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by hu on 2018/12/11.
 */

public class BloodPressureActivity extends Activity {

    public static final int REQUEST_CODE = 1;
    private List<BluetoothDevice> mDeviceList = new ArrayList<>();
    private List<BluetoothDevice> mBondedDeviceList = new ArrayList<>();
    public static final String EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS";
    public static final String EXTRAS_DEVICE_NAME = "DEVICE_NAME";
    public static final String DATA = "EXTRA_DATA";

    private int userId;
//    Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
    private Button startButton;
    private Button stopButton;
    private Button bt_back;
    private Button bt_save;
    private TextView tv_text1;
    private TextView tv_gaoya;
    private TextView tv_diya;
    private BluetoothAdapter mAdapter;
    private BluetoothUtils bluetoothUtils;
    private BluetoothLeService bluetoothLeService;
    private byte[] ps;
    private Intent intent;
    private String mDeviceName;
    private String mDeviceAddress;
    private boolean isConnected;
    private MyDeviceAdapter myDeviceAdapter;
    private MyReceiver myReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private TextView tv_blueScan;
    private Timer timer;
    private int flag;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bloodpressure);
        onInitView(savedInstanceState);
        bluetoothUtils = new BluetoothUtils();
        bluetoothLeService = new BluetoothLeService();
        mAdapter = bluetoothUtils.getmAdapter();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        timer = new Timer();
        bluetoothUtils.turnOnBluetooth(this, REQUEST_CODE);
        intent = getIntent();
        mDeviceName = intent.getStringExtra(EXTRAS_DEVICE_NAME);
        mDeviceAddress = intent.getStringExtra(EXTRAS_DEVICE_ADDRESS);
        Log.w(BloodPressureActivity.class.getSimpleName(),"设备地址：" +mDeviceAddress);
        isConnected = bluetoothLeService.connect(mDeviceAddress);

        myReceiver = new MyReceiver();
        registerBluetoothReceiver(myReceiver);

    }
    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
        {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    private void registerBluetoothReceiver(BroadcastReceiver broadcastReceiver){;
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_CONNECTED");
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED");
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED");
        localIntentFilter.addAction("com.example.bluetooth.le.ACTION_DATA_AVAILABLE");

        localBroadcastManager.registerReceiver(broadcastReceiver, localIntentFilter);
    }


    private  final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bluetoothLeService = null;
        }
    };

    /**
     * 退出时注销广播、注销连接过程、注销等待连接的监听
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(serviceConnection);
        bluetoothLeService = null;
        mAdapter.disable();
//        unregisterReceiver(myReceiver);
    }

protected void onInitView(Bundle paramBundle) {
        userId = MyApplication.getUser().getUserId();
        tv_gaoya = (TextView) findViewById(R.id.gaoya);
        tv_diya = (TextView) findViewById(R.id.diya);
        tv_text1 = (TextView) findViewById(R.id.text1); //动态变化的值
        tv_blueScan = (TextView) findViewById(R.id.scanblue);;
        this.startButton = ((Button) findViewById(R.id.bloodpressurestart));
        this.stopButton = ((Button) findViewById(R.id.bloodpressurestop));
        bt_back = (Button) findViewById(R.id.backblood);
        bt_save = (Button) findViewById(R.id.save);

        bindService(new Intent(this, BluetoothLeService.class), this.serviceConnection, 0);
        this.startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (isConnected) {
                        bluetoothLeService.sendMsg("AT+ST:1\r\n");
                        if (flag == 0){
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    bluetoothLeService.readMsg();
                                }
                            }, 500);
                            flag = 1;
                        }
                    } else {
                        ToastUtile.showText(BloodPressureActivity.this, "请先连接蓝牙");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        this.stopButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    bluetoothLeService.sendMsg("AT+ST:0\r\n");
                    flag = 0;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        this.tv_blueScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BloodPressureActivity.this, DeviceScanActivity.class);
                startActivity(intent);
            }
        });

        bt_back.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {


                BloodPressureActivity.this.finish();
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Date curDate = new Date(System.currentTimeMillis());
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
//                date = simpleDateFormat.format(curDate);
//                String a = saveData(userName, tv_gaoya.getText().toString(), tv_diya.getText().toString(), date);
//                System.out.println("保存成功。。。。" + a);
                ExaminationHandler.loadresultB("bldpressure", tv_gaoya.getText().toString(), tv_diya.getText().toString(), userId);
                System.out.println("保存成功。。。。");
            }
        });
}

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        System.out.println("action===+" + action);

        if ("com.example.bluetooth.le.ACTION_GATT_CONNECTED".equals(action)) {
            //ToastUtile.showText(BloodPressureActivity.this, "蓝牙连接成功");
            tv_blueScan.setText("蓝牙已连接");
            System.out.println("蓝牙广播————————");

        } else if ("com.example.bluetooth.le.ACTION_GATT_DISCONNECTED".equals(action)) {
            //蓝牙未连接
            tv_blueScan.setText("蓝牙未连接");
        }
        if ("com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED".equals(action)) {
            System.out.println("发现服务");
         //   bluetoothLeService.displayGattServices(bluetoothLeService.getSupportedGattServices());
        }
        if ("com.example.bluetooth.le.ACTION_DATA_AVAILABLE".equals(action)) {
            System.out.println("action===+++" + action);
            System.out.println("广播接收者" + intent.getByteArrayExtra(DATA));
            displayData(intent.getByteArrayExtra(DATA));
        }
    }
}

    public void displayData(byte[] paramByte) {
        if (paramByte == null) {
            return;
        }
        String str2;
        String str3;

        String str1 = new String(paramByte);
        System.out.println("str++++++" + str1);
        if (!str1.startsWith("U0")) {
            if (str1.startsWith("U1")) {
                str2 = str1.substring(3, 6);
                str3 = str1.substring(7, 10);
               // str1 = str1.substring(11, 13);
                System.out.println("tv_gaoya" + tv_gaoya);
                tv_gaoya.setText(str2);
                tv_diya.setText(str3);
                flag = 0;
                return;
            }
        }
        for (; ; ) {
            String str;
            try {
                if (str1.length() == 11) {
                    str = str1.substring(3, 6);
                    System.out.println("tv_text1111111" + str);
                    tv_text1.setText(str);
                    return;
                }
                if(str1.startsWith("U0") && str1.length()==16 && str1.endsWith("S1")){
                    ToastUtile.showText(this, "测量失败，请重新测量！！！");
                    finish();
                }
                if (str1.length() != 18) {
                    continue;
                }
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private String saveData(String userName, String highPressure, String lowPressure, String date) {
//
//        if(highPressure == null && lowPressure == null && date == null) {
//            return null;
//        }
//        System.out.println("用户名"+userName);
//        System.out.println("用户名2"+highPressure);
//        System.out.println("用户名3"+lowPressure);
//
//        //根据用户名添加数据
//        String result = "";//数据上传后返回的结果
//        HttpClient httpclient = new DefaultHttpClient();
//        HttpGet request = new HttpGet(ConnectUtil.BASE_URL + "saveBloodPressureServlet?userName=" + userName
//                + "&highPressure=" + highPressure + "&lowPressure=" + lowPressure + "&date=" + date);
//        try {
//
//            HttpResponse response = httpclient.execute(request);
//            if(response.getStatusLine().getStatusCode()==200){
//                HttpEntity entity = response.getEntity();
//                result = EntityUtils.toString(entity,"utf-8");
//                return result;
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return result;
//    }
}

