package com.aniXification.asrandroiduserinterface.intent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.aniXification.asrandroiduserinterface.GetDataActivity;
import com.aniXification.asrandroiduserinterface.R;

public class IntentMainActivity extends Activity implements OnClickListener{

	Button btnStartNewActivity, btnPassData, btnGetData;
	private Spinner spinner;
	
	private int REQUEST_CODE = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent_main);
		
		btnStartNewActivity = (Button) findViewById(R.id.btn_start);
		btnPassData = (Button) findViewById(R.id.btn_pass_data);
		btnGetData = (Button) findViewById(R.id.btn_get_data);
		
		btnStartNewActivity.setOnClickListener(this);
		btnPassData.setOnClickListener(this);
		btnGetData.setOnClickListener(this);
		
		spinner = (Spinner) findViewById(R.id.spinner);
	    ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.intents, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
		
	}

	public void onClickEvent(View view) {
	    int position = spinner.getSelectedItemPosition();
	    Intent intent = null;
	    switch (position) {
	    case 0:
	      intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.aniXification.com"));
	      break;
	    case 1:
	      intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:9841472979"));
	      break;
	    case 2:
	      intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:9841472979"));
	      //startActivity(intent);
	      break;
	    case 3:
	      intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:27.980020,86.921543?z=19"));
	      break;
	    case 4:
	      intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=mount everest"));
	      break;
	    case 5:
	      intent = new Intent("android.media.action.IMAGE_CAPTURE");
	      break;
	    case 6:
	      intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
	      break;
	    case 7:
	      intent = new Intent(Intent.ACTION_EDIT, Uri.parse("content://contacts/people/1"));
	      break;

	    }
	    if (intent != null) {
	      startActivity(intent);
	    }
	  }
/*
	  @Override
	  public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == Activity.RESULT_OK && requestCode == 0) {
	      String result = data.toURI();
	      Toast.makeText(this, result, Toast.LENGTH_LONG);
	    }
	  }*/
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			startActivity(new Intent(getApplicationContext(), NewActivity.class));
			break;
			
		case R.id.btn_pass_data:
			Intent passDataIntent = new Intent(getApplicationContext(), PassDataActivity.class);
			passDataIntent.putExtra("data", "Passed Data");
			startActivity(passDataIntent);
			
			break;
			
		case R.id.btn_get_data:
			startActivityForResult(new Intent(getApplicationContext(), GetDataActivity.class), REQUEST_CODE);
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
			if (data.hasExtra("returnkey")) {
				String result = data.getExtras().getString("returnkey");
				if (result != null && result.length() > 0) {
					Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
				}
			}
		}
	}
	
}
