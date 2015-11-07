package com.bin.weiai.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.bin.weiai.MainActivity;
import com.bin.weiai.R;
import com.lewisjuggins.miband.MiOverviewActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 
 * @author 作者：黄泽彬 E-mail：root@68xg.com
 * @version 创建时间：2015-5-7 下午10:17:22
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
public class Fragment_WeiAiLog extends Fragment  {
	View view;
	static Fragment_WeiAiLog tabGuanZhu;
	ListView list;
	private final String TAG = this.getClass().getSimpleName();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tabGuanZhu = this;
		view = inflater.inflate(R.layout.fragment_guanzhu, container, false);
 
		// initData();
		return view;
	}

	public static Fragment_WeiAiLog getInstance() {
		return tabGuanZhu;
	}


	

}
