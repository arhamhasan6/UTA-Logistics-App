package com.hsn.syedtaha.utalogistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Authenticator;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class deposit extends AppCompatActivity {
    EditText amount;
    Button enter;
    ProgressBar progressBar;
    String username,EWalletID,Amount,full,dateTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        amount = findViewById(R.id.amount);
        enter = findViewById(R.id.enter);
        progressBar = findViewById(R.id.progress);

        Bundle bundle = getIntent().getExtras();
        full = bundle.getString("full");

        String[] separated = full.split(":");
        EWalletID = separated[0];
        username = separated[1];

        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar;
                SimpleDateFormat simpleDateFormat;
                Long dateValueInLong = System.currentTimeMillis();
                calendar = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateTime = simpleDateFormat.format(calendar.getTime()).toString();


                Amount = String.valueOf(amount.getText());
                // url to post our data
                if (!Amount.equals("")){
                    String url = "http://192.168.100.20/utalogistics/deposit.php";

                    // creating a new variable for our request queue
                    RequestQueue queue = Volley.newRequestQueue(deposit.this);

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(deposit.this, response.toString(), Toast.LENGTH_SHORT).show();
                            if (response.toString().equals("Amount Deposited")){
                                Intent intent = new Intent(getApplicationContext(), Wallet.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.
                            Toast.makeText(deposit.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            // as we are passing data in the form of url encoded
                            // so we are passing the content type below
                            return "application/x-www-form-urlencoded; charset=UTF-8";
                        }

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            // below line we are creating a map for storing our values in key and value pair.
                            Map<String, String> params = new HashMap<>();

                            // on below line we are passing our key and value pair to our parameters.
                            params.put("EWalletID",EWalletID);
                            params.put("Amount",Amount);
                            params.put("LastDOT",dateTime);
                            // at last we are returning our params.
                            return params;
                        }
                    };
                    Volley.newRequestQueue(deposit.this).add(request);


                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Enter Amount!", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, Wallet.class);
                intent.putExtra("username", username);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void back_wallet(View view) {
        Intent intent = new Intent(getApplicationContext(), Wallet.class);
        intent.putExtra("username", username);
        startActivity(intent);
        finish();
    }


}