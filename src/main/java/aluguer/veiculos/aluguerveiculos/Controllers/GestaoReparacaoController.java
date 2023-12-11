package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.ReparacaoModel;
import aluguer.veiculos.aluguerveiculos.Services.ReparacaoService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestaoReparacaoController {
    ObservableList<ReparacaoModel> reparacaoModelObservableList = FXCollections.observableArrayList();
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button dragBTN;
    @FXML
    private Button closeBTN;
    @FXML
    private Button minimizeBTN;

    //BOTÕES DE MENU

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
    private Button eliminarButton;

    @FXML
    private TextArea descricao_label;

    @FXML
    private DatePicker data_datepicker;

    @FXML
    private Label nomeLabel;

    @FXML
    private TextField id_veiculo_label;
    @FXML
    private TextField preco_label;
    @FXML
    private TextField pesquisar_label;

    @FXML
    private TableColumn<ReparacaoModel, String> preco_coluna;
    @FXML
    private TableView<ReparacaoModel> tabela;
    @FXML
    private TableColumn<ReparacaoModel, String> data_coluna;
    @FXML
    private TableColumn<ReparacaoModel, String> descricao_coluna;
    @FXML
    private TableColumn<ReparacaoModel, String> id_reparacao_coluna;
    @FXML
    private TableColumn<ReparacaoModel, String> id_veiculo_coluna;

    private String EditId;

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private ReparacaoService reparacaoService;

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

    public void homepageHandle(ActionEvent actionEvent) {
        Loader.loadWindow("dashboard-view.fxml");
        Stage stage = (Stage) pedidoBTN.getScene().getWindow();
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

    //metodos

    public void initialize() {
        reparacaoService = ReparacaoService.getInstance();

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
    void limparHandle(ActionEvent event) {
        limpar();
    }


    @FXML
    void adicionarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        LocalDate data = data_datepicker.getValue();
        String preco = preco_label.getText();
        String id_veiculo = id_veiculo_label.getText();
        String descricao = descricao_label.getText();

        if (reparacaoService.adicionarReparacao(Integer.parseInt(id_veiculo), Double.parseDouble(preco), descricao, data)) {
            AlertMaker.informationAlert(null, "Sucesso", "Reparacao adicionada com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar a reparacao!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        LocalDate data = data_datepicker.getValue();
        String preco = preco_label.getText();
        String id_veiculo = id_veiculo_label.getText();
        String descricao = descricao_label.getText();

        if (reparacaoService.atualizarReparacao(Integer.parseInt(EditId), Integer.parseInt(id_veiculo), Double.parseDouble(preco), descricao, data)) {
            AlertMaker.informationAlert(null, "Sucesso", "Reparacao atualizada com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar a reparacao!");
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_reparacao = pesquisar_label.getText();

        if (id_reparacao.isEmpty()) {
            return;
        }

        List<ReparacaoModel> reparacao = new ArrayList<>(reparacaoService.pesquisarReparacaoID(Integer.parseInt(id_reparacao)));

        if (reparacao.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Reparacao nao encontrada");
            return;
        }

        reparacaoModelObservableList.setAll(reparacao);

        id_reparacao_coluna.setCellValueFactory(new PropertyValueFactory<>("id_reparacao"));
        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));
        id_veiculo_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getVeiculoModel().getId_veiculo())));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        descricao_coluna.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.getItems().setAll(reparacaoModelObservableList);
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (reparacaoService.eliminarModelo(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Recibo", "Reparacao eliminada com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar reparacao");
        }
    }

    @FXML
    void loadDados(MouseEvent event) {
        ReparacaoModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_reparacao());
            data_datepicker.setValue(row.getData());
            id_veiculo_label.setText(String.valueOf(row.getVeiculoModel().getId_veiculo()));
            preco_label.setText(String.valueOf(row.getPreco()));
            descricao_label.setText(row.getDescricao());

            eliminarButton.setDisable(false);
        }
    }

    private void limpar() {
        EditId = null;
        data_datepicker.setValue(null);
        id_veiculo_label.clear();
        pesquisar_label.clear();
        preco_label.clear();
        descricao_label.clear();
        eliminarButton.setDisable(true);
        initTable();
    }

    public void initTable() {
        reparacaoModelObservableList.setAll(reparacaoService.listaReparacoes());

        id_reparacao_coluna.setCellValueFactory(new PropertyValueFactory<>("id_reparacao"));
        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));
        id_veiculo_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getVeiculoModel().getId_veiculo())));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        descricao_coluna.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.getItems().setAll(reparacaoModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (data_datepicker.getValue() != null) {
            data_datepicker.setStyle(null);
        } else {
            data_datepicker.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }
        if (preco_label.getText().isEmpty()) {
            preco_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            preco_label.setStyle(null);
        }
        if (id_veiculo_label.getText().isEmpty()) {
            id_veiculo_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_veiculo_label.setStyle(null);
        }
        if (descricao_label.getText().isEmpty()) {
            descricao_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            descricao_label.setStyle(null);
        }

        if (!preco_label.getText().matches("^\\d+(\\.\\d+)*$")) {
            preco_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
            AlertMaker.errorAlert(null, "Erro", "Insira apenas números no campo 'Preco'");
        } else {
            preco_label.setStyle(null);
        }
        return flag;
    }
}
