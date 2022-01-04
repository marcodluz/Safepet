package com.marcoluz.safepet.model;

public class Appointment {
    private int id;
    private int account_id;
    private int pet_id;
    private String type;
    private String status;
    private String date;

    public Appointment(int id, int account_id, int pet_id, String type, String status, String date) {
        this.id = id;
        this.account_id = account_id;
        this.pet_id = pet_id;
        this.type = type;
        this.status = status;
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

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

}
