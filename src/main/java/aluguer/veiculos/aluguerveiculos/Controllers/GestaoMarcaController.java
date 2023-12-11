package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;
import aluguer.veiculos.aluguerveiculos.Services.MarcaService;
import aluguer.veiculos.aluguerveiculos.Transport.FuncionarioModelTransport;
import aluguer.veiculos.aluguerveiculos.Utils.AlertMaker;
import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GestaoMarcaController {
    @FXML
    public Button dragBTN;
    ObservableList<MarcaModel> marcaModelObservableList = FXCollections.observableArrayList();
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
    private Button eliminar_button;

    //LABELS

    @FXML
    private Label nomeLabel;
    @FXML
    private TextField marca_label;
    @FXML
    private TextField pesquisar_label;

    @FXML
    private TableColumn<MarcaModel, String> id_marca_coluna;
    @FXML
    private TableColumn<MarcaModel, String> marca_coluna;
    @FXML
    private TableView<MarcaModel> tabela;

    private String EditId;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private MarcaService marcaService;

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

    public void loadNomeFuncionario() {
        nomeLabel.setText(funcionarioModel.getNome());
    }

    //metodos

    public void initialize() {
        marcaService = MarcaService.getInstance();

        //admin
        funcionarioModelTransport = FuncionarioModelTransport.getInstance();
        funcionarioModel = funcionarioModelTransport.getFuncionarioModel();

        //load admin
        loadNomeFuncionario();

        //verificacoes
        loadCheckPermissoes();

        initTable();
        limpar();
    }

    @FXML
    void adicionarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String marca = marca_label.getText();

        if (marcaService.adicionarMarca(marca)) {
            AlertMaker.informationAlert(null, "Sucesso", "Marca adicionada com sucesso!");
            initTable();
        } else {
            AlertMaker.informationAlert(null, "Erro", "Erro ao adicionar a marca!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String marca = marca_label.getText();

        if (marcaService.atualizarMarca(Integer.parseInt(EditId), marca)) {
            AlertMaker.informationAlert(null, "Sucesso", "Marca atualizada com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar a marca!");
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (marcaService.eliminarMarca(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Marca", "Marca eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar marca");
        }
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    void loadDados(MouseEvent event) {
        MarcaModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_marca());
            marca_label.setText(row.getMarca());

            eliminar_button.setDisable(false);
        }
    }

    @FXML
    void pesquisaHandle(ActionEvent event) {
        String id_marca = pesquisar_label.getText();

        if (id_marca.isEmpty()) {
            return;
        }

        List<MarcaModel> marca = new ArrayList<>(marcaService.pesquisarMarcaID(Integer.parseInt(id_marca)));

        if (marca.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Marca nao encontrado");
            return;
        }

        marcaModelObservableList.setAll(marca);

        id_marca_coluna.setCellValueFactory(new PropertyValueFactory<>("id_marca"));
        marca_coluna.setCellValueFactory(new PropertyValueFactory<>("marca"));

        tabela.getItems().setAll(marcaModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (marca_label.getText().isEmpty()) {
            marca_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            marca_label.setStyle(null);
        }
        return flag;
    }

    public void initTable() {
        marcaModelObservableList.setAll(marcaService.listaMarcas());

        id_marca_coluna.setCellValueFactory(new PropertyValueFactory<>("id_marca"));
        marca_coluna.setCellValueFactory(new PropertyValueFactory<>("marca"));

        tabela.getItems().setAll(marcaModelObservableList);
    }

    private void limpar() {
        EditId = null;
        marca_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }
}
