package com.marcoluz.safepet.dao;

import com.marcoluz.safepet.controller.MainController;
import com.marcoluz.safepet.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PetDAO {

    public static void insertPetDetails(String pStatement, String[] values){
        try{
            PreparedStatement prepStat = DBUtil.db.prepareStatement(pStatement);
            prepStat.setInt(1, generatePetId());
            prepStat.setInt(2, MainController.id);
            prepStat.setString(3,values[0]);
            prepStat.setString(4,values[1]);
            prepStat.setString(5,values[2]);
            prepStat.setString(6,values[3]);
            prepStat.setString(7,values[4]);
            prepStat.executeUpdate();

        }catch (Exception e){
            System.out.println("SQL Error!");
            System.out.println(e);
        }
    }

    public static int generatePetId() {
        return DBUtil.getNewId("pet");
    }
}
