package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.AluguerModel;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Services.AluguerService;
import aluguer.veiculos.aluguerveiculos.Services.ServicoService;
import aluguer.veiculos.aluguerveiculos.Transport.FuncionarioModelTransport;
import aluguer.veiculos.aluguerveiculos.Utils.AlertMaker;
import aluguer.veiculos.aluguerveiculos.Utils.Loader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestaoAluguerController {
    @FXML
    public Button dragBTN;
    @FXML
    public Button gestaoBTN;
    @FXML
    public Button pedidoBTN;
    ObservableList<AluguerModel> aluguerModelObservableList = FXCollections.observableArrayList();
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button closeBTN;
    @FXML
    private Button minimizeBTN;
    @FXML
    private Button adicionarButton;
    @FXML
    private Button atualizarButton;

    //BOTÕES DE MENU
    @FXML
    private Button eliminarButton;
    @FXML
    private Button limparButton;
    @FXML
    private Button selecionarServicosBTN;
    @FXML
    private Button gestaoVeiculoBTN;
    @FXML
    private Button adminBTN;
    @FXML
    private Button settingsBTN;
    @FXML
    private Button logoutBTN;
    @FXML
    private TableColumn<AluguerModel, String> data_entrega_coluna;
    @FXML
    private TableColumn<AluguerModel, String> data_fim_coluna;
    @FXML
    private TableColumn<AluguerModel, String> data_inicio_coluna;
    @FXML
    private TableColumn<AluguerModel, String> desconto_coluna;
    @FXML
    private TableColumn<AluguerModel, String> id_aluguer_coluna;
    @FXML
    private TableColumn<AluguerModel, String> id_condutor_coluna;
    @FXML
    private TableColumn<AluguerModel, String> id_pedido_coluna;
    @FXML
    private TableColumn<AluguerModel, String> id_veiculo_coluna;
    @FXML
    private TableView<AluguerModel> tableView;
    @FXML
    private TextField id_veiculo_label;
    @FXML
    private TextField id_pedido_label;
    @FXML
    private TextField desconto_label;
    @FXML
    private TextField id_condutor_label;
    @FXML
    private TextField pesquisar_label;
    @FXML
    private DatePicker data_entrega_datepicker;
    @FXML
    private DatePicker data_inicio_datepicker;
    @FXML
    private DatePicker data_fim_datepicker;
    @FXML
    private Label nomeLabel;
    private AluguerService aluguerService;
    private ServicoService servicoService;
    private String EditId;

    // objetos
    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;

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

    public void loadNomeFuncionario() {
        nomeLabel.setText(funcionarioModel.getNome());
    }

    //metodos

    public void initialize() {
        aluguerService = AluguerService.getInstance();
        servicoService = ServicoService.getInstance();

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

    public void initTable() {
        aluguerModelObservableList.setAll(aluguerService.listaAluguers());

        data_entrega_coluna.setCellValueFactory(cellData -> {
            AluguerModel aluguerModel = cellData.getValue();
            if (aluguerModel.getData_entrega().isPresent()) {
                return new SimpleStringProperty(aluguerModel.getData_entrega().get().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });
        data_fim_coluna.setCellValueFactory(new PropertyValueFactory<>("data_fim"));
        data_inicio_coluna.setCellValueFactory(new PropertyValueFactory<>("data_inicio"));
        desconto_coluna.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        id_aluguer_coluna.setCellValueFactory(new PropertyValueFactory<>("id_aluguer"));
        id_condutor_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCondutorModel().getId_condutor())));
        id_pedido_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPedidoModel().getId_pedido())));
        id_veiculo_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getVeiculoModel().getId_veiculo())));

        tableView.getItems().setAll(aluguerModelObservableList);
    }

    private void limpar() {
        EditId = null;
        id_condutor_label.clear();
        desconto_label.clear();
        id_pedido_label.clear();
        id_veiculo_label.clear();
        data_inicio_datepicker.setValue(null);
        data_fim_datepicker.setValue(null);
        data_entrega_datepicker.setValue(null);
        eliminarButton.setDisable(true);
        pesquisar_label.clear();
        initTable();
    }

    private boolean verificarCampos() {
        boolean flag = false;
        if (data_inicio_datepicker.getValue() != null) {
            data_inicio_datepicker.setStyle(null);
        } else {
            data_inicio_datepicker.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }

        if (data_fim_datepicker.getValue() != null) {
            data_fim_datepicker.setStyle(null);
        } else {
            data_fim_datepicker.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }

        if (id_veiculo_label.getText().isEmpty()) {
            id_veiculo_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_veiculo_label.setStyle(null);
        }

        if (id_condutor_label.getText().isEmpty()) {
            id_condutor_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_condutor_label.setStyle(null);
        }

        if (id_pedido_label.getText().isEmpty()) {
            id_pedido_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_pedido_label.setStyle(null);
        }

        if (!desconto_label.getText().matches("^\\d+(\\.\\d+)*$")) {
            desconto_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
            AlertMaker.errorAlert(null, "Erro", "Insira apenas números no campo 'Desconto'");
        } else {
            desconto_label.setStyle(null);
        }
        return flag;
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

        LocalDate data_inicio = data_inicio_datepicker.getValue();
        LocalDate data_fim = data_fim_datepicker.getValue();
        String id_condutor = id_condutor_label.getText();
        String id_veiculo = id_veiculo_label.getText();
        String id_pedido = id_pedido_label.getText();
        String desconto = desconto_label.getText();

        if (desconto.isEmpty()) {
            desconto = "0";
        }

        if (aluguerService.adicionarAluguer(Integer.parseInt(id_veiculo), Integer.parseInt(id_condutor), Integer.parseInt(id_pedido), data_inicio, data_fim, Integer.parseInt(desconto))) {
            AlertMaker.informationAlert(null, "Sucesso", "Aluguer adicionado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o aluguer!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        LocalDate data_inicio = data_inicio_datepicker.getValue();
        LocalDate data_fim = data_fim_datepicker.getValue();
        LocalDate data_entrega = data_entrega_datepicker.getValue();
        String id_condutor = id_condutor_label.getText();
        String id_veiculo = id_veiculo_label.getText();
        String id_pedido = id_pedido_label.getText();
        String desconto = desconto_label.getText();

        if (desconto.isEmpty())
            desconto = "0";

        if (data_entrega_datepicker == null) {
            if (aluguerService.atualizarAluguer(Integer.parseInt(EditId), Integer.parseInt(id_veiculo), Integer.parseInt(id_condutor), Integer.parseInt(id_pedido), data_inicio, data_fim, null, Integer.parseInt(desconto))) {
                AlertMaker.informationAlert(null, "Sucesso", "Aluguer atualizado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o aluguer!");
            }
        } else {
            if (aluguerService.atualizarAluguer(Integer.parseInt(EditId), Integer.parseInt(id_veiculo), Integer.parseInt(id_condutor), Integer.parseInt(id_pedido), data_inicio, data_fim, data_entrega, Integer.parseInt(desconto))) {
                AlertMaker.informationAlert(null, "Sucesso", "Aluguer atualizado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o aluguer!");
            }
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_aluguer = pesquisar_label.getText();

        if (id_aluguer.isEmpty()) {
            return;
        }

        List<AluguerModel> aluguer = new ArrayList<>(aluguerService.pesquisarAluguerID(Integer.parseInt(id_aluguer)));

        if (aluguer.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Aluguer nao encontrado");
            return;
        }

        aluguerModelObservableList.setAll(aluguer);

        data_entrega_coluna.setCellValueFactory(data -> {
            AluguerModel aluguerModel = data.getValue();
            if (aluguerModel.getData_entrega().isPresent()) {
                return new SimpleStringProperty(aluguerModel.getData_entrega().get().toString());
            } else {
                return new SimpleStringProperty("");
            }
        });
        data_fim_coluna.setCellValueFactory(new PropertyValueFactory<>("data_fim"));
        data_inicio_coluna.setCellValueFactory(new PropertyValueFactory<>("data_inicio"));
        desconto_coluna.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        id_aluguer_coluna.setCellValueFactory(new PropertyValueFactory<>("id_aluguer"));
        id_condutor_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getCondutorModel().getId_condutor())));
        id_pedido_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getPedidoModel().getId_pedido())));
        id_veiculo_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getVeiculoModel().getId_veiculo())));

        tableView.getItems().setAll(aluguerModelObservableList);
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (aluguerService.eliminarAluguer(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Aluguer", "Aluguer eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar aluguer");
        }
    }

    @FXML
    void selecionarServicosHandle(ActionEvent event) {
        try {
            closeBTN.setDisable(true);

            FXMLLoader fxmlLoader = new FXMLLoader(Loader.class.getResource("/aluguer/veiculos/aluguerveiculos/selecionar-servicos-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            SelecionarServicosController selecionarServicosController = fxmlLoader.getController();

            if (EditId != null) {
                selecionarServicosController.setId_aluguer(EditId);
                selecionarServicosController.carregarServicosDeUmAluguer();
            }

            stage.setTitle("Rent a Car");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image("icon.png"));
            stage.setScene(scene);
            stage.setResizable(false);
            stage.showAndWait();

            closeBTN.setDisable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadDados(MouseEvent event) {
        data_entrega_datepicker.setValue(null);
        AluguerModel row = tableView.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_aluguer());
            id_veiculo_label.setText(String.valueOf(row.getVeiculoModel().getId_veiculo()));
            id_pedido_label.setText(String.valueOf(row.getPedidoModel().getId_pedido()));
            id_condutor_label.setText(String.valueOf(row.getCondutorModel().getId_condutor()));
            desconto_label.setText(String.valueOf(row.getDesconto()));
            data_fim_datepicker.setValue(row.getData_fim());
            data_inicio_datepicker.setValue(row.getData_inicio());

            if (row.getData_entrega().isPresent()) {
                data_entrega_datepicker.setValue(row.getData_entrega().get());
            }

            eliminarButton.setDisable(false);
        }
    }
}