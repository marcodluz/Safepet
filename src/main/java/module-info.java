module com.marcoluz.safepet {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;

  opens com.marcoluz.safepet to javafx.fxml;
  exports com.marcoluz.safepet ;
  exports com.marcoluz.safepet.controller ;
  opens com.marcoluz.safepet.controller to javafx.fxml;
  exports com.marcoluz.safepet.util ;
  opens com.marcoluz.safepet.util to javafx.fxml;
  exports com.marcoluz.safepet.model ;
  opens com.marcoluz.safepet.model to javafx.fxml;
  exports com.marcoluz.safepet.dao ;
  opens com.marcoluz.safepet.dao to javafx.fxml;
}
