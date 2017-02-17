package com.spottechnician.jsonparsing15_02_2017;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by MyPC on 16-02-2017.
 */

public class getATMDetailsAsyncTask extends AsyncTask<String,Void, Boolean> {

    private boolean error;
    private ProgressDialog progressDialog;
    private Context mContext;
    private DatabaseHandler db;
    ArrayList<HashMap<String, String>> atmList;

    public getATMDetailsAsyncTask(Context mContext) {
        this.mContext = mContext;
    }

    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(mContext, "Validating",Toast.LENGTH_SHORT).show();
        progressDialog  = new ProgressDialog(mContext);
            /*Toast.makeText(MainActivity.this, "Json Data is " +
                    "downloading", Toast.LENGTH_LONG).show();*/


    }

    @Override
    protected Boolean doInBackground(String... params) {
        HttpHandler hh = new HttpHandler();
        String url = "http://www.cleartask.in/caudit_weblink/webservices/ValidateAndFetchUserATMData.aspx";
        String jsonStr = hh.makeServiceCall(url,params[0], params[1]);
        Log.v("Testing","params[0]: "+params[0]+ "params[1]"+params[1]);
        Log.e("Testing", "jsonStr" + jsonStr);

        if (jsonStr != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                atmList = new ArrayList<>();
                error = jsonObject.getBoolean("error");
                if(error) {
                    return true;
                    //Toast.makeText(mContext, "Invalid Login", Toast.LENGTH_SHORT).show();
                }
                else {

                    //Toast.makeText(mContext, "Successfull Login", Toast.LENGTH_SHORT).show();

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

                        db = new DatabaseHandler(mContext);

                        db.addATM(new ATM(atm_id, customer_name, bank_name));
                        Log.v("Testing","atmList"+atmList);
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

                    return false;
                }

            } catch (final JSONException e) {
                Log.e("Testing", "Json parsing error: " + e.getMessage());
                Toast.makeText(mContext,
                        "Json parsing error: " + e.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Log.e("Testing", "Couldn't get json from server.");
            Toast.makeText(mContext,
                    "Couldn't get json from server. Check LogCat for possible errors!",
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(result)
        {
            Toast.makeText(mContext, "Invalid Login", Toast.LENGTH_SHORT).show();

        }
        else {
            progressDialog.setMessage("Fetching results");
            progressDialog.show();
            Intent intent = new Intent(mContext,ATMList.class);
            mContext.startActivity(intent);
        }
    }
}
