package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.CondutorModel;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Services.CondutorService;
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

public class GestaoCondutorController {
    @FXML
    public Button dragBTN;
    ObservableList<CondutorModel> condutorModelObservableList = FXCollections.observableArrayList();
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
    private TextField morada_label;
    @FXML
    private TextField n_carta_conducao_label;
    @FXML
    private TextField n_cartao_cidadao_label;
    @FXML
    private TextField nome_label;
    @FXML
    private TextField pesquisar_label;
    private String EditId;


    @FXML
    private TableColumn<CondutorModel, String> id_coluna;
    @FXML
    private TableColumn<CondutorModel, String> morada_coluna;
    @FXML
    private TableColumn<CondutorModel, String> n_carta_conducao_coluna;
    @FXML
    private TableColumn<CondutorModel, String> n_cartao_cidadao_coluna;
    @FXML
    private TableColumn<CondutorModel, String> nome_coluna;
    @FXML
    private TableView<CondutorModel> tabela;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private CondutorService condutorService;

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

    public void initialize() {
        condutorService = CondutorService.getInstance();

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
        String morada = morada_label.getText();
        String n_cc = n_cartao_cidadao_label.getText();
        String n_carta_conducao = n_carta_conducao_label.getText();

        if (condutorService.adicionarCondutor(nome, morada, n_cc, n_carta_conducao)) {
            AlertMaker.informationAlert(null, "Sucesso", "Condutor adicionado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o condutor!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String nome = nome_label.getText();
        String morada = morada_label.getText();
        String n_cc = n_cartao_cidadao_label.getText();
        String n_carta_conducao = n_carta_conducao_label.getText();

        if (condutorService.atualizarCondutor(Integer.parseInt(EditId), nome, morada, n_cc, n_carta_conducao)) {
            AlertMaker.informationAlert(null, "Sucesso", "Condutor atualizado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o condutor!");
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (condutorService.eliminarCondutor(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Condutor", "Condutor eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar condutor");
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_condutor = pesquisar_label.getText();

        if (id_condutor.isEmpty()) {
            return;
        }

        List<CondutorModel> condutor = new ArrayList<>(condutorService.pesquisarCondutorID(Integer.parseInt(id_condutor)));

        if (condutor.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Condutor nao encontrado");
            return;
        }

        condutorModelObservableList.setAll(condutor);

        id_coluna.setCellValueFactory(new PropertyValueFactory<>("id_condutor"));
        nome_coluna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        morada_coluna.setCellValueFactory(new PropertyValueFactory<>("morada"));
        n_carta_conducao_coluna.setCellValueFactory(new PropertyValueFactory<>("nr_carta"));
        n_cartao_cidadao_coluna.setCellValueFactory(new PropertyValueFactory<>("cc"));

        tabela.getItems().setAll(condutorModelObservableList);
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    void loadDados(MouseEvent event) {
        CondutorModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_condutor());
            nome_label.setText(row.getNome());
            morada_label.setText(row.getMorada());
            n_carta_conducao_label.setText(row.getNr_carta());
            n_cartao_cidadao_label.setText(row.getCc());

            eliminar_button.setDisable(false);
        }
    }

    private void limpar() {
        EditId = null;
        nome_label.clear();
        morada_label.clear();
        n_cartao_cidadao_label.clear();
        n_carta_conducao_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }

    public void initTable() {
        condutorModelObservableList.setAll(condutorService.listaCondutores());

        id_coluna.setCellValueFactory(new PropertyValueFactory<>("id_condutor"));
        nome_coluna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        morada_coluna.setCellValueFactory(new PropertyValueFactory<>("morada"));
        n_carta_conducao_coluna.setCellValueFactory(new PropertyValueFactory<>("nr_carta"));
        n_cartao_cidadao_coluna.setCellValueFactory(new PropertyValueFactory<>("cc"));

        tabela.getItems().setAll(condutorModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (nome_label.getText().isEmpty()) {
            nome_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            nome_label.setStyle(null);
        }
        if (morada_label.getText().isEmpty()) {
            morada_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            morada_label.setStyle(null);
        }
        if (n_carta_conducao_label.getText().isEmpty()) {
            n_carta_conducao_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            n_carta_conducao_label.setStyle(null);
        }
        if (n_cartao_cidadao_label.getText().isEmpty()) {
            n_cartao_cidadao_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            n_cartao_cidadao_label.setStyle(null);
        }
        return flag;
    }
}
