package aluguer.veiculos.aluguerveiculos.Database.DAO.Interface;

import aluguer.veiculos.aluguerveiculos.Models.Servico_AluguerModel;

import java.util.List;

public interface IServico_AluguerDAO {
    public List<Servico_AluguerModel> getAll();

    public List<Servico_AluguerModel> getById(int id);

    public boolean insert(Servico_AluguerModel dados);

    public boolean update(Servico_AluguerModel dados);

    public boolean delete(Servico_AluguerModel dados);
}
