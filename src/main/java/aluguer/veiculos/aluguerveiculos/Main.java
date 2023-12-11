package aluguer.veiculos.aluguerveiculos;

import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("splash-screen.fxml"));
        Scene splashScene = new Scene(fxmlloader.load());
        stage.setScene(splashScene);
        stage.getIcons().add(new Image("icon.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Rent a Car");
        stage.setResizable(false);
        stage.show();

        PauseTransition splashPause = new PauseTransition(Duration.seconds(5));
        splashPause.setOnFinished(event -> {
            stage.hide();
            Loader.loadWindow("login-view.fxml");
        });
        splashPause.play();
    }
}