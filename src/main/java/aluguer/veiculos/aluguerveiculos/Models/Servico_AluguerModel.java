package aluguer.veiculos.aluguerveiculos.Models;

public class Servico_AluguerModel {
    private AluguerModel aluguerModel;
    private ServicoModel servicoModel;
    private double desconto;
    private String servico;
    private double preco;
    private String descricao;

    public Servico_AluguerModel(AluguerModel aluguerModel, ServicoModel servicoModel, double desconto, String servico, double preco, String descricao) {
        this.aluguerModel = aluguerModel;
        this.servicoModel = servicoModel;
        this.desconto = desconto;
        this.servico = servico;
        this.preco = preco;
        this.descricao = descricao;
    }

    public Servico_AluguerModel(AluguerModel aluguerModel, ServicoModel servicoModel, double desconto) {
        this.aluguerModel = aluguerModel;
        this.servicoModel = servicoModel;
        this.desconto = desconto;
    }

    public Servico_AluguerModel(AluguerModel aluguerModel, ServicoModel servicoModel) {
        this.aluguerModel = aluguerModel;
        this.servicoModel = servicoModel;
    }

    public AluguerModel getAluguerModel() {
        return aluguerModel;
    }

    public void setAluguerModel(AluguerModel aluguerModel) {
        this.aluguerModel = aluguerModel;
    }

    public ServicoModel getServicoModel() {
        return servicoModel;
    }

    public void setServicoModel(ServicoModel servicoModel) {
        this.servicoModel = servicoModel;
    }

    public double getDesconto() {
        return desconto;
    }

    public void setDesconto(double desconto) {
        this.desconto = desconto;
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
