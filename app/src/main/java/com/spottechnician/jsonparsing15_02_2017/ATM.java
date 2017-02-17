package com.spottechnician.jsonparsing15_02_2017;

/**
 * Created by MyPC on 16-02-2017.
 */

public class ATM {
    private String site_id="";
    private String bank_name="";
    private String atm_id="";
    private String customer_name="";
    private String url_1="";
    private String url_2="";

    public ATM() {

    }

    public String getUrl_1() {
        return url_1;
    }

    public void setUrl_1(String url_1) {
        this.url_1 = url_1;
    }

    public String getUrl_2() {
        return url_2;
    }

    public void setUrl_2(String url_2) {
        this.url_2 = url_2;
    }



    public ATM(String atm_id, String bank_name, String customer_name) {
        this.bank_name = bank_name;
        this.atm_id = atm_id;
        this.customer_name = customer_name;
    }

    public String getSite_id() {
        return site_id;
    }

    public void setSite_id(String site_id) {
        this.site_id = site_id;
    }

    public String getBank_name() {

        return bank_name; //Hello there
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getAtm_id() {
        return atm_id;
    }

    public void setAtm_id(String atm_id) {
        this.atm_id = atm_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
