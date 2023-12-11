package aluguer.veiculos.aluguerveiculos.Utils;

import javafx.scene.control.Alert;

public class AlertMaker {

    public static void errorAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        //alert.initStyle(StageStyle.TRANSPARENT);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void informationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        //alert.initStyle(StageStyle.TRANSPARENT);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
