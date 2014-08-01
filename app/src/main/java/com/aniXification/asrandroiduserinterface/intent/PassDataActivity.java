package com.aniXification.asrandroiduserinterface.intent;

import com.aniXification.asrandroiduserinterface.R;
import com.aniXification.asrandroiduserinterface.R.id;
import com.aniXification.asrandroiduserinterface.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PassDataActivity extends Activity {

	private TextView tvPassedData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pass_data);
		
		String data = getIntent().getStringExtra("data");
		
		tvPassedData = (TextView) findViewById(R.id.tv_passedData);
		tvPassedData.setText(data);
		
	}
}
