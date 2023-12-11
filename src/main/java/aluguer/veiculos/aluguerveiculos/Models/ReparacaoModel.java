package aluguer.veiculos.aluguerveiculos.Models;

import java.time.LocalDate;

public class ReparacaoModel {
    private int id_reparacao;
    private VeiculoModel veiculoModel;
    private double preco;
    private String descricao;
    private LocalDate data;

    public ReparacaoModel(int id_reparacao, VeiculoModel veiculoModel, double preco, String descricao, LocalDate data) {
        this.id_reparacao = id_reparacao;
        this.veiculoModel = veiculoModel;
        this.preco = preco;
        this.descricao = descricao;
        this.data = data;
    }

    public ReparacaoModel(VeiculoModel veiculoModel, double preco, String descricao, LocalDate data) {
        this.veiculoModel = veiculoModel;
        this.preco = preco;
        this.descricao = descricao;
        this.data = data;
    }

    public ReparacaoModel(int id_reparacao) {
        this.id_reparacao = id_reparacao;
    }

    public int getId_reparacao() {
        return id_reparacao;
    }

    public void setId_reparacao(int id_reparacao) {
        this.id_reparacao = id_reparacao;
    }

    public VeiculoModel getVeiculoModel() {
        return veiculoModel;
    }

    public void setVeiculoModel(VeiculoModel veiculoModel) {
        this.veiculoModel = veiculoModel;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
