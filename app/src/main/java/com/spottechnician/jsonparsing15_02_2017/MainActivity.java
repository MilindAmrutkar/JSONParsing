package com.spottechnician.jsonparsing15_02_2017;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private EditText userName, userPassword;
    private TextView tvIsConnected;
    boolean check;
    String uName,uPassword;

    DatabaseHandler db;

    ArrayList<HashMap<String, String>> atmList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db = new DatabaseHandler(this);

        tvIsConnected = (TextView) findViewById(R.id.txtViewCheckConnectivity);
        if(isConnected()) {
            tvIsConnected.setBackgroundColor(0xFF00CC00);
            tvIsConnected.setText("You are connected");
        }
        else
        {
            tvIsConnected.setText("You are NOT connected");
        }

        atmList = new ArrayList<>();



    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    public void onSubmit(View view) {
        userName= (EditText) findViewById(R.id.edtTxtUserName);
        userPassword = (EditText) findViewById(R.id.edtTxtUserPass);
        uName = userName.getText().toString();
        uPassword = userPassword.getText().toString();
//        Toast.makeText(this,"UserName: "+uName+" Password: "+uPassword,Toast.LENGTH_LONG).show();
        Log.v("Testing", "UserName: "+uName+" Password: "+uPassword);

        new getATMDetailsAsyncTask(this).execute(uName, uPassword);

    }
}
  /*  private class getATMDetailsAsyncTask extends AsyncTask<String, Void, Void> {

        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Validating");
            progressDialog.show();

            *//*Toast.makeText(MainActivity.this, "Json Data is " +
                    "downloading", Toast.LENGTH_LONG).show();*//*

        }

        @Override
        protected Void doInBackground(String... params) {
            HttpHandler hh = new HttpHandler();
            String url = "http://www.cleartask.in/caudit_weblink/webservices/ValidateAndFetchUserATMData.aspx";
            String jsonStr = hh.makeServiceCall(url,params[0], params[1]);
            Log.v("Testing","params[0]: "+params[0]+ "params[1]"+params[1]);
            Log.e("Testing", "jsonStr" + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObject = new JSONObject(jsonStr);

                    error = jsonObject.getBoolean("error");

                    if(error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this,"Invalid user",Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Successfull Login", Toast.LENGTH_LONG).show();
                            }
                        });

                        Log.v("Testing","error: "+error);

                        JSONArray atmArray = jsonObject.getJSONArray("user_atm_data");

                        Log.d("Insert: ", "Inserting...");
                        for (int i = 0; i < atmArray.length(); i++) {
                            JSONObject c = atmArray.getJSONObject(i);
                            String atm_id = c.getString("atm_id");
                            Log.v("Testing", "atm_id" + atm_id);
                            String customer_name = c.getString("customer_name");
                            Log.v("Testing", "customer_name" + customer_name);
                            String bank_name = c.getString("bank_name");
                            Log.v("Testing", "bank_name" + bank_name);

                            HashMap<String, String> atmdetail = new HashMap<>();
                            atmdetail.put("atm_id", atm_id);
                            atmdetail.put("customer_name", customer_name);
                            atmdetail.put("bank_name", bank_name);


                            db.addATM(new ATM(atm_id, customer_name, bank_name));

                            atmList.add(atmdetail);

                        }

                        ArrayList<ATM> getAllAtmList = db.getAllSiteDetails();

                        for(ATM atm : getAllAtmList) {
                            String log = "Site Id: "+atm.getSite_id()
                                    +", ATM Id: "+atm.getAtm_id()
                                    +", Customer Name: "+atm.getCustomer_name()
                                    +", Bank Name: "+atm.getBank_name()
                                    +", Image URL1: "+atm.getUrl_1()
                                    +", Image URL2: "+atm.getUrl_2();
                            Log.d("ATMList: ", log);
                        }


                    }

                } catch (final JSONException e) {
                    Log.e("Testing", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e("Testing", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressDialog.setMessage("Taking you to Home page..");
            Intent intent = new Intent(MainActivity.this, ATMList.class);
            startActivity(intent);

        }


    }
*/

