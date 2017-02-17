package com.spottechnician.jsonparsing15_02_2017;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void btnReportClicked() {

    }

    public void btnPhotoClicked() {
        Intent intent = new Intent(this,ATMList.class);
        startActivity(intent);
    }
}
