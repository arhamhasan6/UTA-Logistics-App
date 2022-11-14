package com.hsn.syedtaha.utalogistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class customer1 extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer1);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");

        getinfo(username);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("username", username);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void getinfo(String username) {

        // url to post our data
        String url = "http://192.168.100.7/utalogistics/view_user.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(customer1.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // on below line passing our response to json object.
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are checking if the response is null or not.
                    //Toast.makeText(customer1.this,response, Toast.LENGTH_SHORT).show();

                    TextView txtView = (TextView) findViewById(R.id.tv1);
                    txtView.setText(jsonObject.getString("userid"));
                    TextView txtView1 = (TextView) findViewById(R.id.tv2);
                    txtView1.setText(jsonObject.getString("fname"));
                    TextView txtView2 = (TextView) findViewById(R.id.tv3);
                    txtView2.setText(jsonObject.getString("lname"));
                    TextView txtView3 = (TextView) findViewById(R.id.tv4);
                    txtView3.setText(jsonObject.getString("address"));
                    TextView txtView4 = (TextView) findViewById(R.id.tv5);
                    txtView4.setText(jsonObject.getString("cnicno"));
                    TextView txtView5 = (TextView) findViewById(R.id.tv6);
                    txtView5.setText(jsonObject.getString("dob"));
                    TextView txtView6 = (TextView) findViewById(R.id.tv7);
                    txtView6.setText(jsonObject.getString("city"));
                    TextView txtView7 = (TextView) findViewById(R.id.tv8);
                    txtView7.setText(jsonObject.getString("contactno"));
                    TextView txtView8 = (TextView) findViewById(R.id.tv9);
                    txtView8.setText(jsonObject.getString("username"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(customer1.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key and value pair to our parameters.
                params.put("username",username);

                // at last we are returning our params.
                return params;
            }
        };
        Volley.newRequestQueue(customer1.this).add(request);
    }


}
