package com.bin.weiai;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley; 

/**
 *
 * @author  作者：黄泽彬	 E-mail：root@68xg.com
 * @version 创建时间：2015-5-19 下午9:44:35 
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
public class SendQingLvAction implements Response.Listener<String>, Response.ErrorListener  {
private static	SendQingLvAction sendQingLvAction;
 public SendQingLvAction getInstance() {
	if(sendQingLvAction==null){
		sendQingLvAction=new SendQingLvAction();
	}
	return sendQingLvAction;

}

 
 public void sendAction(Context context,String action,String tag){
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		String urlTempStr = Constants.URL + "qinglv.php?user="
				+ LoginActivity.user + "&pass=" + LoginActivity.pass +"&action="+action +"&action_tag="+tag;
		requestQueue.add(new StringRequest(Request.Method.GET, urlTempStr,
				this,this));
 }
	@Override
	public void onErrorResponse(VolleyError error) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResponse(String response) {
//		int startTemp = response.indexOf("{");
//		int endTemp = response.lastIndexOf("}");
//		if (startTemp == -1 || endTemp == -1) {
//			toast("服务器接口错误");
//			return;
//		}
//		response = response.substring(startTemp, endTemp + 1);
//		try {
//			JSONObject	jsonResponse = new JSONObject(response);
//		}catch(Exception e){
//			
//		}
		
	}

}
