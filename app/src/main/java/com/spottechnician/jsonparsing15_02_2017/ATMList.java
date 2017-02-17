package com.spottechnician.jsonparsing15_02_2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class ATMList extends AppCompatActivity {

    private ListView listView;
    ArrayList<HashMap<String, String>> atmList;
    ArrayList<ATM> getATMList;
    ATM atm;
    String atm_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atmlist);

        atmList = new ArrayList<>();
        getATMList = new ArrayList<>();

        atm = new ATM();

        DatabaseHandler db = new DatabaseHandler(this);
        getATMList=db.getAllSiteDetails();



        Log.v("ATMList","value1"+atm_id);

        for(int i=0; i<getATMList.size();i++) {
            HashMap<String, String> atmdetail = new HashMap<>();
            atmdetail.put("atm_id", getATMList.get(i).getAtm_id());
            Log.v("ATMList","ATM id: "+atm.getAtm_id());
            atmdetail.put("customer_name", getATMList.get(i).getCustomer_name());
            Log.v("ATMList","Customer Name: "+atm.getCustomer_name());
            atmdetail.put("bank_name", getATMList.get(i).getBank_name());
            Log.v("ATMList","Bank Name: "+atm.getBank_name());
            atmList.add(atmdetail);
        }

        listView = (ListView) findViewById(R.id.main_list);
        ListAdapter adapter = new SimpleAdapter(ATMList.this, atmList, R.layout.single_list_item,
                new String[]{"atm_id", "customer_name", "bank_name"},
                new int[]{R.id.txtViewSingleListItem_atm_id,
                        R.id.txtViewSingleListItem_bank_name,
                        R.id.txtViewSingleListItem_customer_name});
        listView.setAdapter(adapter);

    }


}
