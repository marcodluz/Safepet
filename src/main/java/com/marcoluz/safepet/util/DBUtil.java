package com.marcoluz.safepet.util;

import java.sql.*;

public class DBUtil {

  // Variables
  public static Connection db;
  private static final String dbUrl =
    "jdbc:postgresql://localhost:5432/postgres";

  /////////////////////////////////////////////
  /////// CHANGE BEFORE USING THE APP /////////

  private static String dbUsername = "postgres";
  private static String dbPassword = "lol2636";

  /////////////////////////////////////////////
  /////////////////////////////////////////////

  public static boolean getConneted() {
    try {
      // Check the credentials
      db = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
      System.out.println("Database connection established successfully!");
      return true;
    } catch (Exception e) {
      System.out.println("Database connection failed!");
      System.out.println(
        "Wrong Database Credentials! Change it on DBUtil.java"
      );
      return false;
    }
  }

  public static Connection connect() throws SQLException {
    return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
  }

  public static void sqlUpdate(String statement) {
    try {
      Statement sqlStat = db.createStatement();
      sqlStat.executeUpdate(statement);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static ResultSet sqlExecute(String statement) {
    try {
      Statement sqlStat = db.createStatement();
      ResultSet rs = sqlStat.executeQuery(statement);

      return rs;
    } catch (Exception e) {
      System.out.println(e);
    }
    return null;
  }

  public static int getNewId(String table) throws SQLException {
    String SQL = "SELECT id FROM " + table + " ORDER BY id DESC LIMIT 1";
    ResultSet rs = DBUtil.sqlExecute(SQL);

    if (rs.next()) {
      return (rs.getInt("id") + 1);
    } else {
      return 0;
    }
  }
}
