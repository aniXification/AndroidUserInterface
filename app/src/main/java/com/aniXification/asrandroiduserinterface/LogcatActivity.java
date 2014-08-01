package com.aniXification.asrandroiduserinterface;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class LogcatActivity extends Activity {

	private String TAG = "LogcatActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logcat);
		
		Log.d(TAG, "log.d");
		Log.e(TAG, "log.e");
		Log.v(TAG, "log.v");
		Log.w(TAG, "log.w");
		
		
		
	}
}
