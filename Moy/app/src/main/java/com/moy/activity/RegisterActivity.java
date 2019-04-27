package com.moy.activity;

import org.json.JSONException;
import org.json.JSONObject;


import com.moy.handler.RegisterHandler;
import com.moy.pojo.UserPOJO;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.moy.util.ImageUtils;
import com.moy.util.UploadUtils;


import java.io.File;


public class RegisterActivity extends Activity implements OnClickListener{

	protected static final int CHOOSE_PICTURE = 0;
	protected static final int TAKE_PICTURE = 1;
	private static final int CROP_SMALL_PICTURE = 2;
	protected static Uri tempUri;
	
	private Button bt_register;
	private Button bt_return;
	private EditText et_phonenum;
	private EditText et_password;
	private ImageButton ibt_icon;

	private String phonenum;
	private String password;
	private Context mContext;
	private String imagePath;
	
	public UserPOJO user;


	@Override
	protected void onCreate(Bundle savedInstanceState){
		///在Android2.2以后必须添加以下代码
		//本应用采用的Android4.0
		//设置线程的策略
		 StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
         .detectDiskReads()
         .detectDiskWrites()
         .detectNetwork()   // or .detectAll() for all detectable problems
         .penaltyLog()
         .build());
		//设置虚拟机的策略
		  StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		         .detectLeakedSqlLiteObjects()
		         //.detectLeakedClosableObjects()
		         .penaltyLog()
		         .penaltyDeath()
		         .build());
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setContentView(R.layout.)
	    setContentView(R.layout.register);
	
		mContext=this;
		bt_register = (Button)findViewById(R.id.register_btn);
		bt_return = (Button)findViewById(R.id.return_btn);
		et_phonenum = (EditText)findViewById(R.id.PhoneNum);
        et_password = (EditText)findViewById(R.id.Password);
		ibt_icon = (ImageButton)findViewById(R.id.ibt_icon);
		bt_register.setOnClickListener(this);
		bt_return.setOnClickListener(this);
		ibt_icon.setOnClickListener(this);
		imagePath = "";

		}

	
/*
				String result = RegisterHandler.register(userName,password,secpassword,sex,age,height,weight,telephone);
				//String result = RegisterHandler.register(userName, password, secpassword);
				try{
					JSONObject json = new JSONObject(result);
					if(json.getString("message").equals("false")){
						Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_LONG).show();
					}else if(json.getString("message").equals("true")){
						Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
					
					Intent intent = new Intent();
					intent.putExtra("userName", userName);
					intent.setClass(RegisterActivity.this, AddinfoActivity.class);
					startActivity(intent);
					RegisterActivity.this.finish();
					}
				}catch (JSONException e) {
					e.printStackTrace();
				/*}	*/

	@Override
	public void onClick(View view){
		switch (view.getId()) {
			case R.id.return_btn:
				finish();
				break;
			case R.id.register_btn:
				phonenum = et_phonenum.getText().toString().trim();
				password = et_password.getText().toString().trim();
				boolean result = check();
				if(result){
					new UploadUtils(mContext,imagePath,phonenum);
					alert();
				}
				break;
			case R.id.ibt_icon:
				showChoosePicDialog();
				break;
			default:
				return;

		}

	}

	private boolean check() {
		String result = RegisterHandler.checkphonenum(phonenum);
		System.out.println("检查结果++" + result);
		try {
			JSONObject json = new JSONObject(result);
			if (json.getString("message").equals("false")) {
				Toast.makeText(RegisterActivity.this, "该手机号已注册，请直接登录", Toast.LENGTH_LONG).show();
				return false;
			}else if(json.getString("message").equals("true")){
				return true;
			}
			else{
				Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return false;
	}
	private void alert(){
		/*
		new AlertDialog.Builder(this).setTitle("确认手机号码")//设置对话框标题
				.setMessage("我们将发送验证码短信到这个号码："+ phonenum)//设置显示的内容
				.setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
						// TODO Auto-generated method stub
						Intent intent = new Intent();
						intent.setClass(RegisterActivity.this,ValidateActivity.class);
						intent.putExtra("countryCode","86");
						intent.putExtra("phoneNum",phonenum);
						startActivity(intent);
						dialog.dismiss();
					}
				})
				.setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮
					@Override
					public void onClick(DialogInterface dialog, int which) {//响应事件
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				}).show();//在按键响应事件中显示此对话框
				*/
		final AlertDialog dlg = new AlertDialog.Builder(this).create();



		Window window = dlg.getWindow();
		dlg.show();
		window.setContentView(R.layout.register_confirm_phonenum);// 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
		TextView phonenum = (TextView) window.findViewById(R.id.tv_register_confirm_phonenum);
		phonenum.setText("+86 "+ RegisterActivity.this.phonenum);
		Button ok = (Button) window.findViewById(R.id.bt_register_confirm_ok);
		Button no = (Button) window.findViewById(R.id.bt_register_confirm_no);

		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(RegisterActivity.this,ValidateActivity.class);
				intent.putExtra("countryCode","86");
				intent.putExtra("phoneNum",RegisterActivity.this.phonenum);
				intent.putExtra("password",password);
				startActivity(intent);
				dlg.cancel();

		   }
		});
		no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				dlg.cancel(); // 退出应用
			}
		});
	}

	/**
	 * 显示修改头像的对话框
	 */
	protected void showChoosePicDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("设置头像");
		String[] items = { "选择本地照片", "拍照" };
		builder.setNegativeButton("取消", null);
		builder.setItems(items, new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case CHOOSE_PICTURE: // 选择本地照片
						Intent openAlbumIntent = new Intent(
								Intent.ACTION_GET_CONTENT);
						openAlbumIntent.setType("image/*");
						startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
						break;
					case TAKE_PICTURE: // 拍照
						Intent openCameraIntent = new Intent(
								MediaStore.ACTION_IMAGE_CAPTURE);
						tempUri = Uri.fromFile(new File(Environment
								.getExternalStorageDirectory(), "image.jpg"));
						// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
						openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
						startActivityForResult(openCameraIntent, TAKE_PICTURE);
						break;
				}
			}
		});
		builder.create().show();
	}

	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { // 如果返回码是可以用的
			switch (requestCode) {
				case TAKE_PICTURE:
					startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
					break;
				case CHOOSE_PICTURE:
					startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
					break;
				case CROP_SMALL_PICTURE:
					if (data != null) {
						setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
					}
					break;
			}
		}
	}

	/**
	 * 裁剪图片方法实现
	 *
	 * @param uri
	 */
	protected void startPhotoZoom(Uri uri) {
		if (uri == null) {
			Log.i("tag", "The uri is not exist.");
		}
		tempUri = uri;
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CROP_SMALL_PICTURE);
	}

	/**
	 * 保存裁剪之后的图片数据
	 *
	 * param
	 *
	 * param picdata
	 */
	@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
	protected void setImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
			ibt_icon.setBackground(new BitmapDrawable(photo));
			imagePath = ImageUtils.savePhoto(photo, Environment
					.getExternalStorageDirectory().getAbsolutePath(), String
					.valueOf(System.currentTimeMillis()));
			//uploadPic(mContext,photo);
		}
	}




/*
	else if(json.getString("message").equals("true")){
			Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();

			Intent intent = new Intent();
			intent.putExtra("userName", userName);
			intent.setClass(RegisterActivity.this, AddinfoActivity.class);
			startActivity(intent);
			RegisterActivity.this.finish();
		}
*/

}
