package com.bin.weiai.data;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import com.bin.weiai.R;  
 
 
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
 * @author  作者：黄泽彬	 E-mail：root@68xg.com
 * @version 创建时间：2015-5-7 下午10:17:22 
 *
 *                #####################################################
 *                #                                                   #
 *                #                       _oo0oo_                     #   
 *                #                      o8888888o                    #
 *                #                      88" . "88                    #
 *                #                      (| -_- |)                    #
 *                #                      0\  =  /0                    #   
 *                #                    ___/`---'\___                  #
 *                #                  .' \\|     |# '.                 #
 *                #                 / \\|||  :  |||# \                #
 *                #                / _||||| -:- |||||- \              #
 *                #               |   | \\\  -  #/ |   |              #
 *                #               | \_|  ''\---/''  |_/ |             #
 *                #               \  .-\__  '-'  ___/-. /             #
 *                #             ___'. .'  /--.--\  `. .'___           #
 *                #          ."" '<  `.___\_<|>_/___.' >' "".         #
 *                #         | | :  `- \`.;`\ _ /`;.`/ - ` : | |       #
 *                #         \  \ `_.   \_ __\ /__ _/   .-` /  /       #
 *                #     =====`-.____`.___ \_____/___.-`___.-'=====    #
 *                #                       `=---='                     #
 *                #     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   #
 *                #                                                   #
 *                #               佛祖保佑          永无BUG            	      #
 *                #                                                   #
 *                #####################################################
 */
public class Fragment_Main extends Fragment implements View.OnClickListener {
	View view;
	static Fragment_Main tabGuanZhu;
	ListView list;
	private final String TAG = this.getClass().getSimpleName();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		tabGuanZhu=this;
		view = inflater.inflate(R.layout.fragment_main, container, false);
		init();
		//initData();
		return view; 
	}
	public static Fragment_Main getInstance() {
		return tabGuanZhu;
	}
	
	 
private void init() {
	Toast.makeText(getActivity(), "sdgsdg", 1).show();
	vibrate(50L);

}
	public void initData() {
		list = (ListView) view.findViewById(R.id.listView1);
		
		
		ArrayList listItem = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < 400; i++) {
			 
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", R.drawable.user);// 图像资源的ID	 
			map.put("ItemUserName", "11"); 
			listItem.add(map);
		}
		// 生成适配器的Item和动态数组对应的元素
		SimpleAdapter listItemAdapter = new SimpleAdapter(getActivity(), listItem,// 数据源
				R.layout.list_item_guanzhuren,// ListItem的XML实现 动态数组与ImageItem对应的子项
				new String[] { "ItemImage", "ItemUserName"  },
				// ImageItem的XML文件里面的一个ImageView,两个TextView ID
				new int[] { R.id.imageView1, R.id.textView1 });
		list.setAdapter(listItemAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(getActivity(), String.valueOf(arg2), 1).show();
			}
		});
	}
 
	private void vibrate(final long duration) {
		Intent intent = new Intent("vibrate");
		intent.putExtra("duration", duration);
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
	}
	@Override
	public void onClick(View v) {
		Toast.makeText(getActivity(), "sdgsdg", 1).show();
		vibrate(50L);
		
	}
 
}
