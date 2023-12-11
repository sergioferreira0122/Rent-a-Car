package aluguer.veiculos.aluguerveiculos.Models;

import aluguer.veiculos.aluguerveiculos.Enums.Permissoes;
import aluguer.veiculos.aluguerveiculos.Utils.PasswordHash;

import java.util.List;

public class FuncionarioModel {
    List<Permissoes> permissoes;
    private int id_funcionario;
    private String nome;
    private String email;
    private String password;

    public FuncionarioModel(int id_funcionario, String nome, String email, String password, List<Permissoes> permissoes) {
        this.id_funcionario = id_funcionario;
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.permissoes = permissoes;
    }

    public FuncionarioModel(String nome, String email, String password, List<Permissoes> permissoes) {
        this.nome = nome;
        this.email = email;
        this.password = password;
        this.permissoes = permissoes;
    }

    public FuncionarioModel(int id_funcionario, String nome, String email, List<Permissoes> permissoes) {
        this.id_funcionario = id_funcionario;
        this.nome = nome;
        this.email = email;
        this.permissoes = permissoes;
    }

    public FuncionarioModel(int id_funcionario, String nome, String email) {
        this.id_funcionario = id_funcionario;
        this.nome = nome;
        this.email = email;
    }

    public FuncionarioModel(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public int getId_funcionario() {
        return id_funcionario;
    }

    public void setId_funcionario(int id_funcionario) {
        this.id_funcionario = id_funcionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        if (password.length() == 64) {
            return password;
        }
        return PasswordHash.hashPassword(password);
    }

    public void setPassword(String password) {
        this.password = PasswordHash.hashPassword(password);
    }

    public List<Permissoes> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissoes> permissoes) {
        this.permissoes = permissoes;
    }
}
