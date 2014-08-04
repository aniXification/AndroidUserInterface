package com.aniXification.asrandroiduserinterface;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.aniXification.asrandroiduserinterface.R;

public class AsynTaskActivity extends Activity{

    private Button btn;
    private TextView tv;
    private ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asyn_task);

        btn = (Button) findViewById(R.id.button);
        tv = (TextView) findViewById(R.id.textView);
        pb = (ProgressBar) findViewById(R.id.progressBar);

    }


    public void buttonCLick(View view){
        Toast.makeText(getApplicationContext(), "BOO", Toast.LENGTH_SHORT).show();

        new AsyncTaskTest().execute();
    }

    public class AsyncTaskTest extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids){

            try {
                for (int i=0; i<5; i++){
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            pb.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);

            super.onPostExecute(aVoid);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.asyn_task, menu);
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
