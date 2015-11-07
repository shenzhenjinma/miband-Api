package com.bin.weiai.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.bin.weiai.R;
 
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Fragment_FaXian extends Fragment {
	View view;
	static Fragment_FaXian tabFaXian;
	ListView list;
	TextView tv;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		tabFaXian = this;
		view = inflater.inflate(R.layout.fragment_main, container, false);
	 
		return view;
	}

	public static Fragment_FaXian getInstance() {
		return tabFaXian; 
	}

  
  
}
