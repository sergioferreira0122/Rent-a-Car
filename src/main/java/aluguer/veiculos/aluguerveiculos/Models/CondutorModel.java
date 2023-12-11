package aluguer.veiculos.aluguerveiculos.Models;

public class CondutorModel {
    private int id_condutor;
    private String nome;
    private String morada;
    private String cc;
    private String nr_carta;

    public CondutorModel(int id_condutor, String nome, String morada, String cc, String nr_carta) {
        this.id_condutor = id_condutor;
        this.nome = nome;
        this.morada = morada;
        this.cc = cc;
        this.nr_carta = nr_carta;
    }

    public CondutorModel(String nome, String morada, String cc, String nr_carta) {
        this.nome = nome;
        this.morada = morada;
        this.cc = cc;
        this.nr_carta = nr_carta;
    }

    public CondutorModel(int id_condutor) {
        this.id_condutor = id_condutor;
    }

    public int getId_condutor() {
        return id_condutor;
    }

    public void setId_condutor(int id_condutor) {
        this.id_condutor = id_condutor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getNr_carta() {
        return nr_carta;
    }

    public void setNr_carta(String nr_carta) {
        this.nr_carta = nr_carta;
    }
}
