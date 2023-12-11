package aluguer.veiculos.aluguerveiculos.Database.DAO.Interface;

public interface IVerificacoesDAO {
    boolean verificarModeloDeUmaMarca(int id_modelo,
                                      int id_marca);
}
