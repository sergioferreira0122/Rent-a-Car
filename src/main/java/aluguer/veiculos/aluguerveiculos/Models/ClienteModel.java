package aluguer.veiculos.aluguerveiculos.Models;

import aluguer.veiculos.aluguerveiculos.Enums.TipoCliente;
import aluguer.veiculos.aluguerveiculos.Enums.TipoConta;

public class ClienteModel {
    private int id_cliente;
    private TipoCliente tipoCliente;
    private TipoConta tipoConta;
    private String nome;

    public ClienteModel(int id_cliente, TipoCliente tipoCliente, TipoConta tipoConta, String nome) {
        this.id_cliente = id_cliente;
        this.tipoCliente = tipoCliente;
        this.tipoConta = tipoConta;
        this.nome = nome;
    }

    public ClienteModel(TipoCliente tipoCliente, TipoConta tipoConta, String nome) {
        this.tipoCliente = tipoCliente;
        this.tipoConta = tipoConta;
        this.nome = nome;
    }

    public ClienteModel(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
