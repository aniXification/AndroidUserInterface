package com.aniXification.asrandroiduserinterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.aniXification.asrandroiduserinterface.R;
import com.aniXification.asrandroiduserinterface.util.NetworkChecker;

public class CheckInternetConnection extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_internet_connection);

        if(NetworkChecker.checkInternetConnection(getApplicationContext())){
            Toast.makeText(getApplicationContext(), "Internet available", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "NO Internet", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check_internet_connection, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
