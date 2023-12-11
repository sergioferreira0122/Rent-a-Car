package aluguer.veiculos.aluguerveiculos.Controllers;

import aluguer.veiculos.aluguerveiculos.Models.Servico_AluguerModel;
import aluguer.veiculos.aluguerveiculos.Services.ServicoService;
import aluguer.veiculos.aluguerveiculos.Utils.AlertMaker;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class SelecionarServicosController {
    @FXML
    public Button dragBTN;
    ObservableList<Servico_AluguerModel> servicoModelObservableList = FXCollections.observableArrayList();
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    private Button closeBTN;
    @FXML
    private Button minimizeBTN;
    @FXML
    private Button eliminar_button;

    @FXML
    private TextField pesquisar_label;
    @FXML
    private TextField id_aluguer_label;
    @FXML
    private TextField id_servico_label;
    @FXML
    private TextField desconto_label;

    @FXML
    private TableColumn<Servico_AluguerModel, String> desconto_coluna;
    @FXML
    private TableColumn<Servico_AluguerModel, String> descricao_coluna;
    @FXML
    private TableColumn<Servico_AluguerModel, String> id_aluguer_coluna;
    @FXML
    private TableColumn<Servico_AluguerModel, String> id_servico_coluna;
    @FXML
    private TableColumn<Servico_AluguerModel, String> preco_coluna;
    @FXML
    private TableColumn<Servico_AluguerModel, String> servico_coluna;
    @FXML
    private TableView<Servico_AluguerModel> tabela;

    private String id_aluguer_GestaoAluguerController;
    private String EditId_aluguer;
    private String EditId_servico;

    //objetos

    private ServicoService servicoService;

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

    public void setId_aluguer(String id_aluguer_GestaoAluguerController) {
        this.id_aluguer_GestaoAluguerController = id_aluguer_GestaoAluguerController;
    }

    //metodos

    public void initialize() {
        servicoService = ServicoService.getInstance();

        initTable();
        limpar();
    }

    public void carregarServicosDeUmAluguer() {
        List<Servico_AluguerModel> servicosDeUmAluguer = new ArrayList<>(servicoService.listaServicosDeUmAluguer(Integer.parseInt(id_aluguer_GestaoAluguerController)));

        servicoModelObservableList.setAll(servicosDeUmAluguer);

        id_aluguer_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAluguerModel().getId_aluguer())));
        id_servico_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getServicoModel().getId_servico())));
        desconto_coluna.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        servico_coluna.setCellValueFactory(new PropertyValueFactory<>("servico"));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        descricao_coluna.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.getItems().setAll(servicoModelObservableList);
        pesquisar_label.setText(id_aluguer_GestaoAluguerController);
    }

    @FXML
    public void pesquisarHandle(ActionEvent event) {
        String id_aluguer = pesquisar_label.getText();

        if (id_aluguer.isEmpty()) {
            return;
        }

        List<Servico_AluguerModel> servicosDeUmAluguer = new ArrayList<>(servicoService.listaServicosDeUmAluguer(Integer.parseInt(id_aluguer)));

        if (servicosDeUmAluguer.get(0) == null) {
            AlertMaker.errorAlert(null, "Erro", "Aluguer nao encontrado");
            return;
        }

        servicoModelObservableList.setAll(servicosDeUmAluguer);

        id_aluguer_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAluguerModel().getId_aluguer())));
        id_servico_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getServicoModel().getId_servico())));
        desconto_coluna.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        servico_coluna.setCellValueFactory(new PropertyValueFactory<>("servico"));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        descricao_coluna.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.getItems().setAll(servicoModelObservableList);
    }

    @FXML
    public void limparHandle(ActionEvent event) {
        limpar();
    }

    @FXML
    public void eliminarHandle(ActionEvent event) {
        if (id_aluguer_label.getText().isEmpty()) {
            id_aluguer_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return;
        } else {
            id_aluguer_label.setStyle(null);
        }
        if (id_servico_label.getText().isEmpty()) {
            id_servico_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            return;
        } else {
            id_servico_label.setStyle(null);
        }
        if (!id_aluguer_label.getText().matches("^\\d+(\\.\\d+)*$")) {
            id_aluguer_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            AlertMaker.errorAlert(null, "Erro", "Insira apenas números no campo 'ID Aluguer'");
            return;
        } else {
            id_aluguer_label.setStyle(null);
        }
        if (!id_servico_label.getText().matches("^\\d+(\\.\\d+)*$")) {
            id_servico_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            AlertMaker.errorAlert(null, "Erro", "Insira apenas números no campo 'ID Servico'");
            return;
        } else {
            id_servico_label.setStyle(null);
        }

        String id_aluguer = id_aluguer_label.getText();
        String id_servico = id_servico_label.getText();

        if (servicoService.eliminarServico_Aluguer(Integer.parseInt(id_aluguer), Integer.parseInt(id_servico))) {
            AlertMaker.informationAlert(null, "Eliminar Serviço do aluguer", "Serviço do aluguer eliminado com sucesso");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao eliminar Serviço do aluguer");
        }
    }

    @FXML
    void atualizarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String id_aluguer = id_aluguer_label.getText();
        String id_servico = id_servico_label.getText();
        String desconto = desconto_label.getText();

        if (servicoService.atualizarServico_Aluguer(Integer.parseInt(id_aluguer), Integer.parseInt(id_servico), Double.parseDouble(desconto))) {
            AlertMaker.informationAlert(null, "Sucesso", "Serviço de aluguer atualizado com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao atualizar o serviço do aluguer!");
        }
    }

    @FXML
    public void adicionarHandle(ActionEvent event) {
        if (verificarCampos()) {
            return;
        }

        String id_aluguer = id_aluguer_label.getText();
        String id_servico = id_servico_label.getText();
        String desconto = desconto_label.getText();

        if (servicoService.adicionarServico_Aluguer(Integer.parseInt(id_aluguer), Integer.parseInt(id_servico), Double.parseDouble(desconto))) {
            AlertMaker.informationAlert(null, "Sucesso", "Serviço adicionado ao aluguer com sucesso!");
            initTable();
        } else {
            AlertMaker.errorAlert(null, "Erro", "Erro ao adicionar o serviço ao aluguer!");
        }
    }

    @FXML
    void loadDados(MouseEvent event) {
        Servico_AluguerModel row = tabela.getSelectionModel().getSelectedItem();

        if (!(row == null)) {
            EditId_aluguer = String.valueOf(row.getAluguerModel().getId_aluguer());
            EditId_servico = String.valueOf(row.getServicoModel().getId_servico());
            id_servico_label.setText(String.valueOf(row.getServicoModel().getId_servico()));
            id_aluguer_label.setText(String.valueOf(row.getAluguerModel().getId_aluguer()));
            desconto_label.setText(String.valueOf(row.getDesconto()));

            eliminar_button.setDisable(false);
        }
    }

    private void limpar() {
        EditId_servico = null;
        EditId_aluguer = null;
        id_servico_label.clear();
        id_aluguer_label.clear();
        desconto_label.clear();
        pesquisar_label.clear();
        eliminar_button.setDisable(true);
        initTable();
    }

    public void initTable() {
        servicoModelObservableList.setAll(servicoService.listaServicos_Aluguers());

        id_aluguer_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getAluguerModel().getId_aluguer())));
        id_servico_coluna.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getServicoModel().getId_servico())));
        desconto_coluna.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        servico_coluna.setCellValueFactory(new PropertyValueFactory<>("servico"));
        preco_coluna.setCellValueFactory(new PropertyValueFactory<>("preco"));
        descricao_coluna.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        tabela.getItems().setAll(servicoModelObservableList);
    }

    private boolean verificarCampos() {
        boolean flag = false;

        if (id_aluguer_label.getText().isEmpty()) {
            id_aluguer_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_aluguer_label.setStyle(null);
        }
        if (id_servico_label.getText().isEmpty()) {
            id_servico_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
        } else {
            id_servico_label.setStyle(null);
        }

        if (!id_aluguer_label.getText().matches("^\\d+(\\.\\d+)*$")) {
            id_aluguer_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
            AlertMaker.errorAlert(null, "Erro", "Insira apenas números no campo 'ID Aluguer'");
        } else {
            id_aluguer_label.setStyle(null);
        }
        if (!id_servico_label.getText().matches("^\\d+(\\.\\d+)*$")) {
            id_servico_label.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            flag = true;
            AlertMaker.errorAlert(null, "Erro", "Insira apenas números no campo 'ID Servico'");
        } else {
            id_servico_label.setStyle(null);
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
}
