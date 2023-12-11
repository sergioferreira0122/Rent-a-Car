package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FaturaModel;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Services.FaturaService;
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

public class GestaoFaturaController {
    @FXML
    public Button dragBTN;
    ObservableList<FaturaModel> faturaModelObservableList = FXCollections.observableArrayList();
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
    private TextField id_funcionario_label;
    @FXML
    private TextField id_recibo_label;
    @FXML
    private TextField pesquisar_label;

    @FXML
    private TableView<FaturaModel> tabela;
    @FXML
    private TableColumn<FaturaModel, String> data_coluna;
    @FXML
    private TableColumn<FaturaModel, String> id_fatura_coluna;
    @FXML
    private TableColumn<FaturaModel, String> id_funcionario_coluna;
    @FXML
    private TableColumn<FaturaModel, String> id_recibo_coluna;

    @FXML
    private DatePicker data_datepicker;

    private String EditId;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private FaturaService faturaService;

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
        faturaService = FaturaService.getInstance();

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

        if (!id_recibo_label.getText().isEmpty()) {
            id_recibo_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            AlertMaker.errorAlert(null, "Erro", "Nao é possivel criar uma fatura com um recibo, terá de atualizar a fatura se quiser adicionar um recibo");
            return;
        } else {
            id_recibo_label.setStyle(null);
        }

        LocalDate data = data_datepicker.getValue();
        String id_funcionario = id_funcionario_label.getText();

        if (faturaService.adicionarFatura(Integer.parseInt(id_funcionario), 0, data)) {
            AlertMaker.informationAlert(null, "Sucesso", "Fatura adicionada com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar a fatura!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        LocalDate data = data_datepicker.getValue();
        String id_funcionario = id_funcionario_label.getText();
        String id_recibo = id_recibo_label.getText();

        if (id_recibo == null || id_recibo.trim().isEmpty()) {
            if (faturaService.atualizarFatura(Integer.parseInt(EditId), Integer.parseInt(id_funcionario), 0, data)) {
                AlertMaker.informationAlert(null, "Sucesso", "Fatura atualizada com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar a fatura!");
            }
        } else {
            if (faturaService.atualizarFatura(Integer.parseInt(EditId), Integer.parseInt(id_funcionario), Integer.parseInt(id_recibo), data)) {
                AlertMaker.informationAlert(null, "Sucesso", "Fatura atualizada com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar a fatura!");
            }
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (faturaService.eliminarFatura(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Fatura", "Fatura eliminada com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar fatura");
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_fatura = pesquisar_label.getText();

        if (id_fatura.isEmpty()) {
            return;
        }

        List<FaturaModel> fatura = new ArrayList<>(faturaService.pesquisarFaturaID(Integer.parseInt(id_fatura)));

        if (fatura.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Fatura nao encontrada");
            return;
        }

        faturaModelObservableList.setAll(fatura);

        id_fatura_coluna.setCellValueFactory(new PropertyValueFactory<>("id_fatura"));
        id_funcionario_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getFuncionarioModel().getId_funcionario())));
        id_recibo_coluna.setCellValueFactory(data -> {
            FaturaModel faturaModel = data.getValue();
            if (faturaModel.getReciboModel().isPresent() && faturaModel.getReciboModel().get().getId_recibo() != 0) {
                return new SimpleStringProperty(String.valueOf(faturaModel.getReciboModel().get().getId_recibo()));
            } else {
                return new SimpleStringProperty();
            }
        });

        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabela.getItems().setAll(faturaModelObservableList);
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    public void initTable() {
        faturaModelObservableList.setAll(faturaService.listaFaturas());

        id_fatura_coluna.setCellValueFactory(new PropertyValueFactory<>("id_fatura"));
        id_funcionario_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getFuncionarioModel().getId_funcionario())));
        id_recibo_coluna.setCellValueFactory(data -> {
            FaturaModel faturaModel = data.getValue();
            if (faturaModel.getReciboModel().isPresent() && faturaModel.getReciboModel().get().getId_recibo() != 0) {
                return new SimpleStringProperty(String.valueOf(faturaModel.getReciboModel().get().getId_recibo()));
            } else {
                return new SimpleStringProperty();
            }
        });
        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));

        tabela.getItems().setAll(faturaModelObservableList);
    }

    private void limpar() {
        EditId = null;
        id_recibo_label.clear();
        id_funcionario_label.clear();
        data_datepicker.setValue(null);
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }

    private boolean verificarCampos() {
        boolean flag = false;
        if (data_datepicker.getValue() != null) {
            data_datepicker.setStyle(null);
        } else {
            data_datepicker.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }
        if (id_funcionario_label.getText().isEmpty()) {
            id_funcionario_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_funcionario_label.setStyle(null);
        }
        return flag;
    }

    @FXML
    private void loadDados(MouseEvent event) {  // on mouse clicked
        id_recibo_label.clear();
        FaturaModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_fatura());
            if (row.getReciboModel().isPresent() && row.getReciboModel().get().getId_recibo() != 0) {
                id_recibo_label.setText(String.valueOf(row.getReciboModel().get().getId_recibo()));
            }
            id_funcionario_label.setText(String.valueOf(row.getFuncionarioModel().getId_funcionario()));
            data_datepicker.setValue(row.getData());

            eliminar_button.setDisable(false);
        }
    }
}
