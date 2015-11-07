package com.bin.weiai;

 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Log;

public class BaseUtils {
	private static BaseUtils baseUtils;

	private BaseUtils() {
	}

	public static BaseUtils getInstance() {
		if (baseUtils == null) {
			baseUtils = new BaseUtils();
		}
		return baseUtils;
	}

	/**
	 * 返回当前程序版本名
	 */
	public String getAppVersion(Context context) {
		String versionname = "";
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
			// versioncode = pi.versionCode;
			versionname = pi.versionName;
		} catch (NameNotFoundException e) {// e.printStackTrace();
		}
		// return Integer.toString(versioncode);
		return versionname;
	}

	/**
	 * 
	 * @param 网络是否连接
	 * @return
	 */
	public boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	/**
	 * 数组合并
	 * 
	 * @param first
	 * @param second
	 * @return
	 */
	public <T> T[] arrConcat(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public void saveBitmapToSdcard(String bitName, Bitmap mBitmap) {
		File f = new File("/sdcard/" + bitName + ".png");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			// DebugMessage.put("在保存图片时出错："+e.toString());
		}
		FileOutputStream fOut = null;
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
		}
		mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 转化drawableToBitmap
	public static Bitmap drawableToBitmap(Drawable drawable) {
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
								: Bitmap.Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public static String simSerialNumber, deviceId, subscriberId, line1Number,
			androidId, android_Os_Build_SERIAL,
			android_Os_Build_VERSION_SDK_INT;

	//
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static void getTelephonyManager(Context context) {
		try {
			TelephonyManager tm = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			try {
				simSerialNumber = tm.getSimSerialNumber();
			} catch (Exception e) {
			}
			try {
				deviceId = tm.getDeviceId();
			} catch (Exception e) {
			}
			try {
				subscriberId = tm.getSubscriberId();
			} catch (Exception e) {
			}
			try {
				line1Number = tm.getLine1Number();
			} catch (Exception e) {
			}
			try {
				androidId = Secure.getString(context.getContentResolver(),
						Secure.ANDROID_ID);
			} catch (Exception e) {
			}
			try {
				android_Os_Build_SERIAL = android.os.Build.SERIAL;
			} catch (Exception e) {
			}
			try {
				android_Os_Build_VERSION_SDK_INT = String
						.valueOf(android.os.Build.VERSION.SDK_INT);
			} catch (Exception e) {
			}

		} catch (Exception e) {
		}

	}

	public static String urlToUrlHost(String url) {
		url= EncodeToUrl(url);
		int temp = url.indexOf("//");
		int temp2 = url.indexOf("/", temp + 2);
		if (temp == -1) {
			return url;
		}
		if (temp2 == -1) {
			return url.substring(temp + 2, url.length());
		}
		return url.substring(temp + 2, temp2);
	}

	public static String urlToEncode(String url) {
		try {
			url = URLEncoder.encode(url, "UTF-8");
		} catch (Exception e) { }
		return url;
	}
	public static String EncodeToUrl(String url){
		url = url.replaceAll("%3F", "?");
		url = url.replaceAll("%3D", "=");
		url = url.replaceAll("%26", "&");
		url = url.replaceAll("%3A", ":");
		url = url.replaceAll("%2F", "/");
		url = url.replaceAll("%40", "@");
		return url;
	}
	
}
