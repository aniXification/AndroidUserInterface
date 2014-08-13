package com.aniXification.asrandroiduserinterface;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.aniXification.asrandroiduserinterface.dto.CategoryDTO;
import com.aniXification.asrandroiduserinterface.util.JSONParser;
import com.aniXification.asrandroiduserinterface.util.NetworkChecker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class WbServicesItemListActivity extends ListActivity {

    private String categoryId, categoryName;

    private Context context;
    private String URL_ITEMS = "http://android.riberasolutions.com/api/items_by_category?id=";
    ArrayList<HashMap<String, String>> jsonlist = new ArrayList<HashMap<String, String>>();

    private static final String ID = "id";
    private static final String CATEGORY_ID = "category_id";
    private static final String ITEM = "item";
    private static final String IS_ACTIVE = "is_active";

    ListView lv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wb_services_detail);

        categoryId = getIntent().getExtras().get("id").toString();
        categoryName = getIntent().getExtras().get("name").toString();

        if(NetworkChecker.checkInternetConnection(getApplicationContext())){
            new ItemsAsynTask(WbServicesItemListActivity.this).execute();
        } else {
            Toast.makeText(getApplicationContext(), "Please connect to internet first!", Toast.LENGTH_SHORT).show();
        }
    }


    public class ItemsAsynTask extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog;
        private ListActivity activity;

        public ItemsAsynTask(ListActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Getting list items for " + categoryName);
            this.dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            getItemsByCategory();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ListAdapter adapter = new SimpleAdapter(context, jsonlist,
                    R.layout.list_item, new String[] { ID, CATEGORY_ID,
                    ITEM, IS_ACTIVE }, new int[] {
                    R.id.id, R.id.name, R.id.description,
                    R.id.created_at });

            setListAdapter(adapter);

            // select single ListView item
            lv = getListView();
        }
    }

    public ArrayList<CategoryDTO> getItemsByCategory() {

        JSONParser jParser = new JSONParser();

        // get JSON data from URL
        JSONArray json = jParser.getJSONFromUrl(URL_ITEMS + categoryId);

        for (int i = 0; i < json.length(); i++) {

            try {
                JSONObject c = json.getJSONObject(i);
                String id = c.getString(ID);
                String categoryId = c.getString(CATEGORY_ID);
                String itemName = c.getString(ITEM);
                String isActive = c.getString(IS_ACTIVE);

                HashMap<String, String> map = new HashMap<String, String>();

                // Add child node to HashMap key & value
                map.put(ID, id);
                map.put(CATEGORY_ID, categoryId);
                map.put(ITEM, itemName);
                map.put(IS_ACTIVE, isActive);
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
        getMenuInflater().inflate(R.menu.wb_services_detail, menu);
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
