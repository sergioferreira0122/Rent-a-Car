package aluguer.veiculos.aluguerveiculos.Database.DAO.Interface;

import aluguer.veiculos.aluguerveiculos.Models.FuncionarioModel;

public interface IFuncionarioDAO extends DAO<FuncionarioModel> {
    FuncionarioModel login(String email, String password);

    boolean updateNoPasswordUpdate(FuncionarioModel dados);
}
