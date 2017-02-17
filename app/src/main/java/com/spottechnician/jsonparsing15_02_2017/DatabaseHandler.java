package com.spottechnician.jsonparsing15_02_2017;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by MyPC on 16-02-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "atmManager";

    private static final String TABLE_SITE = "site";

    private static final String KEY_SITE_ID = "site_id";
    private static final String KEY_ATM_ID = "atm_id";
    private static final String KEY_CUST_NAME = "cust_name";
    private static final String KEY_BANK_NAME = "bank_name";
    private static final String KEY_URL_1 = "url1";
    private static final String KEY_URL_2 = "url2";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SITE_DETAILS_TABLE = "CREATE TABLE "+ TABLE_SITE + "("
                +KEY_SITE_ID+" INTEGER PRIMARY KEY, "
                +KEY_ATM_ID+" TEXT, "
                +KEY_CUST_NAME+" TEXT, "
                +KEY_BANK_NAME+" TEXT, "
                +KEY_URL_1+" TEXT, "
                +KEY_URL_2+" TEXT"
                +")";
        db.execSQL(CREATE_SITE_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SITE);
    }

    public void addATM(ATM atm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ATM_ID, atm.getAtm_id());
        values.put(KEY_CUST_NAME, atm.getCustomer_name());
        values.put(KEY_BANK_NAME, atm.getBank_name());

        db.insert(TABLE_SITE, null, values);
        db.close();
    }

    public ArrayList<ATM> getAllSiteDetails() {
        ArrayList<ATM> contactList = new ArrayList<ATM>();

        String selectQuery = "SELECT * FROM " + TABLE_SITE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                ATM atmList= new ATM();
                atmList.setSite_id(cursor.getString(0));
                atmList.setAtm_id(cursor.getString(1));
                atmList.setCustomer_name(cursor.getString(2));
                atmList.setBank_name(cursor.getString(3));
                atmList.setUrl_1(cursor.getString(4));
                atmList.setUrl_2(cursor.getString(5));

                contactList.add(atmList);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


}
