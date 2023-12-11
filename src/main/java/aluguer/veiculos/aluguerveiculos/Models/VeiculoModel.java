package aluguer.veiculos.aluguerveiculos.Models;

public class VeiculoModel {
    private int id_veiculo;
    private ModeloModel modeloModel;
    private String matricula;
    private double preco;
    //private byte[] imagem;

    public VeiculoModel(int id_veiculo, ModeloModel modeloModel, String matricula, double preco) {
        this.id_veiculo = id_veiculo;
        this.modeloModel = modeloModel;
        this.matricula = matricula;
        this.preco = preco;
    }

    public VeiculoModel(ModeloModel modeloModel, String matricula, double preco) {
        this.modeloModel = modeloModel;
        this.matricula = matricula;
        this.preco = preco;
    }

    public VeiculoModel(int id_veiculo) {
        this.id_veiculo = id_veiculo;
    }

    public int getId_veiculo() {
        return id_veiculo;
    }

    public void setId_veiculo(int id_veiculo) {
        this.id_veiculo = id_veiculo;
    }

    public ModeloModel getModeloModel() {
        return modeloModel;
    }

    public void setModeloModel(ModeloModel modeloModel) {
        this.modeloModel = modeloModel;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
