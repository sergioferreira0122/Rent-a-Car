package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Enums.TipoCliente;
import aluguer.veiculos.aluguerveiculos.Enums.TipoConta;
import aluguer.veiculos.aluguerveiculos.Models.ClienteModel;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Services.ClienteService;
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

public class GestaoClienteController {
    @FXML
    public Button dragBTN;
    ObservableList<ClienteModel> clienteModelObservableList = FXCollections.observableArrayList();
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
    private Button gestaoVeiculoBTN;
    @FXML
    private Button adminBTN;
    @FXML
    private Button settingsBTN;
    @FXML
    private Button logoutBTN;
    @FXML
    private Button eliminarButton;

    //LABELS

    @FXML
    private Label nomeLabel;//funcionario

    @FXML
    private TextField pesquisar_label;
    @FXML
    private TextField nome_label;

    @FXML
    private ChoiceBox<TipoCliente> tipo_cliente_choicebox;
    @FXML
    private ChoiceBox<TipoConta> tipo_conta_choicebox;

    @FXML
    private TableView<ClienteModel> tabela;
    @FXML
    private TableColumn<ClienteModel, String> nome_coluna;
    @FXML
    private TableColumn<ClienteModel, String> tipo_cliente_coluna;
    @FXML
    private TableColumn<ClienteModel, String> tipo_conta_coluna;
    @FXML
    private TableColumn<ClienteModel, String> id_cliente_coluna;

    private String EditId;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private ClienteService clienteService;

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
        Stage stage = (Stage) pedidoBTN.getScene().getWindow();
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
        clienteService = ClienteService.getInstance();

        //admin
        funcionarioModelTransport = FuncionarioModelTransport.getInstance();
        funcionarioModel = funcionarioModelTransport.getFuncionarioModel();

        //choicebox
        List<TipoCliente> listaTipoClientes = new ArrayList<>();
        listaTipoClientes.add(TipoCliente.EMPRESA);
        listaTipoClientes.add(TipoCliente.INDIVIDUAL);

        List<TipoConta> listaTipoConta = new ArrayList<>();
        listaTipoConta.add(TipoConta.CONTA_CORRENTE);
        listaTipoConta.add(TipoConta.ESPORADICO);

        tipo_conta_choicebox.getItems().setAll(listaTipoConta);
        tipo_cliente_choicebox.getItems().setAll(listaTipoClientes);

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
        TipoConta tipoConta = tipo_conta_choicebox.getValue();
        TipoCliente tipoCliente = tipo_cliente_choicebox.getValue();

        if (clienteService.adicionarCliente(nome, tipoCliente, tipoConta)) {
            AlertMaker.informationAlert(null, "Sucesso", "Cliente adicionado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o cliente!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String nome = nome_label.getText();
        TipoConta tipoConta = tipo_conta_choicebox.getValue();
        TipoCliente tipoCliente = tipo_cliente_choicebox.getValue();

        if (clienteService.atualizarCliente(Integer.parseInt(EditId), nome, tipoCliente, tipoConta)) {
            AlertMaker.informationAlert(null, "Sucesso", "Cliente atualizado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o cliente!");
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (clienteService.eliminarCliente(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Cliente", "Cliente eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar cliente");
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_cliente = pesquisar_label.getText();

        if (id_cliente.isEmpty()) {
            return;
        }

        List<ClienteModel> cliente = new ArrayList<>(clienteService.pesquisarClienteID(Integer.parseInt(id_cliente)));

        if (cliente.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Cliente nao encontrado");
            return;
        }

        clienteModelObservableList.setAll(cliente);

        nome_coluna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipo_conta_coluna.setCellValueFactory(new PropertyValueFactory<>("tipoConta"));
        tipo_cliente_coluna.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        id_cliente_coluna.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));

        tabela.getItems().setAll(clienteModelObservableList);
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    private void loadDados(MouseEvent event) {  // on mouse clicked
        ClienteModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_cliente());
            nome_label.setText(row.getNome());
            tipo_cliente_choicebox.setValue(row.getTipoCliente());
            tipo_conta_choicebox.setValue(row.getTipoConta());

            eliminarButton.setDisable(false);
        }
    }

    private boolean verificarCampos() {
        boolean flag = false;
        if (tipo_conta_choicebox.getValue() != null) {
            tipo_conta_choicebox.setStyle(null);
        } else {
            tipo_conta_choicebox.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }

        if (tipo_cliente_choicebox.getValue() != null) {
            tipo_cliente_choicebox.setStyle(null);
        } else {
            tipo_cliente_choicebox.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }

        if (nome_label.getText().isEmpty()) {
            nome_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            nome_label.setStyle(null);
        }
        return flag;
    }

    private void limpar() {
        EditId = null;
        nome_label.clear();
        tipo_cliente_choicebox.setValue(null);
        tipo_conta_choicebox.setValue(null);
        pesquisar_label.clear();
        eliminarButton.setDisable(true);
        initTable();
    }

    public void initTable() {
        clienteModelObservableList.setAll(clienteService.listaClientes());

        nome_coluna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipo_conta_coluna.setCellValueFactory(new PropertyValueFactory<>("tipoConta"));
        tipo_cliente_coluna.setCellValueFactory(new PropertyValueFactory<>("tipoCliente"));
        id_cliente_coluna.setCellValueFactory(new PropertyValueFactory<>("id_cliente"));

        tabela.getItems().setAll(clienteModelObservableList);
    }
}
