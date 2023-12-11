package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.MarcaModel;
import aluguer.veiculos.aluguerveiculos.Models.ModeloModel;
import aluguer.veiculos.aluguerveiculos.Models.VeiculoModel;
import aluguer.veiculos.aluguerveiculos.Services.MarcaService;
import aluguer.veiculos.aluguerveiculos.Services.ModeloService;
import aluguer.veiculos.aluguerveiculos.Services.VeiculoService;
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

public class GestaoVeiculoController {
    @FXML
    public Button dragBTN;
    ObservableList<VeiculoModel> veiculoModelObservableList = FXCollections.observableArrayList();
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
    private TextField matricula_label;
    @FXML
    private TextField pesquisar_label;
    @FXML
    private TextField preco_label;

    private String EditId;

    @FXML
    private ChoiceBox<MarcaModel> marca_choicebox;
    @FXML
    private ChoiceBox<ModeloModel> modelo_choicebox;

    @FXML
    private TableColumn<VeiculoModel, String> id_veiculo_coluna;
    @FXML
    private TableColumn<VeiculoModel, String> marca_coluna;
    @FXML
    private TableColumn<VeiculoModel, String> matricula_coluna;
    @FXML
    private TableColumn<VeiculoModel, String> modelo_coluna;
    @FXML
    private TableColumn<VeiculoModel, String> preco_coluna;
    @FXML
    private TableView<VeiculoModel> tabela;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private VeiculoService veiculoService;
    private ModeloService modeloService;
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

    public void initialize() {
        veiculoService = VeiculoService.getInstance();
        marcaService = MarcaService.getInstance();
        modeloService = ModeloService.getInstance();

        //admin
        funcionarioModelTransport = FuncionarioModelTransport.getInstance();
        funcionarioModel = funcionarioModelTransport.getFuncionarioModel();

        List<ModeloModel> listaModelos = new ArrayList<>(modeloService.listaModelos());
        modelo_choicebox.getItems().setAll(listaModelos);

        List<MarcaModel> listaMarcas = new ArrayList<>(marcaService.listaMarcas());
        marca_choicebox.getItems().setAll(listaMarcas);

        //load admin
        loadNomeFuncionario();

        //verificacoes
        loadCheckPermissoes();

        initTable();
        limpar();

        marca_choicebox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                List<ModeloModel> modelos = veiculoService.listaModelosDeUmaMarca(newValue.getId_marca());
                modelo_choicebox.getItems().setAll(modelos);

                ModeloModel modeloSelecionado = modelo_choicebox.getValue();
                if (modeloSelecionado != null) {
                    boolean modeloPertenceAMarca = modelos.stream()
                            .anyMatch(modelo -> modelo.getId_modelo() == modeloSelecionado.getId_modelo());

                    if (!modeloPertenceAMarca) {
                        modelo_choicebox.setValue(null);
                    }
                }
            }
        });

        modelo_choicebox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                marca_choicebox.setValue(veiculoService.marcaDeUmModelo(newValue.getId_modelo()));
            }
        });
    }

    @FXML
    void adicionarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String matricula = matricula_label.getText();
        String preco = preco_label.getText();
        int id_modelo = modelo_choicebox.getValue().getId_modelo();

        if (veiculoService.adicionarVeiculo(id_modelo, matricula, Double.parseDouble(preco))) {
            AlertMaker.informationAlert(null, "Sucesso", "Veiculo adicionado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o veiculo!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String matricula = matricula_label.getText();
        String preco = preco_label.getText();
        int id_modelo = modelo_choicebox.getValue().getId_modelo();

        if (veiculoService.atualizarVeiculo(Integer.parseInt(EditId), id_modelo, matricula, Double.parseDouble(preco))) {
            AlertMaker.informationAlert(null, "Sucesso", "Veiculo atualizado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o veiculo!");
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (veiculoService.eliminarVeiculo(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Veiculo", "Veiculo eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar veiculo");
        }
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    void loadDados(MouseEvent event) {
        VeiculoModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_veiculo());
            matricula_label.setText(row.getMatricula());
            modelo_choicebox.setValue(row.getModeloModel());
            marca_choicebox.setValue(row.getModeloModel().getMarcaModel());
            preco_label.setText(String.valueOf(row.getPreco()));

            eliminar_button.setDisable(false);
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_veiculo = pesquisar_label.getText();

        if (id_veiculo.isEmpty()) {
            return;
        }

        List<VeiculoModel> veiculo = new ArrayList<>(veiculoService.pesquisarVeiculoID(Integer.parseInt(id_veiculo)));

        if (veiculo.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Veiculo nao encontrado");
            return;
        }

        veiculoModelObservableList.setAll(veiculo);

        id_veiculo_coluna.setCellValueFactory(new PropertyValueFactory<>("id_veiculo"));
        matricula_coluna.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        marca_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getModeloModel().getMarcaModel().getMarca())));
        modelo_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getModeloModel().getModelo())));

        tabela.getItems().setAll(veiculoModelObservableList);
    }

    private void limpar() {
        EditId = null;
        matricula_label.clear();

        modelo_choicebox.setValue(null);
        marca_choicebox.setValue(null);
        List<ModeloModel> listaModelos = new ArrayList<>(modeloService.listaModelos());
        modelo_choicebox.getItems().setAll(listaModelos);
        List<MarcaModel> listaMarcas = new ArrayList<>(marcaService.listaMarcas());
        marca_choicebox.getItems().setAll(listaMarcas);

        preco_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }

    public void initTable() {
        veiculoModelObservableList.setAll(veiculoService.listaVeiculos());

        id_veiculo_coluna.setCellValueFactory(new PropertyValueFactory<>("id_veiculo"));
        matricula_coluna.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        marca_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getModeloModel().getMarcaModel().getMarca())));
        modelo_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getModeloModel().getModelo())));

        tabela.getItems().setAll(veiculoModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (matricula_label.getText().isEmpty()) {
            matricula_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            matricula_label.setStyle(null);
        }
        if (preco_label.getText().isEmpty()) {
            preco_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            preco_label.setStyle(null);
        }
        if (marca_choicebox.getValue() != null) {
            marca_choicebox.setStyle(null);
        } else {
            marca_choicebox.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }
        if (modelo_choicebox.getValue() != null) {
            modelo_choicebox.setStyle(null);
        } else {
            modelo_choicebox.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
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
