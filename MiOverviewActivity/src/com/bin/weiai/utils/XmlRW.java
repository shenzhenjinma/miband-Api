package com.bin.weiai.utils; 

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class XmlRW {
	public static String XmlR(Context context, String string) {
		SharedPreferences sp = context.getSharedPreferences("bin_weiai_v1",
				Context.MODE_PRIVATE);
		// 使用getString方法获得value，第2个参数是value的默认值
		return sp.getString(string, "null").replaceAll("﻿", "");

	}

	public static void XmlW(Context context, String string, String value) {
		SharedPreferences sp = context.getSharedPreferences("bin_weiai_v1",
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(string, value);
		editor.commit();// 提交数据
	}

	public static void XmlClear(Context context) {
		SharedPreferences sp = context.getSharedPreferences("bin_weiai_v1",
				Context.MODE_PRIVATE);
		sp.edit().clear().commit();
	}

}