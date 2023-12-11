package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.ServicoModel;
import aluguer.veiculos.aluguerveiculos.Services.ServicoService;
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

public class GestaoServicoController {
    @FXML
    public Button dragBTN;
    ObservableList<ServicoModel> servicoModelObservableList = FXCollections.observableArrayList();
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
    private TextField preco_label;
    @FXML
    private TextField servico_label;

    private String EditId;

    @FXML
    private TextArea descricao_label;

    @FXML
    private TableColumn<ServicoModel, String> descricao_coluna;
    @FXML
    private TableColumn<ServicoModel, String> id_servico_coluna;
    @FXML
    private TableColumn<ServicoModel, String> preco_coluna;
    @FXML
    private TableColumn<ServicoModel, String> servico_coluna;
    @FXML
    private TableView<ServicoModel> tabela;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private ServicoService servicoService;

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

    @FXML
    void adicionarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String servico = servico_label.getText();
        String preco = preco_label.getText();
        String descricao = descricao_label.getText();

        if (servicoService.adicionarServico(servico, Double.parseDouble(preco), descricao)) {
            AlertMaker.informationAlert(null, "Sucesso", "Serviço adicionada com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o serviço!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String servico = servico_label.getText();
        String preco = preco_label.getText();
        String descricao = descricao_label.getText();

        if (servicoService.atualizarServico(Integer.parseInt(EditId), servico, Double.parseDouble(preco), descricao)) {
            AlertMaker.informationAlert(null, "Sucesso", "Servico atualizado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o serviço!");
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (servicoService.eliminarServico(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Servico", "Servico eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar servico");
        }
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    void loadDados(MouseEvent event) {
        ServicoModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_servico());
            servico_label.setText(row.getServico());
            preco_label.setText(String.valueOf(row.getPreco()));
            descricao_label.setText(row.getDescricao());

            eliminar_button.setDisable(false);
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_servico = pesquisar_label.getText();

        if (id_servico.isEmpty()) {
            return;
        }

        List<ServicoModel> servico = new ArrayList<>(servicoService.pesquisarServicoID(Integer.parseInt(id_servico)));

        if (servico.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Servico nao encontrado");
            return;
        }

        servicoModelObservableList.setAll(servico);

        id_servico_coluna.setCellValueFactory(new PropertyValueFactory<>("id_servico"));
        servico_coluna.setCellValueFactory(new PropertyValueFactory<>("servico"));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        descricao_coluna.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.getItems().setAll(servicoModelObservableList);
    }

    private void limpar() {
        EditId = null;
        servico_label.clear();
        preco_label.clear();
        descricao_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }

    public void initTable() {
        servicoModelObservableList.setAll(servicoService.listaServicos());

        id_servico_coluna.setCellValueFactory(new PropertyValueFactory<>("id_servico"));
        servico_coluna.setCellValueFactory(new PropertyValueFactory<>("servico"));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        descricao_coluna.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.getItems().setAll(servicoModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (servico_label.getText().isEmpty()) {
            servico_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            servico_label.setStyle(null);
        }
        if (descricao_label.getText().isEmpty()) {
            descricao_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            descricao_label.setStyle(null);
        }
        if (preco_label.getText().isEmpty()) {
            preco_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            preco_label.setStyle(null);
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
