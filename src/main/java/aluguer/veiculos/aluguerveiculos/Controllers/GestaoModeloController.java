package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.ModeloModel;
import aluguer.veiculos.aluguerveiculos.Services.ModeloService;
import aluguer.veiculos.aluguerveiculos.Transport.FuncionarioModelTransport;
import aluguer.veiculos.aluguerveiculos.Utils.AlertMaker;
import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import javafx.beans.property.SimpleStringProperty;
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

public class GestaoModeloController {
    @FXML
    public Button dragBTN;
    ObservableList<ModeloModel> modeloModelObservableList = FXCollections.observableArrayList();
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

    private String EditId;

    @FXML
    private TextField id_marca_label;
    @FXML
    private TextField nome_label;
    @FXML
    private TextField pesquisar_label;
    @FXML
    private TableView<ModeloModel> tabela;
    @FXML
    private TableColumn<ModeloModel, String> id_marca_coluna;
    @FXML
    private TableColumn<ModeloModel, String> id_modelo_coluna;
    @FXML
    private TableColumn<ModeloModel, String> nome_coluna;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private ModeloService modeloService;

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
        modeloService = ModeloService.getInstance();

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

        String nome = nome_label.getText();
        String id_marca = id_marca_label.getText();

        if (modeloService.adicionarModelo(Integer.parseInt(id_marca), nome)) {
            AlertMaker.informationAlert(null, "Sucesso", "Modelo adicionado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o modelo!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String nome = nome_label.getText();
        String id_marca = id_marca_label.getText();

        if (modeloService.atualizarModelo(Integer.parseInt(EditId), Integer.parseInt(id_marca), nome)) {
            AlertMaker.informationAlert(null, "Sucesso", "Modelo atualizado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o modelo!");
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (modeloService.eliminarModelo(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Modelo", "Modelo eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar Modelo");
        }
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    void loadDados(MouseEvent event) {
        ModeloModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_modelo());
            nome_label.setText(row.getModelo());
            id_marca_label.setText(String.valueOf(row.getMarcaModel().getId_marca()));

            eliminar_button.setDisable(false);
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_modelo = pesquisar_label.getText();

        if (id_modelo.isEmpty()) {
            return;
        }

        List<ModeloModel> modelo = new ArrayList<>(modeloService.pesquisarModeloID(Integer.parseInt(id_modelo)));

        if (modelo.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Modelo nao encontrado");
            return;
        }

        modeloModelObservableList.setAll(modelo);

        id_marca_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMarcaModel().getId_marca())));
        nome_coluna.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        id_modelo_coluna.setCellValueFactory(new PropertyValueFactory<>("id_modelo"));

        tabela.getItems().setAll(modeloModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (nome_label.getText().isEmpty()) {
            nome_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            nome_label.setStyle(null);
        }
        if (id_marca_label.getText().isEmpty()) {
            id_marca_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_marca_label.setStyle(null);
        }
        return flag;
    }

    public void initTable() {
        modeloModelObservableList.setAll(modeloService.listaModelos());

        id_marca_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMarcaModel().getId_marca())));
        nome_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getModelo())));
        id_modelo_coluna.setCellValueFactory(new PropertyValueFactory<>("id_modelo"));

        tabela.getItems().setAll(modeloModelObservableList);
    }

    private void limpar() {
        EditId = null;
        nome_label.clear();
        id_marca_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }
}
