package aluguer.veiculos.aluguerveiculos.Models;

import java.time.LocalDate;

public class ReciboModel {
    private int id_recibo;
    private LocalDate data;
    private double valor;

    public ReciboModel(int id_recibo, LocalDate data, double valor) {
        this.id_recibo = id_recibo;
        this.data = data;
        this.valor = valor;
    }

    public ReciboModel(LocalDate data, double valor) {
        this.data = data;
        this.valor = valor;
    }

    public ReciboModel(int id_recibo) {
        this.id_recibo = id_recibo;
    }

    public int getId_recibo() {
        return id_recibo;
    }

    public void setId_recibo(int id_recibo) {
        this.id_recibo = id_recibo;
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
