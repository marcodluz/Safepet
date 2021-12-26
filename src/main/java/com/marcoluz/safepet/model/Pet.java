package com.marcoluz.safepet.model;

public class Pet {
    private int id;
    private int account_id;
    private String name;
    private String specie;
    private String coat_colour;
    private String dob;
    private String notes;

    public Pet(int id, int account_id, String name, String specie, String coat_colour, String dob, String notes) {
        this.id = id;
        this.account_id = account_id;
        this.name = name;
        this.specie = specie;
        this.coat_colour = coat_colour;
        this.dob = dob;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getName() {
        return name;
    }

    public String getSpecie() {
        return specie;
    }

    public String getCoat_colour() {
        return coat_colour;
    }

    public String getDob() {
        return dob;
    }

    public String getNotes() {
        return notes;
    }

}
