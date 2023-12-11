package aluguer.veiculos.aluguerveiculos;

import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Test extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Loader.class.getResource("/aluguer/veiculos/aluguerveiculos/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Rent a Car");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.getIcons().add(new Image("icon.png"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}