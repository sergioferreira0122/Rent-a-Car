package aluguer.veiculos.aluguerveiculos.Models;

import java.time.LocalDate;
import java.util.Optional;

public class PedidoModel {
    private int id_pedido;
    private Optional<FaturaModel> faturaModel;
    private ClienteModel clienteModel;
    private LocalDate data;
    private double valor;

    public PedidoModel(int id_pedido, FaturaModel faturaModel, ClienteModel clienteModel, LocalDate data, double valor) {
        this.id_pedido = id_pedido;
        this.faturaModel = Optional.ofNullable(faturaModel);
        this.clienteModel = clienteModel;
        this.data = data;
        this.valor = valor;
    }

    public PedidoModel(int id_pedido, FaturaModel faturaModel, ClienteModel clienteModel, LocalDate data) {
        this.id_pedido = id_pedido;
        this.faturaModel = Optional.ofNullable(faturaModel);
        this.clienteModel = clienteModel;
        this.data = data;
    }

    public PedidoModel(FaturaModel faturaModel, ClienteModel clienteModel, LocalDate data) {
        this.faturaModel = Optional.ofNullable(faturaModel);
        this.clienteModel = clienteModel;
        this.data = data;
    }

    public PedidoModel(FaturaModel faturaModel, ClienteModel clienteModel, LocalDate data, double valor) {
        this.faturaModel = Optional.ofNullable(faturaModel);
        this.clienteModel = clienteModel;
        this.data = data;
        this.valor = valor;
    }

    public PedidoModel(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public Optional<FaturaModel> getFaturaModel() {
        return faturaModel;
    }

    public void setFaturaModel(FaturaModel faturaModel) {
        this.faturaModel = Optional.ofNullable(faturaModel);
    }

    public ClienteModel getClienteModel() {
        return clienteModel;
    }

    public void setClienteModel(ClienteModel clienteModel) {
        this.clienteModel = clienteModel;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
