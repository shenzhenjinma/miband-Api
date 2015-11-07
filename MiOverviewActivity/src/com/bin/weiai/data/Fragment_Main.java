package com.bin.weiai.data;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bin.weiai.Constants;
import com.bin.weiai.LoginActivity;
import com.bin.weiai.MainActivity;
import com.bin.weiai.R;
import com.bin.weiai.SendQingLvAction;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_Main extends Fragment implements View.OnClickListener {
	View view;
	static Fragment_Main tabFaXian;
	String qinglvid = "";
	private static ValleyPeiDui valleyPeiDui;
	private static ValleyQingLvAction valleyQingLvAction;
	private static String peiduText = null;
	private static JSONObject jsonResponse;
	public static String wodeid;
	private int mLongr, mLongg, mLongb;
	private boolean isShowLongLight=false;
	ListView list;
	TextView peiduiid;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tabFaXian = this;
		view = inflater.inflate(R.layout.fragment_main, container, false);
		init();

		return view;
	}

	public static Fragment_Main getInstance() {
		return tabFaXian;
	}

	private void login(boolean isShowConnect) {
		if (isShowConnect) {
			peiduiid.setText("连接服务器中...");
		}
		RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
		String urlTempStr = Constants.URL + "login.php?user="
				+ LoginActivity.user + "&pass=" + LoginActivity.pass
				+ "&qinglvid=" + qinglvid;
		requestQueue.add(new StringRequest(Request.Method.GET, urlTempStr,
				new ValleyPeiDui().getInstance(), new ValleyPeiDui()
						.getInstance()));

	}

	private void getQingLvAction() {

		try {
			RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
			String urlTempStr = Constants.URL + "qinglv.php?user="
					+ LoginActivity.user + "&pass=" + LoginActivity.pass;
			requestQueue.add(new StringRequest(Request.Method.GET, urlTempStr,
					new ValleyQingLvAction().getInstance(),
					new ValleyQingLvAction().getInstance()));
		} catch (Exception e) { e.printStackTrace(); }
	}

	private void init() {
		view.findViewById(R.id.action_xintiao).setOnClickListener(this);
		view.findViewById(R.id.action_light).setOnClickListener(this);
		view.findViewById(R.id.action_xunxi).setOnClickListener(this);
		view.findViewById(R.id.action_xinqing).setOnClickListener(this);

		peiduiid = (TextView) view.findViewById(R.id.peiduiid);
		peiduiid.setOnClickListener(this);

		if (peiduText == null) {
			login(true);
		} else {
			peiduiid.setText(peiduText);
		}
	}

	public void initData() {
		int code;
		try {
			code = jsonResponse.getInt("code");

			switch (code) {
			case 210:
				wodeid = jsonResponse.getString("myid");
				if (jsonResponse.getBoolean("ispeidui")) {
					peiduText = ("已配对 TA的ID:" + jsonResponse
							.getString("qinglvid"));
					ha.postDelayed(getQingLvActionRu, 1000);
					return;
				}
				ha.postDelayed(ru2, 1000);
				if (jsonResponse.isNull("qinglvid")) {
					peiduText = ("未添加配对，点此添加");
					return;
				}
				peiduText = ("等待" + jsonResponse.getString("qinglvid") + "确认");
				break;
			case 211:

				break;
			case 214:
				int action = jsonResponse.getInt("action");
				if (action != 0) {
					action(action);
				}
				break;
			default:
				break;
			}
		} catch (Exception e) {
		}

	}

	private void action(int action) {
		switch (action) {
		case 1:
			MainActivity.instanceMainActivity.vibrate(50L);
			break;
		case 2:

			try {
				JSONObject rgbJSON = new JSONObject(
						jsonResponse.getString("action_tag"));

				int r = rgbJSON.getInt("r");
				int g = rgbJSON.getInt("g");
				int b = rgbJSON.getInt("b");
				MainActivity.instanceMainActivity.setColour(r, g, b);
			} catch (JSONException e) {
			}
			break;
		case 3:
			isShowLongLight=true;
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("关闭亮灯");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					isShowLongLight=false; 
				}
			});
			builder.show();
			try {
		
				JSONObject rgbJSON = new JSONObject(
						jsonResponse.getString("action_tag"));

				mLongr = rgbJSON.getInt("r");
				mLongg = rgbJSON.getInt("g");
				mLongb = rgbJSON.getInt("b");
				ha.post(showLight);

			} catch (JSONException e) {
			}
			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.action_xintiao:
			new SendQingLvAction().getInstance().sendAction(getActivity(), "1",
					"");
			// MainActivity.instanceMainActivity.vibrate(50L);

			toast("已发送");
			break;
		case R.id.action_light:
			MainActivity.instanceMainActivity.showLight("2");

			toast("已发送");
			break;
		case R.id.action_xunxi:
			toast("这个功能还需要时间，现在这些先玩吧");
			break;
		case R.id.action_xinqing:
			MainActivity.instanceMainActivity.showLight("3");

			toast("已发送");
			break;
		case R.id.peiduiid:
			showAddQingLv();
			break;
		default:
			break;
		}

	}

	private class ValleyPeiDui implements Response.Listener<String>,
			Response.ErrorListener {

		public ValleyPeiDui getInstance() {
			if (valleyPeiDui == null) {
				valleyPeiDui = new ValleyPeiDui();
			}
			return valleyPeiDui;
		}

		@Override
		public void onErrorResponse(VolleyError error) {
			ha.postDelayed(ru, 1000);
		}

		@Override
		public void onResponse(String response) {
			int startTemp = response.indexOf("{");
			int endTemp = response.lastIndexOf("}");
			if (startTemp == -1 || endTemp == -1) {
				toast("服务器接口错误");
				onErrorResponse(null);
				return;
			}
			response = response.substring(startTemp, endTemp + 1);
			try {
				jsonResponse = new JSONObject(response);
				initData();
				peiduiid.setText(peiduText);
			} catch (Exception e) {
				onErrorResponse(null);
			}
		}
	}

	private class ValleyQingLvAction implements Response.Listener<String>,
			Response.ErrorListener {
		public ValleyQingLvAction getInstance() {
			if (valleyQingLvAction == null) {
				valleyQingLvAction = new ValleyQingLvAction();
			}
			return valleyQingLvAction;
		}

		@Override
		public void onErrorResponse(VolleyError error) {

		}

		@Override
		public void onResponse(String response) {
			int startTemp = response.indexOf("{");
			int endTemp = response.lastIndexOf("}");
			if (startTemp == -1 || endTemp == -1) {
				toast("服务器接口错误");
				onErrorResponse(null);
				return;
			}
			response = response.substring(startTemp, endTemp + 1);
			try {
				jsonResponse = new JSONObject(response);
				initData();
			} catch (Exception e) {
				onErrorResponse(null);
			}
		}
	}

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void showAddQingLv() {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("输入TA的ID");
		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
		try {
			if (jsonResponse != null
					&& !jsonResponse.getString("qinglvid").equals("null"))

				input.setText(jsonResponse.getString("qinglvid"));
		} catch (JSONException e) {
		}
		builder.setView(input);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				qinglvid = input.getText().toString();
				if (qinglvid.isEmpty()) {
					toast("没有输入内容");
				} else {
					login(true);
				}
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.show();
	}

	private void toast(String str) {
		Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
	}

	Handler ha = new Handler();
	Runnable ru = new Runnable() {
		@Override
		public void run() {
			login(true);
			ha.removeCallbacks(ru);
		}
	};
	Runnable ru2 = new Runnable() {
		@Override
		public void run() {
			login(false);
			ha.removeCallbacks(ru);
		}
	};
	Runnable getQingLvActionRu = new Runnable() {
		@Override
		public void run() {
			getQingLvAction();
			ha.postDelayed(getQingLvActionRu, 1000);
		}
	};
	Runnable showLight = new Runnable() {
		@Override
		public void run() {
			MainActivity.instanceMainActivity.setColour(mLongr, mLongg, mLongb);
			
			if(isShowLongLight){
				ha.postDelayed(showLight, 3000);
			}else{
				ha.removeCallbacks(showLight);
			}
		} 
	};
}
