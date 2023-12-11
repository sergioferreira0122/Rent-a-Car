package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Services.FuncionarioService;
import aluguer.veiculos.aluguerveiculos.Transport.FuncionarioModelTransport;
import aluguer.veiculos.aluguerveiculos.Utils.AlertMaker;
import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import aluguer.veiculos.aluguerveiculos.Utils.PasswordHash;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    public Button dragBTN;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button closeBTN;
    @FXML
    private Button minimizeBTN;
    @FXML
    private Button homepageBTN;
    @FXML
    private Button pedidoBTN;
    @FXML
    private Button adminBTN;
    @FXML
    private Button settingsBTN;
    @FXML
    private Button logoutBTN;
    @FXML
    private Button gestaoBTN;
    @FXML
    private Button gestaoVeiculoBTN;
    @FXML
    private Button atualizarButton;
    @FXML
    private PasswordField passwordHiddenLabel;
    @FXML
    private TextField passwordShownLabel;
    @FXML
    private CheckBox passwordCB;

    //LABELS

    @FXML
    private TextField emailLabel;
    @FXML
    private TextField nomeLabel;
    @FXML
    private Label nomeLabel1;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private FuncionarioService funcionarioService;

    public void initialize() {
        funcionarioService = FuncionarioService.getInstance();
        funcionarioModelTransport = FuncionarioModelTransport.getInstance();
        funcionarioModel = funcionarioModelTransport.getFuncionarioModel();

        //load admin
        loadNomeFuncionario();

        //verificacoes
        loadCheckPermissoes();

        emailLabel.setText(funcionarioModelTransport.getFuncionarioModel().getEmail());
        nomeLabel.setText(funcionarioModelTransport.getFuncionarioModel().getNome());
    }

    private void loadCheckPermissoes() {
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_PEDIDOS)) {
            pedidoBTN.setDisable(true);
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_FUNCIONARIOS)) {
            adminBTN.setDisable(true);
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_VEICULOS)) {
            gestaoVeiculoBTN.setDisable(true);
        }
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
    public void dragHandle(MouseEvent mouseEvent) {
        dragBTN.getScene().getWindow().setX(mouseEvent.getScreenX() - xOffset);
        dragBTN.getScene().getWindow().setY(mouseEvent.getScreenY() - yOffset);
    }

    @FXML
    public void dragPress(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }

    @FXML
    public void homepageHandle(ActionEvent actionEvent) {
        Loader.loadWindow("dashboard-view.fxml");
        Stage stage = (Stage) homepageBTN.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void gestaoHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-geral-view.fxml");
        Stage stage = (Stage) gestaoBTN.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void gestaoVeiculoHandle(ActionEvent event) {
        Loader.loadWindow("gestao-veiculo-view.fxml");
        Stage stage = (Stage) gestaoVeiculoBTN.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void pedidoHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-pedido-view.fxml");
        Stage stage = (Stage) pedidoBTN.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void adminHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-funcionario-view.fxml");
        Stage stage = (Stage) adminBTN.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void settingsHandle(ActionEvent actionEvent) {
        Loader.loadWindow("settings-view.fxml");
        Stage stage = (Stage) settingsBTN.getScene().getWindow();
        stage.hide();
    }

    @FXML
    public void logoutHandle(ActionEvent mouseEvent) {
        funcionarioModel = null;
        Loader.loadWindow("login-view.fxml");
        Stage stage = (Stage) logoutBTN.getScene().getWindow();
        stage.hide();
    }

    //metodos

    @FXML
    void handleAtualizar(ActionEvent event) {
        FuncionarioModel funcionarioModel = funcionarioModelTransport.getFuncionarioModel();

        String nome = nomeLabel.getText();
        String email = emailLabel.getText();
        String password;
        if (passwordHiddenLabel.isVisible()) {
            password = passwordHiddenLabel.getText();
        } else if (passwordShownLabel.isVisible()) {
            password = passwordShownLabel.getText();
        } else {
            password = "";
        }

        Stage confirmarPassword = new Stage();
        confirmarPassword.setTitle("Confirmar password");

        TextField passwordTextField = new TextField();
        passwordTextField.setPromptText("Password");

        Button confirmButton = new Button("Confirmar");

        confirmButton.setOnAction(e -> {
            if (!PasswordHash.hashPassword(passwordTextField.getText()).equals(funcionarioModel.getPassword())) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Password errada");
                alert.showAndWait();
            } else {
                if (!nome.trim().isEmpty()) {
                    funcionarioModel.setNome(nome);
                }

                if (!email.trim().isEmpty()) {
                    funcionarioModel.setEmail(email);
                }

                if (!password.trim().isEmpty()) {
                    funcionarioModel.setPassword(password);
                }

                if (!funcionarioService.updateDados(funcionarioModel)) {
                    AlertMaker.informationAlert("Settings", "Atualizar dados", "Falha ao alterar dados!");
                } else {
                    AlertMaker.informationAlert("Settings", "Atualizar dados", "Dados alterados com sucesso!");

                }
            }
        });

        Text text = new Text("Insira a password da sua conta para confirmar alterações");

        VBox vbox = new VBox(text, passwordTextField, confirmButton);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 320, 125);

        confirmarPassword.setScene(scene);

        confirmarPassword.showAndWait();
    }

    @FXML
    void togglePasswordCB(ActionEvent event) {
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

    public void loadNomeFuncionario() {
        nomeLabel1.setText(funcionarioModel.getNome());
    }
}
