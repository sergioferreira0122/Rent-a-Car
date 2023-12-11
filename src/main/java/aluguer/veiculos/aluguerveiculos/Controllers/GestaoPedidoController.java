package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;
import aluguer.veiculos.aluguerveiculos.Models.PedidoModel;
import aluguer.veiculos.aluguerveiculos.Services.PedidoService;
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

public class GestaoPedidoController {
    @FXML
    public Button dragBTN;
    ObservableList<PedidoModel> pedidoModelObservableList = FXCollections.observableArrayList();
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
    private TextField id_cliente_label;
    @FXML
    private TextField id_fatura_label;
    @FXML
    private TextField pesquisar_label;

    @FXML
    private DatePicker data_datepicker;

    private String EditId;

    @FXML
    private TableColumn<PedidoModel, String> data_coluna;
    @FXML
    private TableColumn<PedidoModel, String> id_cliente_coluna;
    @FXML
    private TableColumn<PedidoModel, String> id_fatura_coluna;
    @FXML
    private TableColumn<PedidoModel, String> id_pedido_coluna;
    @FXML
    private TableView<PedidoModel> tabela;
    @FXML
    private TableColumn<PedidoModel, String> valor_coluna;
    @FXML
    private TableColumn<PedidoModel, String> estadoPedido_coluna;
    @FXML
    private TableColumn<PedidoModel, String> estadoPagamento_coluna;


    //OBJETOS

    private FuncionarioModel funcionarioModel;
    private FuncionarioModelTransport funcionarioModelTransport;
    private PedidoService pedidoService;

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
        pedidoService = PedidoService.getInstance();

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
        String id_cliente = id_cliente_label.getText();
        String id_fatura = id_fatura_label.getText();

        if (id_fatura == null || id_fatura.trim().isEmpty()) {
            if (pedidoService.adicionarPedido(0, Integer.parseInt(id_cliente), data)) {
                AlertMaker.informationAlert(null, "Sucesso", "Pedido adicionado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o pedido!");
            }
        } else {
            if (pedidoService.adicionarPedido(Integer.parseInt(id_fatura), Integer.parseInt(id_cliente), data)) {
                AlertMaker.informationAlert(null, "Sucesso", "Pedido adicicionado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o pedido!");
            }
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        LocalDate data = data_datepicker.getValue();
        String id_cliente = id_cliente_label.getText();
        String id_fatura = id_fatura_label.getText();

        if (id_fatura == null || id_fatura.trim().isEmpty()) {
            if (pedidoService.atualizarPedido(Integer.parseInt(EditId), 0, Integer.parseInt(id_cliente), data)) {
                AlertMaker.informationAlert(null, "Sucesso", "Pedido atualizado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o pedido!");
            }
        } else {
            if (pedidoService.atualizarPedido(Integer.parseInt(EditId), Integer.parseInt(id_fatura), Integer.parseInt(id_cliente), data)) {
                AlertMaker.informationAlert(null, "Sucesso", "Pedido atualizado com sucesso!");
                initTable();
            } else {
                AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o pedido!");
            }
        }
    }

    @FXML
    void eliminarHandle(ActionEvent event) {
        if (pedidoService.eliminarPedido(Integer.parseInt(EditId))) {
            AlertMaker.informationAlert(null, "Eliminar Pedido", "Pedido eliminada com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar pedido");
        }
    }

    @FXML
    void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    private void loadDados(MouseEvent event) {  // on mouse clicked
        id_fatura_label.clear();
        PedidoModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId = String.valueOf(row.getId_pedido());
            if (row.getFaturaModel().isPresent() && row.getFaturaModel().get().getId_fatura() != 0) {
                id_fatura_label.setText(String.valueOf(row.getFaturaModel().get().getId_fatura()));
            }
            id_cliente_label.setText(String.valueOf(row.getClienteModel().getId_cliente()));
            data_datepicker.setValue(row.getData());

            eliminar_button.setDisable(false);
        }
    }

    @FXML
    void pesquisarHandle(ActionEvent event) {
        String id_pedido = pesquisar_label.getText();

        if (id_pedido.isEmpty()) {
            return;
        }

        List<PedidoModel> pedido = new ArrayList<>(pedidoService.pesquisarPedidoID(Integer.parseInt(id_pedido)));

        if (pedido.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Pedido nao encontrado");
            return;
        }

        pedidoModelObservableList.setAll(pedido);

        id_cliente_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getClienteModel().getId_cliente())));
        id_fatura_coluna.setCellValueFactory(data -> {
            PedidoModel pedidoModel = data.getValue();
            if (pedidoModel.getFaturaModel().isPresent() && pedidoModel.getFaturaModel().get().getId_fatura() != 0) {
                return new SimpleStringProperty(String.valueOf(pedidoModel.getFaturaModel().get().getId_fatura()));
            } else {
                return new SimpleStringProperty();
            }
        });
        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));
        valor_coluna.setCellValueFactory(new PropertyValueFactory<>("valor"));
        id_pedido_coluna.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));

        estadoPagamento_coluna.setCellValueFactory(data -> {
            PedidoModel pedidoModel = data.getValue();
            boolean flag = pedidoService.estadoPagamento(pedidoModel.getId_pedido());

            if (flag) {
                return new SimpleStringProperty("Pedido pago");
            } else {
                return new SimpleStringProperty("Pedido ainda por pagar");
            }
        });

        estadoPedido_coluna.setCellValueFactory(data -> {
            PedidoModel pedidoModel = data.getValue();
            int flag = pedidoService.estadoPedido(pedidoModel.getId_pedido());

            if (flag == 0) {
                return new SimpleStringProperty("Viatura/s devolvida/s");
            } else if (flag == -1) {
                return new SimpleStringProperty("Sem alugueres registados");
            }else {
                return new SimpleStringProperty("Em uso");
            }
        });

        tabela.getItems().setAll(pedidoModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;
        if (data_datepicker.getValue() != null) {
            data_datepicker.setStyle(null);
        } else {
            data_datepicker.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        }
        if (id_cliente_label.getText().isEmpty()) {
            id_cliente_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_cliente_label.setStyle(null);
        }
        return flag;
    }

    private void limpar() {
        EditId = null;
        id_cliente_label.clear();
        id_fatura_label.clear();
        data_datepicker.setValue(null);
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }

    public void initTable() {
        pedidoModelObservableList.setAll(pedidoService.listaPedidos());

        id_cliente_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getClienteModel().getId_cliente())));
        id_fatura_coluna.setCellValueFactory(data -> {
            PedidoModel pedidoModel = data.getValue();
            if (pedidoModel.getFaturaModel().isPresent() && pedidoModel.getFaturaModel().get().getId_fatura() != 0) {
                return new SimpleStringProperty(String.valueOf(pedidoModel.getFaturaModel().get().getId_fatura()));
            } else {
                return new SimpleStringProperty();
            }
        });
        data_coluna.setCellValueFactory(new PropertyValueFactory<>("data"));
        valor_coluna.setCellValueFactory(new PropertyValueFactory<>("valor"));
        id_pedido_coluna.setCellValueFactory(new PropertyValueFactory<>("id_pedido"));


        estadoPagamento_coluna.setCellValueFactory(data -> {
            PedidoModel pedidoModel = data.getValue();
            boolean flag = pedidoService.estadoPagamento(pedidoModel.getId_pedido());

            if (flag) {
                return new SimpleStringProperty("Pedido pago");
            } else {
                return new SimpleStringProperty("Pedido ainda por pagar");
            }
        });

        estadoPedido_coluna.setCellValueFactory(data -> {
            PedidoModel pedidoModel = data.getValue();
            int flag = pedidoService.estadoPedido(pedidoModel.getId_pedido());

            if (flag == 0) {
                return new SimpleStringProperty("Viatura/s devolvida/s");
            } else if (flag == -1) {
                return new SimpleStringProperty("Sem alugueres registados");
            }else {
                return new SimpleStringProperty("Em uso");
            }
        });

        tabela.getItems().setAll(pedidoModelObservableList);
    }
}
