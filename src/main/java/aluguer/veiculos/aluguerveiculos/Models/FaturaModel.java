package aluguer.veiculos.aluguerveiculos.Models;

import java.time.LocalDate;
import java.util.Optional;

public class FaturaModel {
    private int id_fatura;
    private FuncionarioModel funcionarioModel;
    private Optional<ReciboModel> reciboModel;
    private LocalDate data;

    public FaturaModel(int id_fatura, FuncionarioModel funcionarioModel, ReciboModel reciboModel, LocalDate data) {
        this.id_fatura = id_fatura;
        this.funcionarioModel = funcionarioModel;
        this.reciboModel = Optional.ofNullable(reciboModel);
        this.data = data;
    }

    public FaturaModel(FuncionarioModel funcionarioModel, ReciboModel reciboModel, LocalDate data) {
        this.funcionarioModel = funcionarioModel;
        this.reciboModel = Optional.ofNullable(reciboModel);
        this.data = data;
    }

    public FaturaModel(int id_fatura) {
        this.id_fatura = id_fatura;
    }

    public int getId_fatura() {
        return id_fatura;
    }

    public void setId_fatura(int id_fatura) {
        this.id_fatura = id_fatura;
    }

    public FuncionarioModel getFuncionarioModel() {
        return funcionarioModel;
    }

    public void setFuncionarioModel(FuncionarioModel funcionarioModel) {
        this.funcionarioModel = funcionarioModel;
    }

    public Optional<ReciboModel> getReciboModel() {
        return reciboModel;
    }

    public void setReciboModel(ReciboModel reciboModel) {
        this.reciboModel = Optional.ofNullable(reciboModel);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FaturaModel{" +
                "id_fatura=" + id_fatura +
                ", funcionarioModel=" + funcionarioModel +
                ", reciboModel=" + reciboModel +
                ", data=" + data +
                '}';
    }
}
