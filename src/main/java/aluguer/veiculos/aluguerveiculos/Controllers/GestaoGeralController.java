package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Transport.FuncionarioModelTransport;
import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class GestaoGeralController {

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
    private Button gestaoBTN;
    @FXML
    private Button gestaoVeiculoBTN;
    @FXML
    private Button pedidoBTN;
    @FXML
    private Button adminBTN;
    @FXML
    private Button settingsBTN;
    @FXML
    private Button logoutBTN;
    @FXML
    private Button aluguerBTN;
    @FXML
    private Button clienteBTN;
    @FXML
    private Button condutorBTN;
    @FXML
    private Button faturaBTN;
    @FXML
    private Button marcaBTN;
    @FXML
    private Button modeloBTN;
    @FXML
    private Button reciboBTN;
    @FXML
    private Button servicoBTN;
    @FXML
    private Button reparacaoBTN;

    //LABELS

    @FXML
    private Label nomeLabel;
    @FXML
    private Label aluguer_label;
    @FXML
    private Label cliente_label;
    @FXML
    private Label condutor_label;
    @FXML
    private Label fatura_label;
    @FXML
    private Label marca_label;
    @FXML
    private Label modelo_label;
    @FXML
    private Label recibo_label;
    @FXML
    private Label servico_label;
    @FXML
    private Label reparacao_label;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;

    public void initialize() {
        //admin
        funcionarioModelTransport = FuncionarioModelTransport.getInstance();
        funcionarioModel = funcionarioModelTransport.getFuncionarioModel();

        //load admin
        loadNomeFuncionario();

        //verificacoes
        loadCheckPermissoes();
    }

    private void loadCheckPermissoes() {
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_ALUGUERS)) {
            aluguerBTN.setDisable(true);
            aluguer_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_CLIENTES)) {
            clienteBTN.setDisable(true);
            cliente_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_MODELOS)) {
            modeloBTN.setDisable(true);
            modelo_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_MARCAS)) {
            marcaBTN.setDisable(true);
            marca_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_PEDIDOS)) {
            pedidoBTN.setDisable(true);
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_REPARACOES)) {
            reparacaoBTN.setDisable(true);
            reparacao_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_SERVICOS)) {
            servicoBTN.setDisable(true);
            servico_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_VEICULOS)) {
            gestaoVeiculoBTN.setDisable(true);
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_RECIBOS)) {
            reciboBTN.setDisable(true);
            recibo_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_FATURAS)) {
            faturaBTN.setDisable(true);
            fatura_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_CONDUTORES)) {
            condutorBTN.setDisable(true);
            condutor_label.setText("Sem permissão");
        }
        if (!funcionarioModel.getPermissoes().contains(Permissoes.GESTAO_FUNCIONARIOS)) {
            adminBTN.setDisable(true);
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

    // widgets
    public void aluguerHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-aluguer-view.fxml");
        Stage stage = (Stage) aluguerBTN.getScene().getWindow();
        stage.hide();
    }

    public void clienteHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-cliente-view.fxml");
        Stage stage = (Stage) clienteBTN.getScene().getWindow();
        stage.hide();
    }

    public void condutorHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-condutor-view.fxml");
        Stage stage = (Stage) condutorBTN.getScene().getWindow();
        stage.hide();
    }

    public void faturaHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-fatura-view.fxml");
        Stage stage = (Stage) faturaBTN.getScene().getWindow();
        stage.hide();
    }

    public void marcaHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-marca-view.fxml");
        Stage stage = (Stage) marcaBTN.getScene().getWindow();
        stage.hide();
    }

    public void modeloHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-modelo-view.fxml");
        Stage stage = (Stage) modeloBTN.getScene().getWindow();
        stage.hide();
    }

    public void reciboHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-recibo-view.fxml");
        Stage stage = (Stage) reciboBTN.getScene().getWindow();
        stage.hide();
    }

    public void servicoHandle(ActionEvent actionEvent) {
        Loader.loadWindow("gestao-servico-view.fxml");
        Stage stage = (Stage) servicoBTN.getScene().getWindow();
        stage.hide();
    }

    public void reparacaoHandle(ActionEvent event) {
        Loader.loadWindow("gestao-reparacao-view.fxml");
        Stage stage = (Stage) reparacaoBTN.getScene().getWindow();
        stage.hide();
    }

    public void loadNomeFuncionario() {
        nomeLabel.setText(funcionarioModel.getNome());
    }
}
