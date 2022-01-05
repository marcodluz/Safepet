package com.marcoluz.safepet.dao;

import com.marcoluz.safepet.controller.MainController;
import com.marcoluz.safepet.controller.MedWeiController;
import com.marcoluz.safepet.util.DBUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

public class MedWeiDAO {
    public static void insertMedicationDetails(String pStatement, String[] values){
        // Current date variable
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

        try{
            PreparedStatement prepStat = DBUtil.db.prepareStatement(pStatement);
            prepStat.setInt(1, generateMedicationId());
            prepStat.setInt(2, MainController.id);
            prepStat.setInt(3, MedWeiController.petId1);
            prepStat.setString(4,values[0]);
            prepStat.setString(5, String.valueOf(localDate));
            prepStat.executeUpdate();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static void insertWeightDetails(String pStatement, String[] values){
        // Current date variable
        LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

        try{
            PreparedStatement prepStat = DBUtil.db.prepareStatement(pStatement);
            prepStat.setInt(1, generateWeightId());
            prepStat.setInt(2, MainController.id);
            prepStat.setInt(3, MedWeiController.petId2);
            prepStat.setString(4,values[0]);
            prepStat.setString(5, String.valueOf(localDate));
            prepStat.executeUpdate();

        } catch (Exception e){
            System.out.println(e);
        }
    }

    public static int generateMedicationId() throws SQLException {
        return DBUtil.getNewId("medication");
    }

    public static int generateWeightId() throws SQLException {
        return DBUtil.getNewId("weight");
    }
}
