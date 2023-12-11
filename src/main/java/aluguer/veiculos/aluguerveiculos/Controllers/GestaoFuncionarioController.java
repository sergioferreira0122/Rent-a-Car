package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Services.FuncionarioService;
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

public class GestaoFuncionarioController {
    @FXML
    public Button dragBTN;
    ObservableList<FuncionarioModel> funcionarioModelObservableList = FXCollections.observableArrayList();
    //qol
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button closeBTN;
    @FXML
    private Button minimizeBTN;

    //menu
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

    //permissões
    @FXML
    private CheckBox funcionariosCB;
    @FXML
    private CheckBox pedidosCB;
    @FXML
    private CheckBox veiculosCB;
    @FXML
    private CheckBox clientesCB;
    @FXML
    private CheckBox marcasCB;
    @FXML
    private CheckBox modelosCB;
    @FXML
    private CheckBox servicosCB;
    @FXML
    private CheckBox alugueresCB;
    @FXML
    private CheckBox reparacoesCB;
    @FXML
    private CheckBox recibosCB;
    @FXML
    private CheckBox faturasCB;
    @FXML
    private CheckBox condutoresCB;

    //LABELS

    @FXML
    private Label nomeLabel;
    @FXML
    private TextField email_label;
    @FXML
    private TextField nome_label;
    @FXML
    private TextField password_label;
    @FXML
    private TextField pesquisar_label;


    @FXML
    private TableColumn<FuncionarioModel, String> email_coluna;
    @FXML
    private TableColumn<FuncionarioModel, String> id_funcionario_coluna;
    @FXML
    private TableColumn<FuncionarioModel, String> nome_coluna;
    @FXML
    private TableView<FuncionarioModel> tabela;

    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private FuncionarioService funcionarioService;

    private String EditId;
    private List<Permissoes> permissoes;

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
        funcionarioService = FuncionarioService.getInstance();
        permissoes = new ArrayList<>();

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
        String email = email_label.getText();
        String password = password_label.getText();
        checkarPermissoes();

        if (password_label.getText().isEmpty()) {
            password_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            AlertMaker.errorAlert(null, "Erro", "Voce precisa inserir uma password ao adicionar funcionarios");
            return;
        }

        if (funcionarioService.adicionarFuncionario(email, nome, password, permissoes)) {
            AlertMaker.informationAlert(null, "Sucesso", "Funcionario adicionado com sucesso!");
            initTable();
            password_label.setStyle(null);
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o funcionario!");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String nome = nome_label.getText();
        String email = email_label.getText();
        String password = password_label.getText();
        checkarPermissoes();

        if (password.isBlank()) {
            if (funcionarioService.atualizarFuncionarioSemPassword(Integer.parseInt(EditId), email, nome, permissoes)) {
                AlertMaker.informationAlert(null, "Sucesso", "Funcionario atualizado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o funcionario!");
            }
        } else {
            if (funcionarioService.atualizarFuncionario(Integer.parseInt(EditId), email, nome, password, permissoes)) {
                AlertMaker.informationAlert(null, "Sucesso", "Funcionario atualizado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o funcionario!");
            }
        }

    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (funcionarioService.eliminarFuncionario(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Funcionario", "Funcionario eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar funcionario");
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_funcionario = pesquisar_label.getText();

        if (id_funcionario.isEmpty()) {
            return;
        }

        List<FuncionarioModel> funcionario = new ArrayList<>(funcionarioService.pesquisarFuncionario(Integer.parseInt(id_funcionario)));

        if (funcionario.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Funcionario nao encontrado");
            return;
        }

        funcionarioModelObservableList.setAll(funcionario);

        id_funcionario_coluna.setCellValueFactory(new PropertyValueFactory<>("id_funcionario"));
        nome_coluna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        email_coluna.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabela.getItems().setAll(funcionarioModelObservableList);
    }

    @FXML
    void loadDados(MouseEvent event) {
        FuncionarioModel row = tabela.getSelectionModel().getSelectedItem();
        password_label.clear();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_funcionario());
            nome_label.setText(row.getNome());
            email_label.setText(row.getEmail());
            funcionariosCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_FUNCIONARIOS));
            veiculosCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_VEICULOS));
            marcasCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_MARCAS));
            servicosCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_SERVICOS));
            reparacoesCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_REPARACOES));
            faturasCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_FATURAS));
            pedidosCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_PEDIDOS));
            clientesCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_CLIENTES));
            modelosCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_MODELOS));
            alugueresCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_ALUGUERS));
            recibosCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_RECIBOS));
            condutoresCB.setSelected(row.getPermissoes().contains(Permissoes.GESTAO_CONDUTORES));

            eliminar_button.setDisable(false);
        }
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    private boolean verificarCampos() {
        boolean flag = false;
        if (nome_label.getText().isEmpty()) {
            nome_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            nome_label.setStyle(null);
        }
        if (email_label.getText().isEmpty()) {
            email_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            email_label.setStyle(null);
        }
        return flag;
    }

    public void initTable() {
        funcionarioModelObservableList.setAll(funcionarioService.listaFuncionarios());

        id_funcionario_coluna.setCellValueFactory(new PropertyValueFactory<>("id_funcionario"));
        nome_coluna.setCellValueFactory(new PropertyValueFactory<>("nome"));
        email_coluna.setCellValueFactory(new PropertyValueFactory<>("email"));

        tabela.getItems().setAll(funcionarioModelObservableList);
    }

    private void limpar() {
        EditId = null;

        if (!permissoes.isEmpty()) {
            permissoes.clear();
        }

        nome_label.clear();
        email_label.clear();
        password_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);

        funcionariosCB.setSelected(false);
        veiculosCB.setSelected(false);
        marcasCB.setSelected(false);
        servicosCB.setSelected(false);
        reparacoesCB.setSelected(false);
        faturasCB.setSelected(false);
        pedidosCB.setSelected(false);
        clientesCB.setSelected(false);
        modelosCB.setSelected(false);
        alugueresCB.setSelected(false);
        recibosCB.setSelected(false);
        condutoresCB.setSelected(false);

        initTable();
    }

    private void checkarPermissoes() {
        permissoes.clear();
        if (funcionariosCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_FUNCIONARIOS);
        }
        if (veiculosCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_VEICULOS);
        }
        if (marcasCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_MARCAS);
        }
        if (servicosCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_SERVICOS);
        }
        if (reparacoesCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_REPARACOES);
        }
        if (faturasCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_FATURAS);
        }
        if (pedidosCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_PEDIDOS);
        }
        if (clientesCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_CLIENTES);
        }
        if (modelosCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_MODELOS);
        }
        if (alugueresCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_ALUGUERS);
        }
        if (recibosCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_RECIBOS);
        }
        if (condutoresCB.isSelected()) {
            permissoes.add(Permissoes.GESTAO_CONDUTORES);
        }
    }
}
