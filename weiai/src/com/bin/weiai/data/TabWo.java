package com.bin.weiai.data;
import com.bin.weiai.R;
import com.bin.weiai.ZhuTi; 

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.TextView;
/**
 *
 * @author  作者：黄泽彬	 E-mail：root@68xg.com
 * @version 创建时间：2015-5-7 下午10:17:44 
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
public class TabWo  extends Fragment implements View.OnClickListener {
	View view;
	static TabWo tabWo;
	TextView tv; 

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tabWo=this;
		view = inflater.inflate(R.layout.fragment_wo, container, false);
		initData();
		return view;
	}
	public static TabWo getInstance() {
		return tabWo;
	}

	public void initData() {
		 (view.findViewById(R.id.meGuanZhu)).setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
	switch (v.getId()) {
	case R.id.meGuanZhu:
		startActivity(new Intent().setClass(getActivity(), ZhuTi.class));
		break;

	default:
		break;
	}
		
	}

 
}
