package com.hsn.syedtaha.utalogistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.Random;

public class SignUp extends AppCompatActivity {

    EditText editTextFname,editTextLname,editTextAddress,editTextCnicno,editTextDob,editTextCity,editTextMobileno,editTextUsername,editTextPassword;
    Button buttonSignup;
    TextView textViewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFname = findViewById(R.id.firstName);
        editTextLname = findViewById(R.id.lastName);
        editTextAddress = findViewById(R.id.address);
        editTextCnicno = findViewById(R.id.cnicno);
        editTextDob = findViewById(R.id.dob);
        editTextCity = findViewById(R.id.city);
        editTextMobileno = findViewById(R.id.contactno);
        editTextUsername = findViewById(R.id.usernameSignUp);
        editTextPassword = findViewById(R.id.passwordSignUp);
        buttonSignup = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

       /* Random rand = new Random();
        int max1= 1999999999;
        int WalletID = rand.nextInt(max1) + 2;*/

     /*   Random rand1 = new Random();
        int max= 1999999999;
        int userid1 = rand1.nextInt(max) + 1;
        System.out.print(userid1);*/

        int min = 1999999999;
        int max = 1000000000;
        int userid1 = (int)(Math.random() * (max - min + 1) + min);
        int WalletID = (int)(Math.random() * (max - min + 1) + min);

        int balanceamount= 0;
        int dueamount =0;
        int lastdot=0;

//        Login text link
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });


        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname,lname,address,cnicno,dob,city,contactno,username,password,EWalletID,userid,BalanceAmount,DueAmount,LastDOT;
                fname = String.valueOf(editTextFname.getText());
                lname = String.valueOf(editTextLname.getText());
                address = String.valueOf(editTextAddress.getText());
                cnicno = String.valueOf(editTextCnicno.getText());
                dob = String.valueOf(editTextDob.getText());
                city = String.valueOf(editTextCity.getText());
                contactno = String.valueOf(editTextMobileno.getText());
                username = String.valueOf(editTextUsername.getText());
                password = String.valueOf(editTextPassword.getText());
                EWalletID = String.valueOf(WalletID);
                userid = String.valueOf(userid1);
                BalanceAmount = String.valueOf(balanceamount);
                DueAmount = String.valueOf(dueamount);
                LastDOT = String.valueOf(lastdot);

                if (!userid.equals("") && !fname.equals("") && !lname.equals("") && !address.equals("") && !cnicno.equals("") && !dob.equals("") && !city.equals("") && !contactno.equals("") && !username.equals("") && !password.equals("")) {
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[10];
                            field[0] = "userid";
                            field[1] = "fname";
                            field[2] = "lname";
                            field[3] = "address";
                            field[4] = "cnicno";
                            field[5] = "dob";
                            field[6] = "city";
                            field[7] = "contactno";
                            field[8] = "username";
                            field[9] = "password";
                            //Creating array for data
                            String[] data = new String[10];
                            data[0] = userid;
                            data[1] = fname;
                            data[2] = lname;
                            data[3] = address;
                            data[4] = cnicno;
                            data[5] = dob;
                            data[6] = city;
                            data[7] = contactno;
                            data[8] = username;
                            data[9] = password;

                            String[] field1 = new String[4];
                            field1[0] = "EWalletID";
                            field1[1] = "BalanceAmount";
                            field1[2] = "DueAmount";
                            field1[3] = "LastDOT";

                            String[] data1 = new String[4];
                            data1[0] = EWalletID;
                            data1[1] = BalanceAmount;
                            data1[2] = DueAmount;
                            data1[3] = LastDOT;

                            String[] field2 = new String[2];
                            field2[0] = "userid";
                            field2[1] = "EWalletID";

                            String[] data2 = new String[2];
                            data2[0] = userid;
                            data2[1] = EWalletID;

                            PutData putData = new PutData("http://192.168.100.7/utalogistics/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                progressBar.setVisibility(View.GONE);
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Sign Up Success")) {
                                        PutData putData1 = new PutData("http://192.168.100.7/utalogistics/walletreg.php", "POST", field1, data1);
                                        if (putData1.startPut()){
                                            if (putData1.onComplete()){
                                                String result1 = putData1.getResult();
                                                if (result1.equals("wallet created")){
                                                    PutData putData2 = new PutData("http://192.168.100.7/utalogistics/wallet_reg.php", "POST", field2, data2);
                                                    if (putData2.startPut() && putData2.onComplete()){
                                                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                                                startActivity(intent);
                                                                finish();


                                                    }

                                                }
                                            }
                                        }
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                            //End Write and Read data with URL
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}