package aluguer.veiculos.aluguerveiculos.Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Loader {
    public static void loadWindow(String location) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Loader.class.getResource("/aluguer/veiculos/aluguerveiculos/" + location));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Rent a Car");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image("icon.png"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
