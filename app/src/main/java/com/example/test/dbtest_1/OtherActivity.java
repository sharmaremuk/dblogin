package com.example.test.dbtest_1;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OtherActivity extends Activity implements OnClickListener {
    private EditText medi;
    // Progress Dialog
    private ProgressDialog pDialog;
    // JSON parser class
    JSONParser jsonParser = new JSONParser();
    private static final String SEARCH_URL = "http://sharmaremuk.000webhostapp.com/search.php";
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
	private static final String TAG_DATA = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        medi = (EditText) findViewById(R.id.medicine);
        Button bSearch = (Button) findViewById(R.id.search);
        bSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
 //       switch (v.getId()) {
 //           case R.id.login:
                new SearchPhar().execute();
                // here we have used, switch case, because on login activity you may
                // also want to show registration button, so if the user is new ! we can go the
                // registration activity , other than this we could also do this without switch
                // case.
 //           default:
 //               break;
 //       }
    }

    class SearchPhar extends AsyncTask<String, String, String> {
        /**
         * Before starting background thread Show Progress Dialog
         */
        boolean failure = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OtherActivity.this);
            pDialog.setMessage("Searching for Pharmacy");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // here Check for success tag
            int success;
            String medicine = medi.getText().toString();
            //String password = pass.getText().toString();
            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("medicine", medicine));
                //params.add(new BasicNameValuePair("password", password));

                Log.d("request!",medicine);

                JSONObject json = jsonParser.makeHttpRequest(
                        SEARCH_URL, "POST", params);

                // checking  log for json response
                Log.d("Searching", json.toString());

                // success tag for json
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                  //  Log.d("Successfully Login!", json.toString());

                    //Intent ii = new Intent(Login.this, OtherActivity.class);
                    //startActivity(ii);
                    //finish();
                    // this finish() method is used to tell android os that we are done with current //activity now! Moving to other activity

                    return json.getString(TAG_DATA);
                } else {

                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Once the background process is done we need to  Dismiss the progress dialog asap
         **/
        protected void onPostExecute(String message) {

            pDialog.dismiss();
            if (message != null) {
                Toast.makeText(OtherActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }
}





