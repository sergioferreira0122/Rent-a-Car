package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.ReciboModel;
import aluguer.veiculos.aluguerveiculos.Services.ReciboService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestaoReciboController {
    @FXML
    public Button dragBTN;
    ObservableList<ReciboModel> reciboModelObservableList = FXCollections.observableArrayList();
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
    private TextField pesquisar_label;
    @FXML
    private TextField valor_label;

    @FXML
    private DatePicker data_datepicker;

    private String EditId;

    @FXML
    private TableColumn<ReciboModel, String> data_coluna;
    @FXML
    private TableColumn<ReciboModel, String> id_recibo_coluna;
    @FXML
    private TableView<ReciboModel> tabela;
    @FXML
    private TableColumn<ReciboModel, String> valor_coluna;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private ReciboService reciboService;

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
        reciboService = ReciboService.getInstance();

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

        LocalDate data = data_datepicker.getValue();
        String valor = valor_label.getText();

        if (reciboService.adicionarRecibo(data, Double.parseDouble(valor))) {
            AlertMaker.informationAlert(null, "Sucesso", "Recibo adicionado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o recibo!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        LocalDate data = data_datepicker.getValue();
        String valor = valor_label.getText();

        if (reciboService.atualizarRecibo(Integer.parseInt(EditId), data, Double.parseDouble(valor))) {
            AlertMaker.informationAlert(null, "Sucesso", "Recibo atualizado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o recibo!");
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (reciboService.eliminarRecibo(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Recibo", "Recibo eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar recibo");
        }
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_recibo = pesquisar_label.getText();

        if (id_recibo.isEmpty()) {
            return;
        }

        List<ReciboModel> recibo = new ArrayList<>(reciboService.pesquisarReciboID(Integer.parseInt(id_recibo)));

        if (recibo.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Recibo nao encontrado");
            return;
        }

        reciboModelObservableList.setAll(recibo);

        id_recibo_coluna.setCellValueFactory(new PropertyValueFactory<>("id_recibo"));
        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));
        valor_coluna.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabela.getItems().setAll(reciboModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (data_datepicker.getValue() != null) {
            data_datepicker.setStyle(null);
        } else {
            data_datepicker.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }
        if (valor_label.getText().isEmpty()) {
            valor_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            valor_label.setStyle(null);
        }

        if (!valor_label.getText().matches("^\\d+(\\.\\d+)*$")) {
            valor_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
            AlertMaker.errorAlert(null, "Erro", "Insira apenas números no campo 'Valor'");
        } else {
            valor_label.setStyle(null);
        }
        return flag;
    }

    public void initTable() {
        reciboModelObservableList.setAll(reciboService.listaRecibos());

        id_recibo_coluna.setCellValueFactory(new PropertyValueFactory<>("id_recibo"));
        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));
        valor_coluna.setCellValueFactory(new PropertyValueFactory<>("valor"));

        tabela.getItems().setAll(reciboModelObservableList);
    }

    private void limpar() {
        EditId = null;
        data_datepicker.setValue(null);
        valor_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }

    @FXML
    void loadDados(MouseEvent event) {
        ReciboModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_recibo());
            data_datepicker.setValue(row.getData());
            valor_label.setText(String.valueOf(row.getValor()));

            eliminar_button.setDisable(false);
        }
    }
}
