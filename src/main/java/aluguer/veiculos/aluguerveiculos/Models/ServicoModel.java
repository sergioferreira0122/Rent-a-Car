package aluguer.veiculos.aluguerveiculos.Models;

public class ServicoModel {
    private int id_servico;
    private String servico;
    private double preco;
    private String descricao;

    public ServicoModel(int id_servico, String servico, double preco, String descricao) {
        this.id_servico = id_servico;
        this.servico = servico;
        this.preco = preco;
        this.descricao = descricao;
    }

    public ServicoModel(String servico, double preco, String descricao) {
        this.servico = servico;
        this.preco = preco;
        this.descricao = descricao;
    }

    public ServicoModel(int id_servico) {
        this.id_servico = id_servico;
    }

    public int getId_servico() {
        return id_servico;
    }

    public void setId_servico(int id_servico) {
        this.id_servico = id_servico;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
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
}
