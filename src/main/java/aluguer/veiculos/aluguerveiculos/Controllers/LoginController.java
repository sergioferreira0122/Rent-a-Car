package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Preferences.Preferences;
import aluguer.veiculos.aluguerveiculos.Services.FuncionarioService;
import aluguer.veiculos.aluguerveiculos.Transport.FuncionarioModelTransport;
import aluguer.veiculos.aluguerveiculos.Utils.AlertMaker;
import aluguer.veiculos.aluguerveiculos.Utils.Json;
import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import aluguer.veiculos.aluguerveiculos.Utils.PasswordHash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    public Button dragBTN;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private CheckBox guardarCredenciaisCheckbox;
    @FXML
    private Button closeBTN;
    @FXML
    private Button minimizeBTN;
    @FXML
    private Button loginButton;
    @FXML
    private PasswordField passwordHiddenLabel;
    @FXML
    private TextField passwordShownLabel;
    @FXML
    private TextField emailLabel;
    @FXML
    private CheckBox passwordCB;

    private FuncionarioService funcionarioService;
    private FuncionarioModelTransport funcionarioModelTransport;
    private Preferences preferences;
    private int tentativas;


    public void initialize() {
        preferences = Json.readPreferences();

        if (preferences.isGuardar_credenciais()) {
            passwordHiddenLabel.setText(preferences.getPassword());
            emailLabel.setText(preferences.getEmail());
            guardarCredenciaisCheckbox.fire();
        }
    }

    @FXML
    public void dragPress(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }

    @FXML
    public void dragHandle(MouseEvent mouseEvent) {
        dragBTN.getScene().getWindow().setX(mouseEvent.getScreenX() - xOffset);
        dragBTN.getScene().getWindow().setY(mouseEvent.getScreenY() - yOffset);
    }

    @FXML
    public void closeHandle(ActionEvent event) {
        closeBTN.getScene().getWindow().hide();
    }

    @FXML
    public void minimizeHandle(ActionEvent event) {
        ((Stage) minimizeBTN.getScene().getWindow()).setIconified(true);
    }


    @FXML
    void handleGuardarCredencias(ActionEvent event) {
        String email = emailLabel.getText();

        String password = "";
        if (passwordHiddenLabel.isVisible()) {
            password = passwordHiddenLabel.getText();
        } else if (passwordShownLabel.isVisible()) {
            password = passwordShownLabel.getText();
        }

        preferences.setGuardar_credenciais(false);
        preferences.setEmail(null);
        preferences.setPassword(null);

        if (guardarCredenciaisCheckbox.selectedProperty().getValue()) {
            preferences.setGuardar_credenciais(true);
            preferences.setEmail(email);
            preferences.setPassword(password);
        }

        Json.savePreferences(preferences);
    }

    @FXML
    void loginHandle(ActionEvent event) {
        funcionarioService = FuncionarioService.getInstance();

        String email = emailLabel.getText();

        String password = "";
        if (passwordHiddenLabel.isVisible()) {
            password = passwordHiddenLabel.getText();
        } else if (passwordShownLabel.isVisible()) {
            password = passwordShownLabel.getText();
        }

        if (guardarCredenciaisCheckbox.selectedProperty().getValue()) {
            preferences.setGuardar_credenciais(true);
            preferences.setEmail(email);
            preferences.setPassword(password);

            Json.savePreferences(preferences);
        }

        password = PasswordHash.hashPassword(password);

        if (tentativas == 2) {
            AlertMaker.errorAlert("Login", "Erro no Login", "Excedeu as 3 tentativas, fechando programa...");
            System.exit(0);
        }

        FuncionarioModel funcionarioModel = funcionarioService.login(email, password);

        if (funcionarioModel == null) {
            AlertMaker.errorAlert("Login", "Erro no Login", "Credenciais erradas");
            tentativas++;
        } else {
            funcionarioModelTransport = FuncionarioModelTransport.getInstance();
            funcionarioModelTransport.saveFuncionarioModel(funcionarioModel);

            Loader.loadWindow("dashboard-view.fxml");
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.hide();
        }
    }

    public void togglePasswordCB(ActionEvent actionEvent) {
        if (passwordCB.isSelected()) {
            passwordShownLabel.setText(passwordHiddenLabel.getText());
            passwordShownLabel.setVisible(true);
            passwordHiddenLabel.setVisible(false);
            return;
        }
        passwordHiddenLabel.setText(passwordShownLabel.getText());
        passwordHiddenLabel.setVisible(true);
        passwordShownLabel.setVisible(false);
    }
}