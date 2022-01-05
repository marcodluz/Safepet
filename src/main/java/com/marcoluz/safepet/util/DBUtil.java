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

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static ResultSet sqlExecute(String statement){
        try {
            Statement sqlStat = db.createStatement();
            ResultSet rs = sqlStat.executeQuery(statement);

            return rs;
        }catch (Exception e){
            System.out.println("SQL Error!");
        }
        return null;

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