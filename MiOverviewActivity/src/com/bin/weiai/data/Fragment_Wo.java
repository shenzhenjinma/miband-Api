package com.bin.weiai.data;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley; 
import com.bin.weiai.BaseUtils;
import com.bin.weiai.Constants;
import com.bin.weiai.LoginActivity;
import com.bin.weiai.MainActivity;
import com.bin.weiai.R;
import com.bin.weiai.utils.XmlRW;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author 作者：黄泽彬 E-mail：root@68xg.com
 * @version 创建时间：2015-5-7 下午10:17:44
 * 
 *          ##################################################### # # # _oo0oo_
 *          # # o8888888o # # 88" . "88 # # (| -_- |) # # 0\ = /0 # #
 *          ___/`---'\___ # # .' \\| |# '. # # / \\||| : |||# \ # # / _||||| -:-
 *          |||||- \ # # | | \\\ - #/ | | # # | \_| ''\---/'' |_/ | # # \ .-\__
 *          '-' ___/-. / # # ___'. .' /--.--\ `. .'___ # # ."" '<
 *          `.___\_<|>_/___.' >' "". # # | | : `- \`.;`\ _ /`;.`/ - ` : | | # #
 *          \ \ `_. \_ __\ /__ _/ .-` / / # # =====`-.____`.___
 *          \_____/___.-`___.-'===== # # `=---=' # #
 *          ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ # # # # 佛祖保佑 永无BUG # # #
 *          #####################################################
 */
public class Fragment_Wo extends Fragment implements OnClickListener,
		DialogInterface.OnClickListener, Response.Listener<String>,
		Response.ErrorListener {
	View view; 
	TextView wodeid;
	RequestQueue requestQueue;
	ProgressDialog progressDialog;
	JSONObject jsonResponse;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		view = inflater.inflate(R.layout.fragment_wo, container, false);
		requestQueue = Volley.newRequestQueue(getActivity());
		initData();
		return view;
	}

 

	public void initData() {
		(view.findViewById(R.id.sharetofriend)).setOnClickListener(this);
		(view.findViewById(R.id.exitlogin)).setOnClickListener(this);
		(view.findViewById(R.id.mytest)).setOnClickListener(this);
		(view.findViewById(R.id.yijianfankui)).setOnClickListener(this);
		(view.findViewById(R.id.appupdate)).setOnClickListener(this); 
		 (view.findViewById(R.id.shouhuanshezhi)).setOnClickListener(this);
		wodeid=(TextView) view.findViewById(R.id.wodeid) ;
		wodeid.setText("我的ID："+Fragment_Main.wodeid);
	}

 
	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.shouhuanshezhi:
			MainActivity.instanceMainActivity.showAddMACAddress();
				break;
 

		case R.id.exitlogin:
			XmlRW.XmlW(getActivity(), "pass", "");
			// Intent i = getActivity().getPackageManager()
			// .getLaunchIntentForPackage(getActivity().getPackageName());
			// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			startActivity(new Intent().setClass(getActivity(),
					LoginActivity.class));
			MainActivity.instanceMainActivity.finish();
			break;

		case R.id.sharetofriend:
			shareApp();
			break;
		case R.id.mytest:
			MainActivity.instanceMainActivity.vibrate(50L);
			MainActivity.instanceMainActivity.setColour(6, 6, 6);
			break;
		/*
		 * case R.id.zhaopin:
		 * 
		 * bundle.putString("title", "广招纳贤"); bundle.putString("url",
		 * HttpUtils.URL + "app_zhaopin.html"); bundle.putString("favoriteStr",
		 * "null"); intent.putExtras(bundle); // 把Bundle塞入Intent里面
		 * startActivity(intent); break;
		 */
 
		case R.id.appupdate:
			progressDialog = ProgressDialog.show(getActivity(), null,
					"正在检查更新...");

			String urlTempStr = Constants.URL + "app_info.php";
			requestQueue.add(new StringRequest(Request.Method.GET, urlTempStr,
					this, this));

			break;
		// case R.id.aboutapp:
		//
		// bundle.putString("title","关于"+getString(R.string.app_name));
		// bundle.putString("url",HttpUtils.URL + "about.html");
		// bundle.putString("favoriteStr", "null");
		// intent.putExtras(bundle); // 把Bundle塞入Intent里面
		// startActivity(intent);
		// break;
		default:
			break;
		}
	}

	private void shareApp() {
		String url = "http://www.68xg.com";
		String title = "Hi，我正在使用weiai。下载地址：" + url;
		Bitmap bitmap = BaseUtils.getInstance().drawableToBitmap(
				getActivity().getResources()
						.getDrawable(R.drawable.ic_launcher));
		BaseUtils.getInstance().saveBitmapToSdcard("app_icon", bitmap);

		ShareSDK.initSDK(getActivity());
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(url);
		// text是分享文本，所有平台都需要这个字段
		oks.setText(title);
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/app_icon.png");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment(title);
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(url);

		// 启动分享GUI
		oks.show(getActivity());
	}

	private void toast(String str) {
		Toast.makeText(getActivity(), str, 0).show();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		try {
			Uri uri = Uri.parse(jsonResponse.getString("appdownloadurl"));
			Intent it = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(it);
		} catch (JSONException e) {
		}

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
			case 253:
				if (!BaseUtils.getInstance().getAppVersion(getActivity())
						.equals(jsonResponse.getString("version"))) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setIcon(android.R.drawable.ic_dialog_info);
					builder.setTitle("发现新版本是否下载？");
					builder.setMessage(jsonResponse
							.getString("appintroduction"));
					builder.setPositiveButton("是", this);
					builder.setNegativeButton("否", null);
					builder.show();
				} else {
					Toast.makeText(getActivity(), "已是最新版本", 0).show();
				}
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

}
 
	
 
