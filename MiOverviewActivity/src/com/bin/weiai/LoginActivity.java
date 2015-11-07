package com.bin.weiai;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

import android.os.Handler.Callback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.framework.utils.UIHandler;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bin.weiai.utils.XmlRW;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

public class LoginActivity extends Activity implements PlatformActionListener,
		Callback, Response.Listener<String>, Response.ErrorListener {
	TextView loginregbutton, loginregtext;
	EditText ed1, ed2, ed3;// 用戶名和密碼的編輯框
	ProgressDialog progressDialog;
	// public static GeterSeter geterSeter;
	public static String user, pass, myid, qinglvid;
	public static Activity activity;
	public static JSONObject jsonResponse;
	RequestQueue requestQueue;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_and_reg);
		loginregbutton = (TextView) findViewById(R.id.loginregbutton);
		loginregtext = (TextView) findViewById(R.id.loginregtext);
		ed1 = (EditText) findViewById(R.id.userEdit);
		ed2 = (EditText) findViewById(R.id.passEdit);
		ed3 = (EditText) findViewById(R.id.passEdit2);
		activity = this;
		ShareSDK.initSDK(this);// 初始化sharesdk
		requestQueue = Volley.newRequestQueue(this);
		user = XmlRW.XmlR(getApplicationContext(), "user");
		pass = XmlRW.XmlR(getApplicationContext(), "pass");
		myid = XmlRW.XmlR(getApplicationContext(), "myid");
		qinglvid = XmlRW.XmlR(getApplicationContext(), "qinglvid");
		if (!("null".equals(user) || "null".equals(pass) || "".equals(pass))) {
			startActivity(new Intent().setClass(this, MainActivity.class));
			finish();
		}

	}

	public void onClick(View v) {
		switch (v.getId()) {
		// case R.id.zhaohuimima:
		// Intent intent = new Intent();
		// intent.setClass(activity, PasswordProtection.class);
		// Bundle bundle = new Bundle();
		// bundle.putBoolean("isZhaoHui", true);
		// intent.putExtras(bundle);
		// startActivity(intent);
		// break;
		case R.id.loginbutton:
			loginOrReg();
			break;
		case R.id.regbutton:
			regButton();
			break;
		case R.id.login_qq:
			user = "d3_login_qq";
			Platform qqPlat = ShareSDK.getPlatform(this, QQ.NAME);
			// plat_qq.SSOSetting(true); // true表示不使用SSO方式授权
			qqPlat.setPlatformActionListener(this);
			qqPlat.authorize();
			progressDialog = ProgressDialog.show(this, null, "授权中...");
			break;
		case R.id.login_wechat:
			user = "d3_login_wechat";
			Platform weChatPlat = ShareSDK.getPlatform(this, Wechat.NAME);
			// plat_qq.SSOSetting(true); // true表示不使用SSO方式授权
			weChatPlat.setPlatformActionListener(this);
			weChatPlat.authorize();
			progressDialog = ProgressDialog.show(this, null, "授权中...");
			break;
		case R.id.login_sina:
			user = "d3_login_sinaweibo";
			Platform sinaWeiboPlat = ShareSDK.getPlatform(this, SinaWeibo.NAME);
			// plat_qq.SSOSetting(true); // true表示不使用SSO方式授权
			sinaWeiboPlat.setPlatformActionListener(this);
			sinaWeiboPlat.authorize();
			progressDialog = ProgressDialog.show(this, null, "授权中...");
			break;
		default:
			break;
		}

	}

	private void regButton() {
		View lay = findViewById(R.id.reglayout);
		if (lay.getVisibility() == View.GONE) {
			lay.setVisibility(View.VISIBLE);
			loginregbutton.setText("注册");
			loginregtext.setText("<返回登录界面");
		} else {
			lay.setVisibility(View.GONE);
			loginregbutton.setText("登录");
			loginregtext.setText("注册账号");
		}
	}

	private void loginOrReg() {
		if (!BaseUtils.getInstance().isConnectingToInternet(
				getApplicationContext())) {
			toast("没有网络");
			return;
		}
		user = ed1.getText().toString();
		pass = ed2.getText().toString();
		if ("登录".equals(loginregbutton.getText().toString())) {
			if ("".equals(user) || "".equals(pass)) {
				toast("用户名或者密码不能为空");
				return;
			}

			login();
		} else {
			reg();
		}
	}

	private void login() {
		progressDialog = ProgressDialog.show(this, null, "登录中...");
		String urlTempStr = Constants.URL + "login.php?user=" + user + "&pass="
				+ pass;
		requestQueue.add(new StringRequest(Request.Method.GET, urlTempStr,
				this, this));

	}

	private void reg() {
		if ("".equals(ed1.getText().toString())
				|| "".equals(ed2.getText().toString())
				|| "".equals(ed3.getText().toString())) {
			toast("用户名或者密码不能为空");
			return;
		}
		if (!(ed2.getText().toString().equals(ed3.getText().toString()))) {
			toast("两次输入密码不一致");

			return;
		}
		if (ed1.getText().toString().length() < 3) {
			toast("用户名长度最少4位");

			return;
		}
		if (ed2.getText().toString().length() < 5) {
			toast("密码长度最少6位");
			return;
		}
		progressDialog = ProgressDialog.show(this, null, "注册中...");
		BaseUtils.getInstance().getTelephonyManager(activity); // 获取一下设备的资料先,哈哈，彬哥说：累了就喝杯茶吧，虽然我不知道你是谁
		String urlTempStr = Constants.URL + "reg.php?user="
				+ ed1.getText().toString() + "&pass="
				+ ed2.getText().toString();
		StringRequest stringRequest = new StringRequest(Request.Method.POST,
				urlTempStr, this, this) {

			protected Map<String, String> getParams() {
				Map<String, String> params = new HashMap<String, String>();
				params.put("p_simSerialNumber", BaseUtils.simSerialNumber);
				params.put("p_deviceId", BaseUtils.deviceId);
				params.put("p_subscriberId", BaseUtils.subscriberId);
				params.put("p_line1Number", BaseUtils.line1Number);
				params.put("p_androidId", BaseUtils.androidId);
				params.put("p_android_Os_Build_SERIAL",
						BaseUtils.android_Os_Build_SERIAL);
				params.put("p_android_Os_Build_VERSION_SDK_INT",
						BaseUtils.android_Os_Build_VERSION_SDK_INT);
				return params;
			}
		};
		requestQueue.add(stringRequest);
	}

	public static void finishMe() {
		activity.finish();
	}

	private void toast(String str) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
	}

	private void toast(String str, boolean ShowLongTime) {
		Toast.makeText(getApplicationContext(), str, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onResponse(String response) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}

		int startTemp = response.indexOf("{");
		int endTemp = response.lastIndexOf("}");
		if (startTemp == -1 || endTemp == -1) {
			toast("服务器接口错误");
			return;
		}
		response = response.substring(startTemp, endTemp + 1);
		try {
			jsonResponse = new JSONObject(response);
			int code = jsonResponse.getInt("code");
			switch (code) {
			case 210:// 210登录成功`

				XmlRW.XmlW(getApplicationContext(), "user", user);
				XmlRW.XmlW(getApplicationContext(), "pass", pass);
				myid=jsonResponse.getString("myid");
				qinglvid=jsonResponse.getString("qinglvid");
				XmlRW.XmlW(getApplicationContext(), "myid",myid);
				XmlRW.XmlW(getApplicationContext(), "qinglvid",qinglvid );

				startActivity(new Intent().setClass(this, MainActivity.class));

				finish();
				break;
			case 211:// 211密码错误
				toast("用户名或密码错误");
				break;
			case 200:
				toast("注册成功,请点击登录按钮登录");
				regButton();
				break;
			case 201:
				toast("你已注册过该帐号,请点击登录按钮登录");
				regButton();
				break;
			case 202:
			case 203:
				toast("注册失败，该用户已被注册");
				break;

			}

		} catch (Exception e) {
		}
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		toast("网络不给力");// String err = error.getMessage();
	}

	public void onComplete(Platform plat, int action,
			HashMap<String, Object> res) {
		Message msg = new Message();
		msg.arg1 = 1;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	public void onError(Platform plat, int action, Throwable t) {
		t.printStackTrace();

		Message msg = new Message();
		msg.arg1 = 2;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	public void onCancel(Platform plat, int action) {
		Message msg = new Message();
		msg.arg1 = 3;
		msg.arg2 = action;
		msg.obj = plat;
		UIHandler.sendMessage(msg, this);
	}

	/**
	 * 处理操作结果
	 * <p>
	 * 如果获取到用户的名称，则显示名称；否则如果已经授权，则显示 平台名称
	 */
	public boolean handleMessage(Message msg) {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
		Platform plat = (Platform) msg.obj;
		String text = actionToString(msg.arg2);
		Log.e(plat.getName(), text);
		switch (msg.arg1) {
		case 1: {
			final String userid = plat.getDb().getUserId().trim();
			final String username = plat.getDb().getUserName().trim();
			final String usergender = plat.getDb().getUserGender().trim();
			pass = userid;
			if ("".equals(pass) || pass == null) {
				break;
			}
			try {
				pass = Md5.getStringMD5String("mybaobo" + pass);// 这里两次md5安全一点
				pass = Md5.getStringMD5String(pass + "mybaobo");// 这里两次md5安全一点
			} catch (Exception e) {
			}

			toast("授权成功");
			// Toast.makeText(getApplicationContext(), text,
			// Toast.LENGTH_SHORT).show();
			progressDialog = ProgressDialog.show(this, null, "正在登录...");
			// 成功
			String urlTempStr = Constants.URL + "login_d3fang.php?user=" + user
					+ "&pass=" + pass + "&userid=" + userid + "&username="
					+ username + "&usergender=" + usergender;
			requestQueue.add(new StringRequest(Request.Method.GET, urlTempStr,
					this, this));

		}
			break;
		case 2: {
			// 失败

			toast("授权失败(plat.getName()" + " 错误：  " + text + ")");
			return false;
		}
		case 3: {
			// 取消
			toast("取消授权");
			return false;
		}
		}

		return false;
	}

	/** 将action转换为String */
	public static String actionToString(int action) {
		switch (action) {
		case Platform.ACTION_AUTHORIZING:
			return "ACTION_AUTHORIZING";
		case Platform.ACTION_GETTING_FRIEND_LIST:
			return "ACTION_GETTING_FRIEND_LIST";
		case Platform.ACTION_FOLLOWING_USER:
			return "ACTION_FOLLOWING_USER";
		case Platform.ACTION_SENDING_DIRECT_MESSAGE:
			return "ACTION_SENDING_DIRECT_MESSAGE";
		case Platform.ACTION_TIMELINE:
			return "ACTION_TIMELINE";
		case Platform.ACTION_USER_INFOR:
			return "ACTION_USER_INFOR";
		case Platform.ACTION_SHARE:
			return "ACTION_SHARE";
		default: {
			return "UNKNOWN";
		}
		}
	}

}
