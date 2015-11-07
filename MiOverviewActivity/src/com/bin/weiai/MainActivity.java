package com.bin.weiai;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.bin.weiai.data.Fragment_Data;
import com.bin.weiai.data.Fragment_Main;
import com.bin.weiai.data.Fragment_WeiAiLog;
import com.bin.weiai.data.Fragment_Wo;
import com.lewisjuggins.miband.ApplicationArrayAdapter;
import com.lewisjuggins.miband.MiBandCommunicationService;
import com.lewisjuggins.miband.MiBandConstants;
import com.lewisjuggins.miband.MiOverviewActivity;
import com.lewisjuggins.miband.colorpicker.ColorPickerDialog;
import com.lewisjuggins.miband.preferences.Application;
import com.lewisjuggins.miband.preferences.UserPreferences;
import com.melnykov.fab.FloatingActionButton;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private ApplicationArrayAdapter mAppArrayAdapter;
	public static UserPreferences userPreferences;
	public static MainActivity instanceMainActivity;
 
	private String[] mTitles = new String[] { "First Fragment!",
			"Second Fragment!", "Third Fragment!" };

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		instanceMainActivity = this;// 单例
		setOverflowShowingAlways();
		getActionBar().setDisplayShowHomeEnabled(false);
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		initDatas();
	 onCreateInit();
	}

	private void initDatas() {

		Fragment_Data.setTitle1("1111");
		Fragment_Data.setTitle2("2222");
		Fragment_Data.setTitle3("3333");

		Fragment_Main tabFragment = new Fragment_Main();
		mTabs.add(tabFragment);

		Fragment_WeiAiLog tabGuanZhu = new Fragment_WeiAiLog();
		mTabs.add(tabGuanZhu);

		Fragment_Wo tabWo = new Fragment_Wo();
		mTabs.add(tabWo);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return mTabs.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		initTabIndicator();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private void initTabIndicator() {
		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);

		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);

		one.setIconAlpha(1.0f);
	}

	@Override
	public void onPageSelected(int arg0) {
	}

	// 滑动时候下面图标颜色改变
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		// Log.e("TAG", "position = " + position + " , positionOffset = "
		// + positionOffset);
		if (positionOffset > 0) {
			ChangeColorIconWithTextView left = mTabIndicator.get(position);
			ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {

		resetOtherTabs();
		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;

		}

	}

	/**
	 * 重置其他的Tab
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);
		}
	}

	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	private void setOverflowShowingAlways() {
		try {
			// true if a permanent menu key is present, false otherwise.
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	private void actionSendBroadcast(String  broadcase) {
		Intent intent = new Intent();
		intent.setAction(broadcase);
        MainActivity.this.sendOrderedBroadcast(intent, null);   //有序广播发送  
	 
	}
	private void onCreateInit() {
		final Intent serviceIntent = new Intent(this,
				MiBandCommunicationService.class);
		startService(serviceIntent);
		
//actionSendBroadcast("cn.com.smartdevices.bracelet.TimeChangedReceiver");
//actionSendBroadcast("cn.com.smartdevices.bracelet.SystemBindReceiver");
//actionSendBroadcast("com.xiaomi.push.service.receivers.NetworkStatusReceiver");
//actionSendBroadcast("cn.com.smartdevices.bracelet.lab.sync.LabWifiConnReceiver");
// 
// 
//	
		
		
		
		
		try {
			UserPreferences
					.loadPreferences(openFileInput(UserPreferences.FILE_NAME));
		} catch (FileNotFoundException e) {
			new UserPreferences().savePreferences(getPreferencesOutputStream());
		}

		userPreferences = UserPreferences.getInstance();

		final List<Application> appArray = userPreferences.getAppArray();
		final PackageManager pm = getPackageManager();

		for (Application app : appArray) {
			try {
				app.setmAppName(pm.getApplicationLabel(
						pm.getApplicationInfo(app.getmPackageName(),
								PackageManager.GET_META_DATA)).toString());
			} catch (PackageManager.NameNotFoundException ignored) {

			}
		}

		Collections.sort(appArray, new Comparator<Object>() {
			@Override
			public int compare(Object lhs, Object rhs) {
				if (lhs instanceof Application && rhs instanceof Application) {
					return ((Application) lhs).getmAppName().compareTo(
							((Application) rhs).getmAppName());
				} else {
					return 0;
				}
			}
		});

		checkMACAddressRequired();
		if(!isSetAddress())
			showAddMACAddress();
	}

	// 震动控制
	public void vibrate(final long duration) {
		try {
			Intent intent = new Intent("vibrate");
			intent.putExtra("duration", duration);
			LocalBroadcastManager.getInstance(MainActivity.this).sendBroadcast(
					intent);
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void showLight(final String action) {
		new ColorPickerDialog(MainActivity.this,
				userPreferences.getmBandColour(),
				new ColorPickerDialog.OnColorSelectedListener() {
					@Override
					public void onColorSelected(int rgb) {
						Log.i("light", String.valueOf(rgb));
						final int red = ((rgb >> 16) & 0x0ff) / 42;
						final int green = ((rgb >> 8) & 0x0ff) / 42;
						final int blue = ((rgb) & 0x0ff) / 42;

						setColour(red, green, blue);
						Toast.makeText(getApplicationContext(), "发送的rgb值是:" + red + "," + green + "," + blue,
								Toast.LENGTH_LONG).show();
						try {
							JSONObject jSONObject=new JSONObject();
							jSONObject.put("r", String.valueOf(red));
							jSONObject.put("g", String.valueOf(green));
							jSONObject.put("b", String.valueOf(blue));
							System.out.println(jSONObject.toString());
							new SendQingLvAction().getInstance().sendAction(getApplicationContext(), action, jSONObject.toString());
						} catch (JSONException e) { 	 
						}
						userPreferences.setmBandColour(rgb);
						userPreferences
								.savePreferences(getPreferencesOutputStream());
					}
				}).show();
	}
public boolean isSetAddress(){
boolean isSetAddress=false;
String lastMAC = userPreferences.getMiBandMAC();
	Pattern p = Pattern.compile("([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}");
	Matcher m = p.matcher(lastMAC);
	if (m.find()) 
		isSetAddress=true;    
	return isSetAddress;
	
}
	public void showAddMACAddress() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(getResources().getString(R.string.set_MAC_address));
		final EditText input = new EditText(this);
 
		builder.setMessage(getResources().getString(R.string.look_MAC_address));
		input.setInputType(InputType.TYPE_CLASS_TEXT);
		String lastMAC = userPreferences.getMiBandMAC();
		if (lastMAC.equals(""))
			lastMAC = MiBandConstants.MAC_ADDRESS_FILTER;
		input.setText(lastMAC.replaceAll(":",""));
		builder.setView(input);
		builder.setPositiveButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String macAddress = input.getText().toString()
								.trim().toUpperCase();
						if(macAddress.length()!=12){
							Toast.makeText( getBaseContext(), getResources().getString(
													R.string.set_MAC_address_error),
									Toast.LENGTH_SHORT).show();
							return;
						}
						macAddress=macAddress.substring(0, 2)+":"+
								macAddress.substring(2, 4)+":"+
								macAddress.substring(4, 6)+":"+
								macAddress.substring(6, 8)+":"+
								macAddress.substring(8, 10)+":"+
								macAddress.substring(10, 12);
						Toast.makeText(getApplicationContext(), macAddress, 1).show();
						if (macAddress.equals("")
								|| macAddress
										.equals(MiBandConstants.MAC_ADDRESS_FILTER
												.toUpperCase())) {
							userPreferences.setMiBandMAC("");
							userPreferences
									.savePreferences(getPreferencesOutputStream());
							Toast.makeText(
									getBaseContext(),
									getResources()
											.getString(
													R.string.set_MAC_address_removed),
									Toast.LENGTH_SHORT).show();
						} else {
							Pattern p = Pattern
									.compile("([0-9a-fA-F]{2}:){5}[0-9a-fA-F]{2}");
							Matcher m = p.matcher(macAddress);
							if (m.find()) {
								userPreferences.setMiBandMAC(macAddress);
								userPreferences
										.savePreferences(getPreferencesOutputStream());
								Toast.makeText(
										getBaseContext(),
										getResources()
												.getString(
														R.string.set_MAC_address_ok),
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(
										getBaseContext(),
										getResources()
												.getString(
														R.string.set_MAC_address_error),
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				});
		builder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		builder.show();
	}
	// 亮灯控制
	public void setColour(int r, int g, int b) {
  
		Intent intent = new Intent("colour");
		intent.putExtra("red", r);
		intent.putExtra("green", g);
		intent.putExtra("blue", b);
		LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}

	// 得到输出流，我不知道这里是干什么的
	private FileOutputStream getPreferencesOutputStream() {
		try {
			return openFileOutput(UserPreferences.FILE_NAME,
					Context.MODE_PRIVATE);
		} catch (FileNotFoundException e) {
			try {
				new UserPreferences().savePreferences(openFileOutput(
						UserPreferences.FILE_NAME, Context.MODE_PRIVATE));
			} catch (FileNotFoundException ignored) {
			}
		}
		return null;
	}

	// 点击mac进行判断的
	private void checkMACAddressRequired() {
		boolean showAlert = true;

		if (BluetoothAdapter.getDefaultAdapter().isEnabled()) {
			final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
					.getDefaultAdapter();
			final Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
					.getBondedDevices();

			for (BluetoothDevice pairedDevice : pairedDevices) {
				if (pairedDevice.getName().equals("MI")
						&& pairedDevice.getAddress().startsWith(
								MiBandConstants.MAC_ADDRESS_FILTER)) {
					showAlert = false;
				}
			}

			if (showAlert) {
				if (!userPreferences.getMiBandMAC().equals(""))
					showAlert = false;
			}

			if (showAlert) {
				Toast.makeText(getBaseContext(),
						getResources().getString(R.string.alert_MAC_address),
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		try {
			if (!BluetoothAdapter.getDefaultAdapter().isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, 1);
			}

		} catch (NullPointerException e) {
			Toast.makeText(getApplicationContext(),
					"没有可用的蓝牙设备\n（No Bluetooth device available）",
					Toast.LENGTH_SHORT).show();
		}
	}

}
