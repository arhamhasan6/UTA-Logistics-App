package com.hsn.syedtaha.utalogistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Wallet extends AppCompatActivity {

    String walletid,username,full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


//        getSupportActionBar().hide();
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void getinfo(String username) {

        // url to post our data
        String url = "http://192.168.100.36/utalogistics/view_wallet.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(Wallet.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // on below line passing our response to json object.
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are checking if the response is null or not.
                    //Toast.makeText(Wallet.this, response, Toast.LENGTH_SHORT).show();

                    // if we get the data then we are setting it in our text views in below line.
                    TextView txtView = (TextView) findViewById(R.id.textView5);
                    walletid= jsonObject.getString("EWalletID");
                    txtView.setText(jsonObject.getString("EWalletID"));
                    TextView txtView1 = (TextView) findViewById(R.id.textView6);
                    txtView1.setText(jsonObject.getString("BalanceAmount"));
                    TextView txtView2 = (TextView) findViewById(R.id.textView7);
                    txtView2.setText(jsonObject.getString("DueAmount"));
                    TextView txtView3 = (TextView) findViewById(R.id.textView8);
                    txtView3.setText(jsonObject.getString("LastDOT"));

                    // on below line we are displaying
                    // a success toast message.
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Wallet.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(Wallet.this).add(request);
    }

    public void wallet(View view) {
        full= walletid+":"+username ;
        Intent intent = new Intent(getApplicationContext(), deposit.class);
        intent.putExtra("full", full);
        startActivity(intent);
        finish();
    }


}

