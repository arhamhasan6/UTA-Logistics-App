package com.hsn.syedtaha.utalogistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    ImageView order,wallet,customer_info;
    String username,userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        order = findViewById(R.id.orderImage);
        wallet = findViewById(R.id.ewalletImage);
        customer_info = findViewById(R.id.profile);
        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        getinfo(username);



    }

    private void getinfo(String username) {

        // url to post our data
        String url = "http://192.168.100.20/utalogistics/mainactivity.php";

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // on below line passing our response to json object.
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are checking if the response is null or not.
                    //Toast.makeText(Wallet.this, response, Toast.LENGTH_SHORT).show();

                    // if we get the data then we are setting it in our text views in below line.

                    userid= jsonObject.getString("userid");

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
                Toast.makeText(MainActivity.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
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
        Volley.newRequestQueue(MainActivity.this).add(request);
    }

    public void user_info(View view) {
        Intent intent = new Intent(getApplicationContext(), customer1.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }
    public void wallett(View view) {
        Intent intent = new Intent(getApplicationContext(), Wallet.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    public void order(View view) {
        Intent intent = new Intent(getApplicationContext(), PackageOrder.class);
        intent.putExtra("username", username);
        intent.putExtra("userid", userid);
        startActivity(intent);
        finish();
    }

    public void aboutUs(View view) {
        Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }

    public void signout(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }



}