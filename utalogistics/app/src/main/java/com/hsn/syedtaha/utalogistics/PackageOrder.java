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

public class PackageOrder extends AppCompatActivity {
    EditText name, email, address, cnic, city, contact, weight, amount, perishable_item, no_goods;
    Button submit;
    ProgressBar progressBar;
    String userid, username, Name, Email, Address, Cnic, City, Contact, Weight, Amount, Perishable, No_goods, Order_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package);

        name = findViewById(R.id.r_name);
        email = findViewById(R.id.r_email);
        address = findViewById(R.id.r_cnic);
        cnic = findViewById(R.id.r_city);
        city = findViewById(R.id.r_address);
        contact = findViewById(R.id.r_contact);
        weight = findViewById(R.id.weight);
        amount = findViewById(R.id.amount);
        perishable_item = findViewById(R.id.perishable_item);
        no_goods = findViewById(R.id.goods);
        submit = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        int min = 1999999999;
        int max = 1000000000;
        int packageid = (int) (Math.random() * (max - min + 1) + min);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        userid= bundle.getString("userid");



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://192.168.100.20/utalogistics/package.php";

                Name = String.valueOf(name.getText());
                Email = String.valueOf(email.getText());
                Address = String.valueOf(address.getText());
                Cnic = String.valueOf(cnic.getText());
                City = String.valueOf(city.getText());
                Contact = String.valueOf(contact.getText());
                Weight = String.valueOf(weight.getText());
                Amount = String.valueOf(amount.getText());
                Perishable = String.valueOf(perishable_item.getText());
                No_goods = String.valueOf(no_goods.getText());
                Order_id = String.valueOf(packageid);
                if (!name.equals("") && !Email.equals("") && !Address.equals("") && !Cnic.equals("") && !City.equals("") && !Contact.equals("") && !Weight.equals("") && !Amount.equals("") && !Perishable.equals("") && !No_goods.equals("") && !Order_id.equals("") && !userid.equals("")){
                    RequestQueue queue = Volley.newRequestQueue(PackageOrder.this);

                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            if (response.toString().equals("Order submitted")) {
                                Toast.makeText(PackageOrder.this, response.toString()+"successfully your id is "+Order_id, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.putExtra("username", username);
                                startActivity(intent);
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.
                            Toast.makeText(PackageOrder.this, "Fail to get course" + error, Toast.LENGTH_SHORT).show();
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
                            params.put("order_id", Order_id);
                            params.put("name", Name);
                            params.put("email", Email);
                            params.put("address", Address);
                            params.put("cnic", Cnic);
                            params.put("city", City);
                            params.put("contact", Contact);
                            params.put("weight", Weight);
                            params.put("amount", Amount);
                            params.put("perishable_item", Perishable);
                            params.put("no_goods", No_goods);
                            params.put("userid", userid);

                            // at last we are returning our params.
                            return params;
                        }
                    };
                    Volley.newRequestQueue(PackageOrder.this).add(request);

                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }





            }
        });


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


}