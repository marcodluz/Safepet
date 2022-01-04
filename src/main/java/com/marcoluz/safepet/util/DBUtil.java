package com.marcoluz.safepet.util;

import java.sql.*;

public class DBUtil {

    public static Connection db;
    private static final String dbUrl  = "jdbc:postgresql://localhost:5432/postgres";

    public static boolean getConneted(){
        try {
            db = DriverManager.getConnection(dbUrl, "postgres", "lol2636");
            System.out.println("Database connection established successfully!");
            return true;
        }catch (Exception e){
            System.out.println("Database connection failed!");
            return false;
        }
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(dbUrl, "postgres", "lol2636");
    }

    public  static void sqlUpdate(String statement){
        try{
            Statement sqlStat = db.createStatement();
            sqlStat.executeUpdate(statement);
            System.out.println("SQL statement updated successfully!");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static ResultSet sqlExecute(String statement){
        try {
            Statement sqlStat = db.createStatement();
            ResultSet rs = sqlStat.executeQuery(statement);
            System.out.println("SQL statement executed successfully!");
            return rs;

        }catch (Exception e){
            System.out.println("SQL Error!");
        }
        return null;

    }

    public static void prepupdate(String pStatement, String[] values){
        try{
            PreparedStatement prepStat = db.prepareStatement(pStatement);
            prepStat.setInt(1, Integer.parseInt(values[2]));
            prepStat.setString(2,values[1]);
            prepStat.setString(3,values[0]);

            prepStat.setDate(4,Date.valueOf(values[3]));
            prepStat.executeUpdate();
            System.out.println("Entry Updated... ");

        }catch (Exception e){
            System.out.println("SQL Error...");
            System.out.println(e);
        }
    }

    public static void disconnect(){
        try {
            db.close();
            System.out.println("Database disconnected successfully!");
        }catch (Exception e){
            System.out.println("Database disconnected unsuccessfully!");
        }

    }

    public static int getNewId(String table) {
        String SQL = "SELECT id FROM "+ table +" ORDER BY id DESC LIMIT 1";

        try (Connection conn = connect();
             PreparedStatement preStat = conn.prepareStatement(SQL))
        {
            ResultSet result = preStat.executeQuery();

            if(result.next()) {
                return (result.getInt("id") + 1);
            }
            else {
                return 0;
            }


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return -1;
        }
    }


}