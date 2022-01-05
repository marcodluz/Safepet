package com.marcoluz.safepet.model;

public class Weight {

  private int id;
  private int account_id;
  private int pet_id;
  private String weight;
  private String date;

  public Weight(
    int id,
    int account_id,
    int pet_id,
    String weight,
    String date
  ) {
    this.id = id;
    this.account_id = account_id;
    this.pet_id = pet_id;
    this.weight = weight;
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

  public String getWeight() {
    return weight;
  }

  public String getDate() {
    return date;
  }
}
