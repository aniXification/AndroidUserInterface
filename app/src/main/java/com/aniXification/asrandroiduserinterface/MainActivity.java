package com.aniXification.asrandroiduserinterface;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aniXification.asrandroiduserinterface.intent.IntentMainActivity;
import com.aniXification.asrandroiduserinterface.lifecycle.ActivityA;

/**
 * @author aniXification
 * Jul 23, 2014 2014
 * MainActivity.java
 */

public class MainActivity extends Activity implements OnItemClickListener{

	private ListView listView;
	private GridView gridView;
	private int switchCase = 0; //listview activated

    static String[] listItems = { 
    	"Input Controls", 
    	"Alert Dialog", 
    	"Notification",
    	"Activity Lifecycle",
    	"Intents",
    	"Logcat",
    	"Relative Layout",
        };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView);
		gridView = (GridView) findViewById(R.id.gridview);

		listView.setAdapter( new MyArrayAdapter(this, R.layout.row_listview, listItems));
		gridView.setAdapter( new MyArrayAdapter(this, R.layout.row_gridview, listItems));
		
		listView.setOnItemClickListener(this);
		gridView.setOnItemClickListener(this);
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
	        case R.id.menu_switch:
	        	
	        	if(switchCase == 0){
	        		switchCase = 1;
	        		listView.setVisibility(View.GONE);
	        		gridView.setVisibility(View.VISIBLE);
	        	} else if(switchCase == 1){
	        		switchCase = 0;
	        		listView.setVisibility(View.VISIBLE);
	        		gridView.setVisibility(View.GONE);
	        	} 
	        	
                break;
                
	        default:
				break;
				
		}
		return true;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
    	getMenuInflater().inflate(R.menu.menu_main_activity, menu);
    	return super.onCreateOptionsMenu(menu);
	}

	private class MyArrayAdapter extends ArrayAdapter<String>{

		private int resourceLayoutId;

        public MyArrayAdapter(Context context, int resource, String[] values) {
            super(context, resource, values);  
            resourceLayoutId = resource;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(resourceLayoutId, parent, false);
            TextView textView = (TextView) view.findViewById(R.id.textView1);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView1);

            textView.setText( getItem(position));

            return view;
        }
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(getApplicationContext(), "Position: " + position, Toast.LENGTH_SHORT).show();

		if(position == 0){
			startActivity(new Intent(getApplicationContext(), InputControlsActivity.class));
		} else if(position == 1){
			
			AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               Toast.makeText(getApplicationContext(), "OK pressed", Toast.LENGTH_SHORT).show();
			           }
			       });
			builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   Toast.makeText(getApplicationContext(), "CANCEL pressed", Toast.LENGTH_SHORT).show();
			           }
			       });
			builder.setTitle("Title");
			builder.setMessage("This is the main content of the Alert Dailog.");

			AlertDialog dialog = builder.create();
			dialog.show();
			
		} else if(position == 2){
			
			final int NOTIFY_ME_ID = 1337;
			final int ALARM_REQUEST_CODE = 1234;
			
			// This pending intent will open after notification click
			Intent i = new Intent(this, NotificationActivity.class);
			
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
			stackBuilder.addParentStack(MainActivity.class);
			stackBuilder.addNextIntent(i);
			
		    //PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
			PendingIntent pIntent = stackBuilder.getPendingIntent(ALARM_REQUEST_CODE, PendingIntent.FLAG_UPDATE_CURRENT);
			
			
			
			NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);
			notificationBuilder.setContentTitle("Notification");
			notificationBuilder.setContentText("Click here to see the notification!");
			notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
			notificationBuilder.setAutoCancel(true);
			notificationBuilder.setContentIntent(pIntent);		
			
			notificationManager.notify(NOTIFY_ME_ID, notificationBuilder.build());	
			
		} else if(position == 3){
			startActivity(new Intent(getApplicationContext(), ActivityA.class));
		} else if(position == 4){
			startActivity(new Intent(getApplicationContext(), IntentMainActivity.class));
		} else if(position == 5){
			startActivity(new Intent(getApplicationContext(), LogcatActivity.class));
		} else if(position == 6){
			startActivity(new Intent(getApplicationContext(), RelativeLayoutActivity.class));
		} else {
			Toast.makeText(getApplicationContext(), "position: " + position, Toast.LENGTH_SHORT).show();
		}
		
	}
	
}
