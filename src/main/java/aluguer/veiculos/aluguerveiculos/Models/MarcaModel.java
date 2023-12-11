package aluguer.veiculos.aluguerveiculos.Models;

public class MarcaModel {
    private int id_marca;
    private String marca;

    public MarcaModel(int id_marca, String marca) {
        this.id_marca = id_marca;
        this.marca = marca;
    }

    public MarcaModel(String marca) {
        this.marca = marca;
    }

    public MarcaModel(int id_marca) {
        this.id_marca = id_marca;
    }

    public int getId_marca() {
        return id_marca;
    }

    public void setId_marca(int id_marca) {
        this.id_marca = id_marca;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return marca;
    }
}
