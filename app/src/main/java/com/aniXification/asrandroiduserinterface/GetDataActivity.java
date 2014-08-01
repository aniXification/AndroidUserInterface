package com.aniXification.asrandroiduserinterface;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GetDataActivity extends Activity {

	Button btnReturnData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_data);
		
		btnReturnData = (Button) findViewById(R.id.btn_return_data);
		btnReturnData.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
			    intent.putExtra("returnkey", "return this data");
			    setResult(RESULT_OK, intent);
			    finish();
			}
		});
		
	}
}
