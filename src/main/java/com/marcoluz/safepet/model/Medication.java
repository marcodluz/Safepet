package com.marcoluz.safepet.model;

public class Medication {
    private int id;
    private int account_id;
    private int pet_id;
    private String med_name;
    private String date;

    public Medication(int id, int account_id, int pet_id, String med_name, String date) {
        this.id = id;
        this.account_id = account_id;
        this.pet_id = pet_id;
        this.med_name = med_name;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public int getPet_id() {
        return pet_id;
    }

    public String getMed_name() {
        return med_name;
    }

    public String getDate() {
        return date;
    }

}
