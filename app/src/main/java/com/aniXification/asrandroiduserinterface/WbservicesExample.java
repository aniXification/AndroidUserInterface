package com.aniXification.asrandroiduserinterface;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.aniXification.asrandroiduserinterface.dto.CategoryDTO;
import com.aniXification.asrandroiduserinterface.util.JSONParser;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WbservicesExample extends ListActivity implements AdapterView.OnItemClickListener{

    private Context context;
    private String URL_CATEGORY = "http://android.riberasolutions.com/api/categories";
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String CREATED_AT = "created_at";

    ListView lv ;
    private Button btnPost;
    private CategoryDTO categoryDto;
    private JSONParser jsonParser;
    private String POST_CATEGORY_URL = "http://android.riberasolutions.com/admin/categories";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wbservices_example);

        btnPost = (Button) findViewById(R.id.button);
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PostAsyncTask().execute();
            }
        });

        getListView().setOnItemClickListener(this);

        new CategoryAsynTask(WbservicesExample.this).execute();
    }

    public class PostAsyncTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            jsonParser = new JSONParser();
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "POST started", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("name", "post_name_anish1"));
            params.add(new BasicNameValuePair("is_active", "Y"));
            params.add(new BasicNameValuePair("description", "POST_DESCRIPTION_ANISH1"));

            jsonParser.postJSONFromUrl(POST_CATEGORY_URL, params);

            Log.d("POST category PARAMS: ", " category params: "+ params);

            // return json
            //Log.e("JSON", json.toString());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(), "POST ended", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        HashMap<String, String> hash = (HashMap<String, String>)adapterView.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(), "selected item hash name " + hash.get(NAME), Toast.LENGTH_SHORT).show();

        Intent wbServicesItemIntent = new Intent(getApplicationContext(), WbServicesItemListActivity.class);
        wbServicesItemIntent.putExtra("id", hash.get(ID));
        wbServicesItemIntent.putExtra("name", hash.get(NAME));
        startActivity(wbServicesItemIntent);

    }

    public class CategoryAsynTask extends AsyncTask<Void, Void, Void>{

        private ProgressDialog dialog;
        private ListActivity activity;

        public CategoryAsynTask(ListActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Progress start");
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getCategory();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ListAdapter adapter = new SimpleAdapter(context, jsonlist,
                    R.layout.list_item, new String[] { ID, NAME,
                    DESCRIPTION, CREATED_AT }, new int[] {
                    R.id.id, R.id.name, R.id.description,
                    R.id.created_at });

            setListAdapter(adapter);

            // select single ListView item
            lv = getListView();
        }
    }

    public ArrayList<CategoryDTO> getCategory() {

        JSONParser jParser = new JSONParser();

        // get JSON data from URL
        JSONArray json = jParser.getJSONFromUrl(URL_CATEGORY);

        for (int i = 0; i < json.length(); i++) {

            try {
                JSONObject c = json.getJSONObject(i);
                String id = c.getString(ID);

                String name = c.getString(NAME);
                String description = c.getString(DESCRIPTION);
                String created_at = c.getString(CREATED_AT);

                HashMap<String, String> map = new HashMap<String, String>();

                // Add child node to HashMap key & value
                map.put(ID, id);
                map.put(NAME, name);
                map.put(DESCRIPTION, description);
                map.put(CREATED_AT, created_at);
                jsonlist.add(map);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.wbservices_example, menu);
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
