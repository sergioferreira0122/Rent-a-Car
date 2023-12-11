package aluguer.veiculos.aluguerveiculos.Models;

import java.time.LocalDate;
import java.util.Optional;

public class AluguerModel {
    private int id_aluguer;
    private VeiculoModel veiculoModel;
    private CondutorModel condutorModel;
    private PedidoModel pedidoModel;
    private LocalDate data_inicio;
    private LocalDate data_fim;
    private Optional<LocalDate> data_entrega;
    private int desconto;

    public AluguerModel(int id_aluguer, VeiculoModel veiculoModel, CondutorModel condutorModel, PedidoModel pedidoModel, LocalDate data_inicio, LocalDate data_fim, LocalDate data_entrega, int desconto) {
        this.id_aluguer = id_aluguer;
        this.veiculoModel = veiculoModel;
        this.condutorModel = condutorModel;
        this.pedidoModel = pedidoModel;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.data_entrega = Optional.ofNullable(data_entrega);
        this.desconto = desconto;
    }

    public AluguerModel(VeiculoModel veiculoModel, CondutorModel condutorModel, PedidoModel pedidoModel, LocalDate data_inicio, LocalDate data_fim, LocalDate data_entrega, int desconto) {
        this.veiculoModel = veiculoModel;
        this.condutorModel = condutorModel;
        this.pedidoModel = pedidoModel;
        this.data_inicio = data_inicio;
        this.data_fim = data_fim;
        this.data_entrega = Optional.ofNullable(data_entrega);
        this.desconto = desconto;
    }

    public AluguerModel(int id_aluguer) {
        this.id_aluguer = id_aluguer;
    }

    public int getId_aluguer() {
        return id_aluguer;
    }

    public void setId_aluguer(int id_aluguer) {
        this.id_aluguer = id_aluguer;
    }

    public VeiculoModel getVeiculoModel() {
        return veiculoModel;
    }

    public void setVeiculoModel(VeiculoModel veiculoModel) {
        this.veiculoModel = veiculoModel;
    }

    public CondutorModel getCondutorModel() {
        return condutorModel;
    }

    public void setCondutorModel(CondutorModel condutorModel) {
        this.condutorModel = condutorModel;
    }

    public PedidoModel getPedidoModel() {
        return pedidoModel;
    }

    public void setPedidoModel(PedidoModel pedidoModel) {
        this.pedidoModel = pedidoModel;
    }

    public LocalDate getData_inicio() {
        return data_inicio;
    }

    public void setData_inicio(LocalDate data_inicio) {
        this.data_inicio = data_inicio;
    }

    public LocalDate getData_fim() {
        return data_fim;
    }

    public void setData_fim(LocalDate data_fim) {
        this.data_fim = data_fim;
    }

    public Optional<LocalDate> getData_entrega() {
        return data_entrega;
    }

    public void setData_entrega(LocalDate data_entrega) {
        this.data_entrega = Optional.ofNullable(data_entrega);
    }

    public int getDesconto() {
        return desconto;
    }

    public void setDesconto(int desconto) {
        this.desconto = desconto;
    }

    @Override
    public String toString() {
        return "AluguerModel{" +
                "id_aluguer=" + id_aluguer +
                ", veiculoModel=" + veiculoModel +
                ", condutorModel=" + condutorModel +
                ", pedidoModel=" + pedidoModel +
                ", data_inicio=" + data_inicio +
                ", data_fim=" + data_fim +
                ", data_entrega=" + data_entrega +
                ", desconto=" + desconto +
                '}';
    }
}
