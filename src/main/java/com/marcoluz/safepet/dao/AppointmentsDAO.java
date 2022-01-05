package com.marcoluz.safepet.dao;

import com.marcoluz.safepet.controller.AddAppointmentController;
import com.marcoluz.safepet.controller.MainController;
import com.marcoluz.safepet.util.DBUtil;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AppointmentsDAO {

  public static void insertAppointmentDetails(
    String pStatement,
    String[] values
  ) {
    try {
      PreparedStatement prepStat = DBUtil.db.prepareStatement(pStatement);
      prepStat.setInt(1, generateAppointmentId());
      prepStat.setInt(2, MainController.id);
      prepStat.setInt(3, AddAppointmentController.petId);
      prepStat.setString(4, values[0]);
      prepStat.setString(5, "Active");
      prepStat.setString(6, values[1]);
      prepStat.executeUpdate();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static int generateAppointmentId() throws SQLException {
    return DBUtil.getNewId("appointment");
  }
}
