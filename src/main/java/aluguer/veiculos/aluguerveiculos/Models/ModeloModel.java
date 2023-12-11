package aluguer.veiculos.aluguerveiculos.Models;

public class ModeloModel {
    private int id_modelo;
    private MarcaModel marcaModel;
    private String modelo;

    public ModeloModel(int id_modelo, MarcaModel marcaModel, String modelo) {
        this.id_modelo = id_modelo;
        this.marcaModel = marcaModel;
        this.modelo = modelo;
    }

    public ModeloModel(MarcaModel marcaModel, String modelo) {
        this.marcaModel = marcaModel;
        this.modelo = modelo;
    }

    public ModeloModel(int id_modelo, String modelo) {
        this.id_modelo = id_modelo;
        this.modelo = modelo;
    }

    public ModeloModel(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public int getId_modelo() {
        return id_modelo;
    }

    public void setId_modelo(int id_modelo) {
        this.id_modelo = id_modelo;
    }

    public MarcaModel getMarcaModel() {
        return marcaModel;
    }

    public void setMarcaModel(MarcaModel marcaModel) {
        this.marcaModel = marcaModel;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Override
    public String toString() {
        return modelo;
    }
}
